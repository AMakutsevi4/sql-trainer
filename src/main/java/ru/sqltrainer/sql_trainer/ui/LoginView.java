package ru.sqltrainer.sql_trainer.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "login", autoLayout = false)
@PageTitle("Вход — SQL Trainer")
@AnonymousAllowed
public class LoginView extends Main implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();

        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);

        VerticalLayout box = new VerticalLayout(
                new H1("SQL Trainer"),
                new Paragraph("Вход только по логину от администратора."),
                login
        );
        box.setAlignItems(FlexComponent.Alignment.CENTER);
        box.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        box.setSizeFull();
        box.setSpacing(true);

        add(box);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            login.setError(true);
        }
    }
}
