package ru.sqltrainer.sql_trainer.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.sqltrainer.sql_trainer.user.UserService;

@Component
public class PasswordChangeInterceptor implements HandlerInterceptor {

    private final UserService users;

    public PasswordChangeInterceptor(UserService users) {
        this.users = users;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String path = request.getRequestURI();
        if (path.startsWith("/css/") || path.startsWith("/images/")
                || path.equals("/login") || path.equals("/logout")
                || path.equals("/change-password")) {
            return true;
        }
        String login = SecurityUtils.currentLogin();
        if (login != null && users.mustChangePassword(login)) {
            response.sendRedirect("/change-password");
            return false;
        }
        return true;
    }
}
