package ru.sqltrainer.sql_trainer.ui.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import ru.sqltrainer.sql_trainer.progress.AdminProgressService;
import ru.sqltrainer.sql_trainer.progress.AdminProgressService.SectionProgress;
import ru.sqltrainer.sql_trainer.progress.AdminProgressService.StudentDetail;
import ru.sqltrainer.sql_trainer.progress.AdminProgressService.StudentSummary;
import ru.sqltrainer.sql_trainer.progress.AdminProgressService.TaskProgress;
import ru.sqltrainer.sql_trainer.ui.MainLayout;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Route(value = "admin/progress", layout = MainLayout.class)
@PageTitle("Прогресс")
@RolesAllowed("ADMIN")
public class AdminProgressView extends VerticalLayout implements HasUrlParameter<Long> {

    private static final DateTimeFormatter TIME =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withZone(ZoneId.systemDefault());
    private static final String ALL_SECTIONS = "Все разделы";

    private final AdminProgressService progress;
    private final int totalTasks;

    private final Grid<StudentSummary> studentsGrid = new Grid<>(StudentSummary.class, false);
    private final Span detailHeader = new Span();
    private final Span sectionsSummary = new Span();
    private final ComboBox<String> sectionFilter = new ComboBox<>("Раздел");
    private final Grid<TaskProgress> tasksGrid = new Grid<>(TaskProgress.class, false);

    private StudentDetail currentDetail;
    private Long pendingUserId;

    public AdminProgressView(AdminProgressService progress) {
        this.progress = progress;
        this.totalTasks = progress.totalTasks();

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Прогресс учеников"),
                new Paragraph("Сводка по попыткам и решённым задачам. Выбери ученика, чтобы увидеть детали."));

        setupStudentsGrid();
        add(studentsGrid);

        detailHeader.addClassName("trainer-meta");
        sectionsSummary.addClassName("trainer-meta");

        sectionFilter.setWidth("280px");
        sectionFilter.setItems(ALL_SECTIONS);
        sectionFilter.setValue(ALL_SECTIONS);
        sectionFilter.addValueChangeListener(e -> refreshTasks());

        HorizontalLayout detailToolbar = new HorizontalLayout(detailHeader, sectionFilter);
        detailToolbar.setWidthFull();
        detailToolbar.setAlignItems(Alignment.END);
        detailToolbar.expand(detailHeader);

        setupTasksGrid();

        VerticalLayout detail = new VerticalLayout(detailToolbar, sectionsSummary, tasksGrid);
        detail.setPadding(false);
        detail.setSpacing(true);
        detail.setSizeFull();
        detail.expand(tasksGrid);

        add(detail);
        expand(detail);

        refreshStudents();
        clearDetail();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long userId) {
        pendingUserId = userId;
        if (studentsGrid.getListDataView().getItemCount() > 0) {
            applyPendingSelection();
        }
    }

    private void setupStudentsGrid() {
        studentsGrid.addColumn(StudentSummary::login).setHeader("Логин").setAutoWidth(true).setFlexGrow(1);
        studentsGrid.addColumn(s -> s.solved() + " / " + totalTasks)
                .setHeader("Решено")
                .setAutoWidth(true);
        studentsGrid.addColumn(StudentSummary::attempts).setHeader("Попытки").setAutoWidth(true);
        studentsGrid.addColumn(s -> formatTime(s.lastActivity()))
                .setHeader("Последняя активность")
                .setAutoWidth(true)
                .setFlexGrow(1);
        studentsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        studentsGrid.addSelectionListener(e ->
                e.getFirstSelectedItem().ifPresentOrElse(
                        s -> showDetail(s.userId()),
                        this::clearDetail
                )
        );
        studentsGrid.setHeight("220px");
    }

    private void setupTasksGrid() {
        tasksGrid.addColumn(TaskProgress::section).setHeader("Раздел").setAutoWidth(true);
        tasksGrid.addColumn(TaskProgress::order).setHeader("№").setAutoWidth(true).setFlexGrow(0);
        tasksGrid.addColumn(TaskProgress::title).setHeader("Задача").setFlexGrow(2);
        tasksGrid.addColumn(t -> t.solved() ? "решено" : (t.attempts() > 0 ? "в работе" : "—"))
                .setHeader("Статус")
                .setAutoWidth(true);
        tasksGrid.addColumn(TaskProgress::attempts).setHeader("Попытки").setAutoWidth(true);
        tasksGrid.addColumn(t -> formatTime(t.lastAttempt()))
                .setHeader("Последняя попытка")
                .setAutoWidth(true);
        tasksGrid.addComponentColumn(task -> {
            Button answer = new Button("Ответ", e -> openSqlDialog(task));
            answer.setEnabled(task.lastSql() != null && !task.lastSql().isBlank());
            answer.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            return answer;
        }).setHeader("").setAutoWidth(true).setFlexGrow(0);
        tasksGrid.setSizeFull();
    }

    private void refreshStudents() {
        List<StudentSummary> items = progress.studentSummaries();
        studentsGrid.setItems(items);
        applyPendingSelection();
    }

    private void applyPendingSelection() {
        if (pendingUserId == null) {
            return;
        }
        studentsGrid.getListDataView().getItems()
                .filter(s -> Objects.equals(s.userId(), pendingUserId))
                .findFirst()
                .ifPresentOrElse(studentsGrid::select, () -> showDetail(pendingUserId));
    }

    private void showDetail(Long userId) {
        progress.studentDetail(userId).ifPresentOrElse(detail -> {
            currentDetail = detail;
            detailHeader.setText(
                    detail.login() + " — решено " + detail.solved() + " / " + totalTasks
                            + ", попыток " + detail.attempts()
                            + (detail.lastActivity() == null ? "" : ", активность " + formatTime(detail.lastActivity()))
            );
            sectionsSummary.setText(formatSections(detail.sections()));

            List<String> filterItems = new ArrayList<>();
            filterItems.add(ALL_SECTIONS);
            detail.sections().stream().map(SectionProgress::section).forEach(filterItems::add);
            sectionFilter.setItems(filterItems);
            sectionFilter.setValue(ALL_SECTIONS);
            refreshTasks();
        }, this::clearDetail);
    }

    private void clearDetail() {
        currentDetail = null;
        detailHeader.setText("Выбери ученика в таблице выше");
        sectionsSummary.setText("");
        sectionFilter.setItems(ALL_SECTIONS);
        sectionFilter.setValue(ALL_SECTIONS);
        tasksGrid.setItems(List.of());
    }

    private void refreshTasks() {
        if (currentDetail == null) {
            tasksGrid.setItems(List.of());
            return;
        }
        String section = sectionFilter.getValue();
        List<TaskProgress> rows = currentDetail.tasks().stream()
                .filter(t -> section == null || ALL_SECTIONS.equals(section) || section.equals(t.section()))
                .toList();
        tasksGrid.setItems(rows);
    }

    private void openSqlDialog(TaskProgress task) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(task.title() + " — последний ответ");
        dialog.setWidth("720px");

        TextArea sql = new TextArea();
        sql.setValue(task.lastSql() == null ? "" : task.lastSql());
        sql.setReadOnly(true);
        sql.setWidthFull();
        sql.setHeight("360px");
        sql.getStyle().set("font-family", "var(--mono)");

        Button close = new Button("Закрыть", e -> dialog.close());
        dialog.add(sql);
        dialog.getFooter().add(close);
        dialog.open();
    }

    private static String formatSections(List<SectionProgress> sections) {
        if (sections.isEmpty()) {
            return "";
        }
        return "По разделам: " + sections.stream()
                .map(s -> s.section() + " " + s.solved() + "/" + s.total())
                .collect(Collectors.joining(" · "));
    }

    private static String formatTime(Instant instant) {
        return instant == null ? "—" : TIME.format(instant);
    }
}
