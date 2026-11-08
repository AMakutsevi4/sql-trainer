package ru.sqltrainer.sql_trainer.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import ru.sqltrainer.sql_trainer.security.SecurityUtils;
import ru.sqltrainer.sql_trainer.ui.trainee.TrainerView;
import ru.sqltrainer.sql_trainer.user.UserService;

@Route(value = "change-password", autoLayout = false)
@PageTitle("Смена пароля — SQL Trainer")
@PermitAll
public class ChangePasswordView extends Main implements BeforeEnterObserver {

    private final UserService users;

    public ChangePasswordView(UserService users) {
        this.users = users;

        addClassName("login-view");
        setSizeFull();

        PasswordField password = new PasswordField("Новый пароль");
        password.setWidth("320px");
        password.setRevealButtonVisible(true);

        PasswordField confirm = new PasswordField("Повторите пароль");
        confirm.setWidth("320px");
        confirm.setRevealButtonVisible(true);

        Button save = new Button("Сохранить", e -> {
            try {
                users.changeOwnPassword(password.getValue(), confirm.getValue());
                Notification.show("Пароль обновлён", 2500, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                UI.getCurrent().navigate(TrainerView.class);
            } catch (IllegalArgumentException ex) {
                Notification.show(ex.getMessage(), 3500, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setWidth("320px");

        VerticalLayout box = new VerticalLayout(
                new H1("Смена пароля"),
                password,
                confirm,
                save
        );
        box.setAlignItems(FlexComponent.Alignment.CENTER);
        box.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        box.setSizeFull();
        box.setSpacing(true);

        add(box);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String login = SecurityUtils.currentLogin();
        if (login == null) {
            event.forwardTo(LoginView.class);
            return;
        }
        if (!users.mustChangePassword(login)) {
            event.forwardTo(TrainerView.class);
        }
    }
}
