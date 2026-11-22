package ru.sqltrainer.sql_trainer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sqltrainer.sql_trainer.user.UserService;

@Controller
public class AdminUsersController {

    private final UserService users;

    public AdminUsersController(UserService users) {
        this.users = users;
    }

    @GetMapping("/admin/users")
    public String page(Model model) {
        model.addAttribute("users", users.findAll());
        return "admin/users";
    }

    @PostMapping("/admin/users/create")
    public String create(
            @RequestParam String login,
            @RequestParam String password,
            RedirectAttributes redirect
    ) {
        try {
            users.createStudent(login, password);
            redirect.addFlashAttribute("message", "Ученик создан");
        } catch (IllegalArgumentException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/reset")
    public String reset(
            @RequestParam Long userId,
            @RequestParam String password,
            RedirectAttributes redirect
    ) {
        try {
            users.resetPassword(userId, password);
            redirect.addFlashAttribute("message", "Пароль обновлён");
        } catch (IllegalArgumentException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/users";
    }
}
