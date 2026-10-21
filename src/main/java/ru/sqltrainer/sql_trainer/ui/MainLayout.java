package ru.sqltrainer.sql_trainer.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import ru.sqltrainer.sql_trainer.security.SecurityUtils;
import ru.sqltrainer.sql_trainer.ui.admin.AdminUsersView;
import ru.sqltrainer.sql_trainer.ui.trainee.TrainerView;

@PermitAll
public class MainLayout extends AppLayout {

    public MainLayout() {
        H1 title = new H1("SQL Trainer");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        String login = SecurityUtils.currentLogin();
        Span user = new Span(login == null ? "" : "Вы вошли: " + login);

        Button logout = new Button("Выйти", e -> {
            SecurityUtils.logout();
            UI.getCurrent().getPage().setLocation("/login");
        });

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title, user, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setWidthFull();
        header.getStyle().set("padding", "0 var(--lumo-space-m)");
        addToNavbar(header);

        VerticalLayout menu = new VerticalLayout();
        menu.setPadding(true);
        menu.setSpacing(false);
        menu.add(new RouterLink("Тренажёр", TrainerView.class));
        if (SecurityUtils.isAdmin()) {
            menu.add(new RouterLink("Ученики", AdminUsersView.class));
        }
        addToDrawer(menu);
    }
}
