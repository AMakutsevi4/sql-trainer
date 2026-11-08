package ru.sqltrainer.sql_trainer.exam;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sqltrainer.sql_trainer.progress.AttemptRecorder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExamCheckService {

    private final AttemptRecorder attemptRecorder;

    public ExamCheckService(AttemptRecorder attemptRecorder) {
        this.attemptRecorder = attemptRecorder;
    }

    @Transactional
    public ExamSessionResult submit(ExamDefinition exam, Map<String, Set<String>> answersByQuestionId) {
        List<QuestionResult> results = new ArrayList<>();
        int correct = 0;

        for (ExamQuestion question : exam.questions()) {
            Set<String> selected = normalize(answersByQuestionId.get(question.id()));
            boolean passed = !selected.isEmpty() && selected.equals(question.correctOptionIds());
            if (passed) {
                correct++;
            }
            attemptRecorder.record(question.id(), formatAnswer(question, selected), passed);
            results.add(new QuestionResult(question.id(), question.order(), question.title(), passed, selected));
        }

        int total = exam.questions().size();
        int percent = total == 0 ? 0 : (int) Math.round(100.0 * correct / total);
        String summary = correct + "/" + total + " (" + percent + "%)";
        attemptRecorder.record(exam.attemptTaskId(), summary, false);

        return new ExamSessionResult(exam.id(), exam.title(), correct, total, percent, List.copyOf(results));
    }

    private static Set<String> normalize(Set<String> selected) {
        if (selected == null || selected.isEmpty()) {
            return Set.of();
        }
        return selected.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static String formatAnswer(ExamQuestion question, Set<String> selected) {
        if (selected.isEmpty()) {
            return "(нет ответа)";
        }
        return question.options().stream()
                .filter(o -> selected.contains(o.id()))
                .map(o -> o.id() + ") " + o.text())
                .collect(Collectors.joining("; "));
    }

    public record QuestionResult(
            String questionId,
            int order,
            String title,
            boolean correct,
            Set<String> selectedOptionIds
    ) {
    }

    public record ExamSessionResult(
            String examId,
            String examTitle,
            int correct,
            int total,
            int percent,
            List<QuestionResult> questions
    ) {
    }
}
