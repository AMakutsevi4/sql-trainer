package ru.sqltrainer.sql_trainer.ui.exam;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;
import ru.sqltrainer.sql_trainer.exam.ExamCatalog;
import ru.sqltrainer.sql_trainer.exam.ExamCheckService;
import ru.sqltrainer.sql_trainer.exam.ExamCheckService.ExamSessionResult;
import ru.sqltrainer.sql_trainer.exam.ExamCheckService.QuestionResult;
import ru.sqltrainer.sql_trainer.exam.ExamDefinition;
import ru.sqltrainer.sql_trainer.exam.ExamOption;
import ru.sqltrainer.sql_trainer.exam.ExamQuestion;
import ru.sqltrainer.sql_trainer.progress.ProgressService;
import ru.sqltrainer.sql_trainer.ui.MainLayout;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Route(value = "exam", layout = MainLayout.class)
@PageTitle("Экзамен — SQL Trainer")
@PermitAll
public class ExamView extends VerticalLayout {

    private static final DateTimeFormatter TIME_FMT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withZone(ZoneId.systemDefault());

    private final ExamCatalog catalog;
    private final ExamCheckService checkService;
    private final ProgressService progress;

    private final Div listBlock = new Div();
    private final Div introBlock = new Div();
    private final Div quizBlock = new Div();
    private final Div resultsBlock = new Div();

    private final H2 introTitle = new H2();
    private final Pre introBody = new Pre();
    private final Span cooldownLabel = new Span();
    private final Button startButton = new Button("Пройти");
    private final Button backToList = new Button("← Ко всем экзаменам");

    private final Span progressLabel = new Span();
    private final Span timerLabel = new Span();
    private final Paragraph prompt = new Paragraph();
    private final Div answersBox = new Div();
    private final Span status = new Span();
    private final Button prev = new Button("← Назад");
    private final Button next = new Button("Вперед →");
    private final Button finish = new Button("Завершить экзамен");

    private final Map<String, Set<String>> answers = new HashMap<>();

    private ExamDefinition currentExam;
    private RadioButtonGroup<ExamOption> singleGroup;
    private CheckboxGroup<ExamOption> multiGroup;
    private int index;
    private boolean finished;
    private Instant deadline;
    private Registration pollRegistration;

    public ExamView(ExamCatalog catalog, ExamCheckService checkService, ProgressService progress) {
        this.catalog = catalog;
        this.checkService = checkService;
        this.progress = progress;

        addClassName("trainer");
        setPadding(false);
        setSpacing(false);
        setWidthFull();

        H1 brand = new H1("SQL Trainer");
        brand.addClassName("trainer-brand");

        buildList();
        buildIntro();
        buildQuiz();
        resultsBlock.addClassName("exam-results");
        resultsBlock.setVisible(false);
        introBlock.setVisible(false);
        quizBlock.setVisible(false);

        addDetachListener(e -> stopTimer());

        add(brand, listBlock, introBlock, quizBlock, resultsBlock);
        showList();
    }

    private void buildList() {
        listBlock.addClassName("exam-list");
        refreshList();
    }

    private void refreshList() {
        listBlock.removeAll();
        Div cards = new Div();
        cards.addClassName("exam-cards");
        for (ExamDefinition exam : catalog.exams()) {
            cards.add(examCard(exam));
        }
        listBlock.add(panel("Доступные экзамены", cards));
    }

    private Div examCard(ExamDefinition exam) {
        Div card = new Div();
        card.addClassName("exam-card");

        H2 title = new H2(exam.title());
        title.addClassName("exam-card-title");

        Paragraph meta = new Paragraph(
                exam.questions().size() + " вопросов · "
                        + exam.timeLimitMinutes() + " мин · повтор через "
                        + exam.retakeCooldownHours() + " ч"
        );
        meta.addClassName("trainer-meta");

        Span cooldown = new Span();
        cooldown.addClassName("trainer-meta");
        Button open = new Button("Открыть");
        open.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Optional<Instant> availableAt = nextAvailableAt(exam);
        boolean locked = availableAt.filter(when -> Instant.now().isBefore(when)).isPresent();
        if (locked) {
            cooldown.setText("Повтор с " + TIME_FMT.format(availableAt.get()));
            open.setEnabled(false);
            open.setText("Недоступно");
        } else {
            int attempts = progress.attempts(exam.attemptTaskId());
            cooldown.setText(attempts == 0 ? "" : "Попыток: " + attempts);
            open.setEnabled(true);
            open.addClickListener(e -> showIntro(exam));
        }

        card.add(title, meta, cooldown, open);
        return card;
    }

    private void buildIntro() {
        introBlock.addClassName("exam-intro");
        introTitle.addClassName("exam-intro-title");
        introBody.addClassName("exam-intro-text");
        cooldownLabel.addClassName("trainer-meta");
        startButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        startButton.addClickListener(e -> beginExam());
        backToList.addClickListener(e -> showList());

        Div actions = new Div(backToList, startButton);
        actions.addClassName("trainer-actions");

        Div panel = panel("Экзамен", introTitle, introBody, cooldownLabel, actions);
        introBlock.add(panel);
    }

    private void buildQuiz() {
        progressLabel.addClassName("trainer-progress");
        timerLabel.addClassName("exam-timer");
        prompt.addClassName("trainer-description");
        status.addClassName("trainer-status");
        status.addClassName("is-info");
        answersBox.addClassName("exam-answers");
        finish.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Div nav = new Div(prev, next, finish, progressLabel, timerLabel);
        nav.addClassName("trainer-nav");

        Div left = panel("Вопрос", prompt);
        Div right = panel("Варианты ответа", answersBox, status);
        Div grid = new Div(left, right);
        grid.addClassName("trainer-grid");

        quizBlock.addClassName("exam-quiz");
        quizBlock.add(nav, grid);

        prev.addClickListener(e -> move(-1));
        next.addClickListener(e -> move(1));
        finish.addClickListener(e -> finishExam(false));
    }

    private void showList() {
        stopTimer();
        finished = false;
        currentExam = null;
        listBlock.setVisible(true);
        introBlock.setVisible(false);
        quizBlock.setVisible(false);
        resultsBlock.setVisible(false);
        resultsBlock.removeAll();
        refreshList();
    }

    private void showIntro(ExamDefinition exam) {
        stopTimer();
        finished = false;
        currentExam = exam;
        listBlock.setVisible(false);
        quizBlock.setVisible(false);
        resultsBlock.setVisible(false);
        resultsBlock.removeAll();
        introBlock.setVisible(true);

        introTitle.setText(exam.title());
        introBody.setText(exam.introText());
        refreshCooldownUi();
    }

    private void refreshCooldownUi() {
        if (currentExam == null) {
            return;
        }
        Optional<Instant> availableAt = nextAvailableAt(currentExam);
        if (availableAt.filter(when -> Instant.now().isBefore(when)).isPresent()) {
            cooldownLabel.setText("Доступно с " + TIME_FMT.format(availableAt.get()));
            startButton.setEnabled(false);
            startButton.setText("Недоступно");
            return;
        }
        cooldownLabel.setText("");
        startButton.setEnabled(true);
        startButton.setText("Пройти");
    }

    private Optional<Instant> nextAvailableAt(ExamDefinition exam) {
        return progress.lastAttemptAt(exam.attemptTaskId())
                .map(last -> last.plus(Duration.ofHours(exam.retakeCooldownHours())));
    }

    private void beginExam() {
        if (currentExam == null) {
            return;
        }
        if (nextAvailableAt(currentExam).filter(when -> Instant.now().isBefore(when)).isPresent()) {
            refreshCooldownUi();
            return;
        }
        finished = false;
        answers.clear();
        index = 0;
        introBlock.setVisible(false);
        resultsBlock.setVisible(false);
        quizBlock.setVisible(true);
        deadline = Instant.now().plus(Duration.ofMinutes(currentExam.timeLimitMinutes()));
        startTimer();
        showCurrentQuestion();
    }

    private void startTimer() {
        stopTimer();
        UI ui = UI.getCurrent();
        if (ui == null) {
            return;
        }
        ui.setPollInterval(1000);
        pollRegistration = ui.addPollListener(e -> onTimerTick());
        onTimerTick();
    }

    private void stopTimer() {
        deadline = null;
        if (pollRegistration != null) {
            pollRegistration.remove();
            pollRegistration = null;
        }
        UI ui = UI.getCurrent();
        if (ui != null) {
            ui.setPollInterval(-1);
        }
    }

    private void onTimerTick() {
        if (finished || deadline == null) {
            return;
        }
        long secondsLeft = Duration.between(Instant.now(), deadline).getSeconds();
        if (secondsLeft <= 0) {
            timerLabel.setText("Осталось: 00:00");
            timerLabel.addClassName("is-bad");
            finishExam(true);
            return;
        }
        timerLabel.setText("Осталось: " + formatMmSs(secondsLeft));
        timerLabel.removeClassName("is-bad");
        if (secondsLeft <= 60) {
            timerLabel.addClassName("is-bad");
        }
    }

    private static String formatMmSs(long totalSeconds) {
        long m = totalSeconds / 60;
        long s = totalSeconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    private List<ExamQuestion> questions() {
        return currentExam == null ? List.of() : currentExam.questions();
    }

    private void move(int delta) {
        saveCurrentAnswer();
        int nextIndex = index + delta;
        if (nextIndex < 0 || nextIndex >= questions().size()) {
            return;
        }
        index = nextIndex;
        showCurrentQuestion();
    }

    private void showCurrentQuestion() {
        List<ExamQuestion> all = questions();
        if (all.isEmpty()) {
            return;
        }
        ExamQuestion question = all.get(index);
        answersBox.removeAll();
        singleGroup = null;
        multiGroup = null;
        setStatus("", "is-info");

        prompt.setText(question.prompt());

        boolean withImages = question.options().stream().anyMatch(ExamOption::hasImage);
        if (withImages) {
            answersBox.addClassName("exam-answers-images");
        } else {
            answersBox.removeClassName("exam-answers-images");
        }

        if (question.multiSelect()) {
            multiGroup = new CheckboxGroup<>();
            multiGroup.setItems(question.options());
            multiGroup.setItemLabelGenerator(ExamOption::text);
            multiGroup.setRenderer(new ComponentRenderer<>(ExamView::optionComponent));
            multiGroup.setWidthFull();
            Set<String> selectedIds = answers.getOrDefault(question.id(), Set.of());
            multiGroup.setValue(question.options().stream()
                    .filter(o -> selectedIds.contains(o.id()))
                    .collect(Collectors.toSet()));
            multiGroup.addValueChangeListener(e -> saveCurrentAnswer());
            answersBox.add(multiGroup);
        } else {
            singleGroup = new RadioButtonGroup<>();
            singleGroup.setItems(question.options());
            singleGroup.setItemLabelGenerator(ExamOption::text);
            singleGroup.setRenderer(new ComponentRenderer<>(ExamView::optionComponent));
            singleGroup.setWidthFull();
            Set<String> selectedIds = answers.getOrDefault(question.id(), Set.of());
            question.options().stream()
                    .filter(o -> selectedIds.contains(o.id()))
                    .findFirst()
                    .ifPresent(singleGroup::setValue);
            singleGroup.addValueChangeListener(e -> saveCurrentAnswer());
            answersBox.add(singleGroup);
        }

        refreshProgressLabel();
        prev.setEnabled(index > 0);
        next.setEnabled(index < all.size() - 1);
        finish.setEnabled(!finished);
    }

    private void saveCurrentAnswer() {
        if (finished || questions().isEmpty()) {
            return;
        }
        ExamQuestion question = questions().get(index);
        Set<String> selected = currentSelection();
        if (selected.isEmpty()) {
            answers.remove(question.id());
        } else {
            answers.put(question.id(), selected);
        }
        refreshProgressLabel();
    }

    private void refreshProgressLabel() {
        progressLabel.setText("Вопрос " + (index + 1) + " из " + questions().size()
                + " · отвечено " + answeredCount() + " / " + questions().size());
    }

    private int answeredCount() {
        return (int) questions().stream()
                .map(ExamQuestion::id)
                .filter(id -> answers.containsKey(id) && !answers.get(id).isEmpty())
                .count();
    }

    private Set<String> currentSelection() {
        if (multiGroup != null) {
            return multiGroup.getValue().stream().map(ExamOption::id).collect(Collectors.toCollection(HashSet::new));
        }
        if (singleGroup != null && singleGroup.getValue() != null) {
            return Set.of(singleGroup.getValue().id());
        }
        return Set.of();
    }

    private void finishExam(boolean timedOut) {
        if (finished || currentExam == null) {
            return;
        }
        saveCurrentAnswer();

        if (!timedOut) {
            List<ExamQuestion> unanswered = questions().stream()
                    .filter(q -> !answers.containsKey(q.id()) || answers.get(q.id()).isEmpty())
                    .toList();
            if (!unanswered.isEmpty()) {
                setStatus("Нет ответа: "
                                + unanswered.stream().map(q -> String.valueOf(q.order())).collect(Collectors.joining(", ")),
                        "is-bad");
                index = questions().indexOf(unanswered.getFirst());
                showCurrentQuestion();
                return;
            }
        }

        finished = true;
        stopTimer();
        ExamSessionResult result = checkService.submit(currentExam, answers);
        showResults(result, timedOut);
    }

    private void showResults(ExamSessionResult result, boolean timedOut) {
        quizBlock.setVisible(false);
        introBlock.setVisible(false);
        listBlock.setVisible(false);
        resultsBlock.removeAll();
        resultsBlock.setVisible(true);

        H2 title = new H2("Результат: " + result.examTitle());
        title.addClassName("exam-results-title");

        Paragraph score = new Paragraph(
                (timedOut ? "Время вышло. " : "")
                        + "Верно: " + result.correct() + " из " + result.total()
                        + " · Итог: " + result.percent() + "%"
        );
        score.addClassName("exam-score");
        if (result.percent() == 100) {
            score.addClassName("is-ok");
        } else if (result.percent() >= 60) {
            score.addClassName("is-info");
        } else {
            score.addClassName("is-bad");
        }

        Div list = new Div();
        list.addClassName("exam-result-list");
        for (QuestionResult item : result.questions()) {
            Span row = new Span(
                    (item.correct() ? "✓ " : "✗ ")
                            + item.order() + ". " + item.title()
                            + (item.correct() ? " — верно" : " — неверно")
            );
            row.addClassName("exam-result-row");
            row.addClassName(item.correct() ? "is-ok" : "is-bad");
            list.add(row);
        }

        Button toIntro = new Button("К описанию экзамена", e -> showIntro(currentExam));
        Button toList = new Button("Ко всем экзаменам", e -> showList());
        toIntro.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Div actions = new Div(toIntro, toList);
        actions.addClassName("trainer-actions");

        Div panel = panel("Итоги", title, score, list, actions);
        resultsBlock.add(panel);
    }

    private void setStatus(String text, String kind) {
        status.setText(text);
        status.removeClassNames("is-ok", "is-bad", "is-info");
        status.addClassName(kind);
    }

    private static com.vaadin.flow.component.Component optionComponent(ExamOption option) {
        if (!option.hasImage()) {
            return new Span(option.text());
        }
        Image image = new Image(option.imageUrl(), option.text());
        image.addClassName("exam-option-image");
        image.setAlt(option.text());
        Span caption = new Span(option.text());
        caption.addClassName("exam-option-caption");
        Div box = new Div(caption, image);
        box.addClassName("exam-option-with-image");
        return box;
    }

    private static Div panel(String title, com.vaadin.flow.component.Component... parts) {
        Span heading = new Span(title);
        heading.addClassName("trainer-panel-title");
        Div box = new Div();
        box.addClassName("trainer-panel");
        box.add(heading);
        box.add(parts);
        return box;
    }
}
