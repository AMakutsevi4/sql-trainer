package ru.sqltrainer.sql_trainer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sqltrainer.sql_trainer.security.SecurityUtils;
import ru.sqltrainer.sql_trainer.user.UserService;

@Controller
public class ChangePasswordController {

    private final UserService users;

    public ChangePasswordController(UserService users) {
        this.users = users;
    }

    @GetMapping("/change-password")
    public String form() {
        String login = SecurityUtils.currentLogin();
        if (login == null) {
            return "redirect:/login";
        }
        if (!users.mustChangePassword(login)) {
            return "redirect:/";
        }
        return "change-password";
    }

    @PostMapping("/change-password")
    public String save(
            @RequestParam String password,
            @RequestParam String confirm,
            Model model
    ) {
        try {
            users.changeOwnPassword(password, confirm);
            return "redirect:/";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "change-password";
        }
    }
}
