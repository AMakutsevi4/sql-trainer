package ru.sqltrainer.sql_trainer.security;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.ui.ChangePasswordView;
import ru.sqltrainer.sql_trainer.ui.LoginView;
import ru.sqltrainer.sql_trainer.user.UserService;

@Component
public class ForcePasswordChangeInitListener implements VaadinServiceInitListener {

    private final UserService users;

    public ForcePasswordChangeInitListener(UserService users) {
        this.users = users;
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent ->
                uiEvent.getUI().addBeforeEnterListener(this::beforeEnter)
        );
    }

    private void beforeEnter(BeforeEnterEvent event) {
        Class<?> target = event.getNavigationTarget();
        if (target == ChangePasswordView.class || target == LoginView.class) {
            return;
        }
        String login = SecurityUtils.currentLogin();
        if (login == null) {
            return;
        }
        if (users.mustChangePassword(login)) {
            event.forwardTo(ChangePasswordView.class);
        }
    }
}
