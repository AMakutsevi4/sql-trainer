package ru.sqltrainer.sql_trainer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sqltrainer.sql_trainer.progress.AdminProgressService;
import ru.sqltrainer.sql_trainer.progress.AdminProgressService.TaskProgress;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AdminProgressController {

    private static final DateTimeFormatter TIME =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withZone(ZoneId.systemDefault());

    private final AdminProgressService progress;

    public AdminProgressController(AdminProgressService progress) {
        this.progress = progress;
    }

    @GetMapping("/admin/progress")
    public String page(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String section,
            Model model
    ) {
        int totalTasks = progress.totalTasks();
        model.addAttribute("totalTasks", totalTasks);
        model.addAttribute("students", progress.studentSummaries());
        model.addAttribute("selectedUserId", userId);
        model.addAttribute("timeFormatter", TIME);

        if (userId != null) {
            progress.studentDetail(userId).ifPresent(detail -> {
                model.addAttribute("detail", detail);
                List<String> sections = detail.sections().stream()
                        .map(s -> s.section())
                        .toList();
                model.addAttribute("sections", sections);
                String filter = section == null || section.isBlank() ? "ALL" : section;
                model.addAttribute("sectionFilter", filter);
                String sectionsText = detail.sections().stream()
                        .map(s -> s.section() + " " + s.solved() + "/" + s.total())
                        .reduce((a, b) -> a + " · " + b)
                        .orElse("");
                model.addAttribute("sectionsText", sectionsText.isBlank() ? "" : "По разделам: " + sectionsText);
                List<TaskProgress> tasks = detail.tasks().stream()
                        .filter(t -> "ALL".equals(filter) || filter.equals(t.section()))
                        .toList();
                model.addAttribute("tasks", tasks);
            });
        }
        return "admin/progress";
    }

    public static String formatTime(Instant instant) {
        return instant == null ? "—" : TIME.format(instant);
    }
}
