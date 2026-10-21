package ru.sqltrainer.sql_trainer.ui.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import ru.sqltrainer.sql_trainer.ui.MainLayout;
import ru.sqltrainer.sql_trainer.user.AppUser;
import ru.sqltrainer.sql_trainer.user.UserService;

@Route(value = "admin/users", layout = MainLayout.class)
@PageTitle("Ученики")
@RolesAllowed("ADMIN")
public class AdminUsersView extends VerticalLayout {

    private final UserService users;
    private final Grid<AppUser> grid = new Grid<>(AppUser.class, false);

    public AdminUsersView(UserService users) {
        this.users = users;
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Ученики"), new Paragraph("Создай логин/пароль и отдай ученику. Саморегистрации нет."));

        TextField login = new TextField("Логин");
        PasswordField password = new PasswordField("Пароль");
        Button create = new Button("Создать ученика", e -> {
            try {
                users.createStudent(login.getValue(), password.getValue());
                login.clear();
                password.clear();
                refresh();
                Notification.show("Ученик создан", 2500, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (IllegalArgumentException ex) {
                Notification.show(ex.getMessage(), 3500, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout form = new HorizontalLayout(login, password, create);
        form.setAlignItems(Alignment.END);
        add(form);

        grid.addColumn(AppUser::getLogin).setHeader("Логин").setAutoWidth(true);
        grid.addColumn(u -> u.getRole().name()).setHeader("Роль").setAutoWidth(true);
        grid.addColumn(u -> u.isActive() ? "да" : "нет").setHeader("Активен").setAutoWidth(true);
        grid.addComponentColumn(user ->
                new Button("Сбросить пароль", e -> openResetDialog(user))
        ).setHeader("");

        add(grid);
        expand(grid);
        refresh();
    }

    private void openResetDialog(AppUser user) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Новый пароль для " + user.getLogin());

        PasswordField password = new PasswordField("Пароль");
        password.setWidthFull();

        Button save = new Button("Сохранить", e -> {
            try {
                users.resetPassword(user.getId(), password.getValue());
                dialog.close();
                Notification.show("Пароль обновлён", 2500, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (IllegalArgumentException ex) {
                Notification.show(ex.getMessage(), 3500, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Отмена", e -> dialog.close());

        dialog.add(password);
        dialog.getFooter().add(cancel, save);
        dialog.open();
    }

    private void refresh() {
        grid.setItems(users.findAll());
    }
}
