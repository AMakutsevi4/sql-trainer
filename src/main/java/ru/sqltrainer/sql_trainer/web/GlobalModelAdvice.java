package ru.sqltrainer.sql_trainer.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.sqltrainer.sql_trainer.security.SecurityUtils;

@ControllerAdvice
public class GlobalModelAdvice {

    @ModelAttribute("currentLogin")
    public String currentLogin() {
        return SecurityUtils.currentLogin();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return SecurityUtils.isAdmin();
    }

    @ModelAttribute("currentPath")
    public String currentPath(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
