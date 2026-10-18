package ru.sqltrainer.sql_trainer.ui.trainee;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.sqltrainer.sql_trainer.engine.QueryTable;
import ru.sqltrainer.sql_trainer.progress.TraineeProgress;
import ru.sqltrainer.sql_trainer.task.CheckResult;
import ru.sqltrainer.sql_trainer.task.SqlTask;
import ru.sqltrainer.sql_trainer.task.TaskCatalog;
import ru.sqltrainer.sql_trainer.task.TaskCheckService;

import java.time.Duration;
import java.util.List;

@Route("")
@PageTitle("SQL Trainer")
public class TrainerView extends VerticalLayout {

    private final TaskCatalog catalog;
    private final TaskCheckService checkService;

    private final ComboBox<String> sectionBox = new ComboBox<>("Раздел");
    private final ComboBox<SqlTask> taskBox = new ComboBox<>("Задача");
    private final Span progressLabel = new Span();
    private final Span taskMeta = new Span();
    private final Paragraph description = new Paragraph();
    private final Span status = new Span();
    private final Pre schema = new Pre();
    private final SqlEditor editor = new SqlEditor();
    private final Div resultBox = new Div();
    private final Button prev = new Button("← Назад");
    private final Button next = new Button("Вперед →");
    private boolean suppressTaskEvents;

    public TrainerView(TaskCatalog catalog, TaskCheckService checkService) {
        this.catalog = catalog;
        this.checkService = checkService;

        addClassName("trainer");
        setPadding(false);
        setSpacing(false);
        setWidthFull();

        H1 brand = new H1("SQL Trainer");
        brand.addClassName("trainer-brand");

        Paragraph tagline = new Paragraph("Выбери задачу, напиши SQL и сразу проверь результат.");
        tagline.addClassName("trainer-tagline");

        sectionBox.setItems(catalog.sections());
        sectionBox.setWidthFull();
        taskBox.setWidthFull();
        taskBox.setItemLabelGenerator(this::taskLabel);

        progressLabel.addClassName("trainer-progress");
        taskMeta.addClassName("trainer-meta");

        Div pickers = new Div(sectionBox, taskBox);
        pickers.addClassName("trainer-pickers");

        Div nav = new Div(prev, next, progressLabel);
        nav.addClassName("trainer-nav");

        description.addClassName("trainer-description");
        schema.addClassName("trainer-code");
        status.addClassName("trainer-status");
        status.addClassName("is-info");

        Button run = new Button("Выполнить", e -> run(false));
        Button check = new Button("Проверить", e -> run(true));
        check.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button reset = new Button("Сбросить", e -> resetEditor());

        Div actions = new Div(run, check, reset);
        actions.addClassName("trainer-actions");

        Div left = panel("Условие и схема", description, schema);
        Div right = panel("Редактор и результат", taskMeta, editor, actions, status, resultBox);

        Div grid = new Div(left, right);
        grid.addClassName("trainer-grid");

        sectionBox.addValueChangeListener(e -> {
            if (e.getValue() == null) {
                return;
            }
            List<SqlTask> tasks = catalog.bySection(e.getValue());
            taskBox.setItems(tasks);
            refreshProgress();
            if (!tasks.isEmpty()) {
                taskBox.setValue(tasks.getFirst());
            } else {
                taskBox.clear();
            }
        });
        taskBox.addValueChangeListener(e -> {
            if (!suppressTaskEvents) {
                showTask(e.getValue());
            }
        });

        prev.addClickListener(e -> moveTask(-1));
        next.addClickListener(e -> moveTask(1));

        add(brand, tagline, pickers, nav, grid);

        if (!catalog.sections().isEmpty()) {
            sectionBox.setValue(catalog.sections().getFirst());
        }
    }

    private String taskLabel(SqlTask task) {
        String mark = TraineeProgress.current().isSolved(task.id()) ? "✓ " : "";
        return mark + task.title();
    }

    private void moveTask(int delta) {
        String section = sectionBox.getValue();
        SqlTask current = taskBox.getValue();
        if (section == null || current == null) {
            return;
        }
        List<SqlTask> tasks = catalog.bySection(section);
        int index = tasks.indexOf(current);
        if (index < 0) {
            return;
        }
        int nextIndex = index + delta;
        if (nextIndex < 0 || nextIndex >= tasks.size()) {
            return;
        }
        taskBox.setValue(tasks.get(nextIndex));
    }

    private void refreshProgress() {
        String section = sectionBox.getValue();
        if (section == null) {
            progressLabel.setText("");
            return;
        }
        List<SqlTask> tasks = catalog.bySection(section);
        long solved = TraineeProgress.current().solvedIn(tasks.stream().map(SqlTask::id).toList());
        progressLabel.setText("Прогресс раздела: " + solved + " / " + tasks.size());

        SqlTask selected = taskBox.getValue();
        suppressTaskEvents = true;
        try {
            taskBox.setItems(tasks);
            if (selected != null && tasks.contains(selected)) {
                taskBox.setValue(selected);
            }
        } finally {
            suppressTaskEvents = false;
        }
        updateNavEnabled();
    }

    private void updateNavEnabled() {
        String section = sectionBox.getValue();
        SqlTask current = taskBox.getValue();
        if (section == null || current == null) {
            prev.setEnabled(false);
            next.setEnabled(false);
            return;
        }
        List<SqlTask> tasks = catalog.bySection(section);
        int index = tasks.indexOf(current);
        prev.setEnabled(index > 0);
        next.setEnabled(index >= 0 && index < tasks.size() - 1);
    }

    private void refreshTaskMeta(SqlTask task) {
        if (task == null) {
            taskMeta.setText("");
            return;
        }
        TraineeProgress progress = TraineeProgress.current();
        String solved = progress.isSolved(task.id()) ? "решено" : "не решено";
        int attempts = progress.attempts(task.id());
        String time = formatDuration(progress.timeSpent(task.id()));
        taskMeta.setText("Статус: " + solved + " · Попытки: " + attempts + " · Время: " + time);
    }

    private static String formatDuration(Duration duration) {
        long seconds = Math.max(0, duration.getSeconds());
        long minutes = seconds / 60;
        long rem = seconds % 60;
        if (minutes == 0) {
            return rem + " с";
        }
        return minutes + " мин " + rem + " с";
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

    private void showTask(SqlTask task) {
        if (task == null) {
            description.setText("");
            schema.setText("");
            editor.setValue("");
            resultBox.removeAll();
            setStatus("", "is-info");
            refreshTaskMeta(null);
            updateNavEnabled();
            return;
        }
        TraineeProgress.current().openTask(task.id());
        description.setText(task.description());
        schema.setText(task.schema().trim() + "\n\n" + task.data().trim());
        editor.setValue(task.starter());
        resultBox.removeAll();
        setStatus("", "is-info");
        refreshTaskMeta(task);
        refreshProgress();
    }

    private void resetEditor() {
        SqlTask task = taskBox.getValue();
        if (task != null) {
            editor.setValue(task.starter());
        }
        resultBox.removeAll();
        setStatus("", "is-info");
    }

    private void run(boolean check) {
        SqlTask task = taskBox.getValue();
        if (task == null) {
            setStatus("Сначала выбери задачу.", "is-bad");
            return;
        }

        editor.getValueAsync().thenAccept(sql -> getUI().ifPresent(ui -> ui.access(() ->
                applyOutcome(task, check, sql)
        )));
    }

    private void applyOutcome(SqlTask task, boolean check, String sql) {
        CheckResult outcome = check
                ? checkService.check(task.id(), sql)
                : checkService.run(task.id(), sql);

        if (outcome.hasError()) {
            setStatus("Ошибка SQL", "is-bad");
            showError(outcome.errorMessage());
            refreshTaskMeta(task);
            refreshProgress();
            notify("Ошибка SQL", NotificationVariant.LUMO_ERROR);
            return;
        }

        if (!check) {
            setStatus("Запрос выполнен", "is-info");
            showTable("Результат", outcome.table());
            refreshTaskMeta(task);
            return;
        }

        if (outcome.passed()) {
            setStatus("Верно!", "is-ok");
            showTable("Результат", outcome.table());
            notify("Верно!", NotificationVariant.LUMO_SUCCESS);
        } else {
            setStatus("Пока неверно", "is-bad");
            showCompare(outcome.table(), QueryTable.fromCsv(task.expected()));
            notify("Пока неверно", NotificationVariant.LUMO_ERROR);
        }
        refreshTaskMeta(task);
        refreshProgress();
    }

    private void showError(String message) {
        resultBox.removeAll();
        Pre pre = new Pre(message);
        pre.addClassName("trainer-result");
        resultBox.add(pre);
    }

    private void showTable(String label, QueryTable table) {
        resultBox.removeAll();
        Span title = new Span(label);
        title.addClassName("trainer-result-label");
        resultBox.add(title, createGrid(table));
    }

    private void showCompare(QueryTable actual, QueryTable expected) {
        resultBox.removeAll();

        Span yours = new Span("Твой результат");
        yours.addClassName("trainer-result-label");

        Span want = new Span("Ожидалось");
        want.addClassName("trainer-result-label");

        resultBox.add(yours, createGrid(actual), want, createGrid(expected));
    }

    private Grid<List<String>> createGrid(QueryTable table) {
        Grid<List<String>> grid = new Grid<>();
        grid.addClassName("trainer-result-grid");
        grid.setWidthFull();
        grid.setAllRowsVisible(true);

        List<String> columns = table.columns();
        for (int i = 0; i < columns.size(); i++) {
            final int index = i;
            grid.addColumn(row -> index < row.size() ? row.get(index) : "")
                    .setHeader(columns.get(i))
                    .setAutoWidth(true)
                    .setFlexGrow(0);
        }
        grid.setItems(table.rows());
        return grid;
    }

    private void setStatus(String text, String tone) {
        status.setText(text);
        status.removeClassName("is-ok");
        status.removeClassName("is-bad");
        status.removeClassName("is-info");
        status.addClassName(tone);
    }

    private static void notify(String text, NotificationVariant variant) {
        Notification n = Notification.show(text, 2800, Notification.Position.TOP_CENTER);
        n.addThemeVariants(variant);
    }
}
