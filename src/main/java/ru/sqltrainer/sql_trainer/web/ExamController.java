package ru.sqltrainer.sql_trainer.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sqltrainer.sql_trainer.exam.ExamCatalog;
import ru.sqltrainer.sql_trainer.exam.ExamCheckService;
import ru.sqltrainer.sql_trainer.exam.ExamDefinition;
import ru.sqltrainer.sql_trainer.exam.ExamQuestion;
import ru.sqltrainer.sql_trainer.progress.ProgressService;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ExamController {

    private static final String SESSION_KEY = "examSession";
    private static final DateTimeFormatter TIME_FMT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withZone(ZoneId.systemDefault());

    private final ExamCatalog catalog;
    private final ExamCheckService checkService;
    private final ProgressService progress;

    public ExamController(ExamCatalog catalog, ExamCheckService checkService, ProgressService progress) {
        this.catalog = catalog;
        this.checkService = checkService;
        this.progress = progress;
    }

    @GetMapping("/exam")
    public String list(Model model) {
        model.addAttribute("exams", catalog.exams().stream().map(this::card).toList());
        return "exam/list";
    }

    @GetMapping("/exam/{examId}")
    public String intro(@PathVariable String examId, Model model, HttpSession httpSession) {
        ExamDefinition exam = catalog.findExam(examId).orElse(null);
        if (exam == null) {
            return "redirect:/exam";
        }
        ExamSession session = session(httpSession);
        if (session.getResult() != null && examId.equals(session.getExamId())) {
            return resultsModel(model, session);
        }
        if (session.getDeadline() != null && examId.equals(session.getExamId()) && session.getResult() == null) {
            return "redirect:/exam/" + examId + "/quiz";
        }
        model.addAttribute("exam", exam);
        Optional<Instant> availableAt = nextAvailableAt(exam);
        boolean locked = availableAt.filter(when -> Instant.now().isBefore(when)).isPresent();
        model.addAttribute("locked", locked);
        model.addAttribute("availableAt", locked ? TIME_FMT.format(availableAt.get()) : null);
        model.addAttribute("attempts", progress.attempts(exam.attemptTaskId()));
        return "exam/intro";
    }

    @PostMapping("/exam/{examId}/start")
    public String start(@PathVariable String examId, HttpSession httpSession) {
        ExamDefinition exam = catalog.findExam(examId).orElse(null);
        if (exam == null) {
            return "redirect:/exam";
        }
        if (nextAvailableAt(exam).filter(when -> Instant.now().isBefore(when)).isPresent()) {
            return "redirect:/exam/" + examId;
        }
        ExamSession session = new ExamSession();
        session.setExamId(examId);
        session.setIndex(0);
        session.setDeadline(Instant.now().plus(Duration.ofMinutes(exam.timeLimitMinutes())));
        httpSession.setAttribute(SESSION_KEY, session);
        return "redirect:/exam/" + examId + "/quiz";
    }

    @GetMapping("/exam/{examId}/quiz")
    public String quiz(@PathVariable String examId, Model model, HttpSession httpSession) {
        ExamDefinition exam = catalog.findExam(examId).orElse(null);
        ExamSession session = session(httpSession);
        if (exam == null || !examId.equals(session.getExamId()) || session.getDeadline() == null) {
            return "redirect:/exam/" + examId;
        }
        if (session.getResult() != null) {
            return resultsModel(model, session);
        }
        if (!Instant.now().isBefore(session.getDeadline())) {
            return finishInternal(exam, session, true, httpSession, model);
        }
        List<ExamQuestion> questions = exam.questions();
        int index = Math.min(Math.max(session.getIndex(), 0), questions.size() - 1);
        session.setIndex(index);
        ExamQuestion question = questions.get(index);
        long secondsLeft = Duration.between(Instant.now(), session.getDeadline()).getSeconds();
        model.addAttribute("exam", exam);
        model.addAttribute("question", question);
        model.addAttribute("index", index);
        model.addAttribute("total", questions.size());
        model.addAttribute("answered", answeredCount(session, questions));
        model.addAttribute("selected", session.getAnswers().getOrDefault(question.id(), Set.of()));
        model.addAttribute("hasPrev", index > 0);
        model.addAttribute("hasNext", index < questions.size() - 1);
        model.addAttribute("secondsLeft", Math.max(secondsLeft, 0));
        model.addAttribute("timerText", formatMmSs(Math.max(secondsLeft, 0)));
        return "exam/quiz";
    }

    @PostMapping("/exam/{examId}/quiz")
    public String quizAction(
            @PathVariable String examId,
            @RequestParam String action,
            @RequestParam(required = false) String[] option,
            HttpSession httpSession,
            Model model
    ) {
        ExamDefinition exam = catalog.findExam(examId).orElse(null);
        ExamSession session = session(httpSession);
        if (exam == null || !examId.equals(session.getExamId())) {
            return "redirect:/exam";
        }
        if (!Instant.now().isBefore(session.getDeadline())) {
            return finishInternal(exam, session, true, httpSession, model);
        }
        ExamQuestion question = exam.questions().get(session.getIndex());
        Set<String> selected = option == null
                ? Set.of()
                : Arrays.stream(option).collect(Collectors.toCollection(HashSet::new));
        if (selected.isEmpty()) {
            session.getAnswers().remove(question.id());
        } else {
            session.getAnswers().put(question.id(), selected);
        }

        return switch (action) {
            case "prev" -> {
                session.setIndex(Math.max(0, session.getIndex() - 1));
                yield "redirect:/exam/" + examId + "/quiz";
            }
            case "next" -> {
                session.setIndex(Math.min(exam.questions().size() - 1, session.getIndex() + 1));
                yield "redirect:/exam/" + examId + "/quiz";
            }
            case "finish" -> finishInternal(exam, session, false, httpSession, model);
            default -> "redirect:/exam/" + examId + "/quiz";
        };
    }

    @GetMapping("/exam/{examId}/result")
    public String result(@PathVariable String examId, Model model, HttpSession httpSession) {
        ExamSession session = session(httpSession);
        if (session.getResult() == null || !examId.equals(session.getExamId())) {
            return "redirect:/exam/" + examId;
        }
        return resultsModel(model, session);
    }

    @PostMapping("/exam/{examId}/clear")
    public String clear(@PathVariable String examId, HttpSession httpSession) {
        httpSession.removeAttribute(SESSION_KEY);
        return "redirect:/exam/" + examId;
    }

    private String finishInternal(
            ExamDefinition exam,
            ExamSession session,
            boolean timedOut,
            HttpSession httpSession,
            Model model
    ) {
        if (!timedOut) {
            List<ExamQuestion> unanswered = exam.questions().stream()
                    .filter(q -> !session.getAnswers().containsKey(q.id()) || session.getAnswers().get(q.id()).isEmpty())
                    .toList();
            if (!unanswered.isEmpty()) {
                session.setIndex(exam.questions().indexOf(unanswered.getFirst()));
                model.addAttribute("statusText", "Нет ответа: " + unanswered.stream()
                        .map(q -> String.valueOf(q.order()))
                        .collect(Collectors.joining(", ")));
                return "redirect:/exam/" + exam.id() + "/quiz";
            }
        }
        session.setTimedOut(timedOut);
        session.setResult(checkService.submit(exam, session.getAnswers()));
        session.setDeadline(null);
        httpSession.setAttribute(SESSION_KEY, session);
        return "redirect:/exam/" + exam.id() + "/result";
    }

    private String resultsModel(Model model, ExamSession session) {
        model.addAttribute("result", session.getResult());
        model.addAttribute("timedOut", session.isTimedOut());
        model.addAttribute("examId", session.getExamId());
        return "exam/result";
    }

    private ExamCard card(ExamDefinition exam) {
        Optional<Instant> availableAt = nextAvailableAt(exam);
        boolean locked = availableAt.filter(when -> Instant.now().isBefore(when)).isPresent();
        return new ExamCard(
                exam.id(),
                exam.title(),
                exam.questions().size(),
                exam.timeLimitMinutes(),
                exam.retakeCooldownHours(),
                progress.attempts(exam.attemptTaskId()),
                locked,
                locked ? TIME_FMT.format(availableAt.get()) : null
        );
    }

    private Optional<Instant> nextAvailableAt(ExamDefinition exam) {
        return progress.lastAttemptAt(exam.attemptTaskId())
                .map(last -> last.plus(Duration.ofHours(exam.retakeCooldownHours())));
    }

    private static ExamSession session(HttpSession httpSession) {
        Object raw = httpSession.getAttribute(SESSION_KEY);
        if (raw instanceof ExamSession existing) {
            return existing;
        }
        ExamSession created = new ExamSession();
        httpSession.setAttribute(SESSION_KEY, created);
        return created;
    }

    private static int answeredCount(ExamSession session, List<ExamQuestion> questions) {
        return (int) questions.stream()
                .map(ExamQuestion::id)
                .filter(id -> session.getAnswers().containsKey(id) && !session.getAnswers().get(id).isEmpty())
                .count();
    }

    private static String formatMmSs(long totalSeconds) {
        long m = totalSeconds / 60;
        long s = totalSeconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    public record ExamCard(
            String id,
            String title,
            int questions,
            int timeLimitMinutes,
            int retakeCooldownHours,
            int attempts,
            boolean locked,
            String availableAt
    ) {
    }
}
