package ru.sqltrainer.sql_trainer.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("SQL Trainer")
public class TrainerView extends VerticalLayout {

    private final SqlRunner sqlRunner;

    private final ComboBox<String> sectionBox = new ComboBox<>("Раздел");
    private final ComboBox<SqlTask> taskBox = new ComboBox<>("Задача");
    private final Paragraph description = new Paragraph();
    private final Paragraph status = new Paragraph();
    private final Pre schema = new Pre();
    private final TextArea editor = new TextArea("SQL");
    private final Pre result = new Pre();

    public TrainerView(TaskCatalog catalog, SqlRunner sqlRunner) {
        this.sqlRunner = sqlRunner;

        setMaxWidth("900px");
        setPadding(true);
        setSpacing(true);

        sectionBox.setItems(catalog.sections());
        sectionBox.setWidthFull();
        taskBox.setWidthFull();
        taskBox.setItemLabelGenerator(SqlTask::title);

        editor.setWidthFull();
        editor.setMinHeight("140px");
        schema.getStyle().set("white-space", "pre-wrap").set("background", "#f5f5f5").set("padding", "8px");
        result.getStyle().set("white-space", "pre-wrap").set("background", "#f5f5f5").set("padding", "8px");
        status.getStyle().set("font-weight", "bold");

        Button run = new Button("Выполнить", e -> runQuery(false));
        Button check = new Button("Проверить", e -> runQuery(true));
        check.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        sectionBox.addValueChangeListener(e -> {
            if (e.getValue() == null) {
                return;
            }
            taskBox.setItems(catalog.bySection(e.getValue()));
            taskBox.clear();
        });

        taskBox.addValueChangeListener(e -> showTask(e.getValue()));

        add(
                new H1("SQL Trainer"),
                sectionBox,
                taskBox,
                new H3("Условие"),
                description,
                new H3("Схема и данные"),
                schema,
                editor,
                new HorizontalLayout(run, check),
                status,
                new H3("Результат"),
                result
        );

        if (!catalog.sections().isEmpty()) {
            sectionBox.setValue(catalog.sections().getFirst());
        }

    }

    private void showTask(SqlTask task) {
        if (task == null) {
            description.setText("");
            schema.setText("");
            editor.clear();
            result.setText("");
            status.setText("");
            return;
        }
        description.setText(task.description());
        schema.setText(task.schemaSql() + "\n\n" + task.dataSql());
        editor.setValue(task.hintQuery());
        result.setText("");
        status.setText("");
    }

    private void runQuery(boolean check) {
        SqlTask task = taskBox.getValue();
        if (task == null) {
            status.setText("Сначала выбери задачу.");
            result.setText("");
            return;
        }

        SqlRunner.QueryResult queryResult = sqlRunner.execute(task, editor.getValue());
        if (!queryResult.isOk()) {
            status.setText("Ошибка SQL");
            result.setText(queryResult.error());
            notify("Ошибка SQL", NotificationVariant.LUMO_ERROR);
            return;
        }

        result.setText(queryResult.csv());

        if (!check) {
            status.setText("Запрос выполнен");
            return;
        }

        boolean ok = sqlRunner.matchesExpected(task, editor.getValue());
        if (ok) {
            status.setText("Верно!");
            notify("Верно!", NotificationVariant.LUMO_SUCCESS);
        } else {
            status.setText("Пока неверно — сравни с ожидаемым результатом.");
            notify("Пока неверно", NotificationVariant.LUMO_ERROR);
        }
    }

    private static void notify(String text, NotificationVariant variant) {
        Notification n = Notification.show(text, 3000, Notification.Position.TOP_CENTER);
        n.addThemeVariants(variant);
    }
}
