package ru.sqltrainer.sql_trainer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sqltrainer.sql_trainer.progress.ProgressService;
import ru.sqltrainer.sql_trainer.task.SqlTask;
import ru.sqltrainer.sql_trainer.task.TaskCatalog;

import java.util.List;

@Controller
public class TrainerController {

    private final TaskCatalog catalog;
    private final ProgressService progress;

    public TrainerController(TaskCatalog catalog, ProgressService progress) {
        this.catalog = catalog;
        this.progress = progress;
    }

    @GetMapping("/")
    public String trainer(
            @RequestParam(required = false) String section,
            @RequestParam(required = false) String taskId,
            Model model
    ) {
        List<String> sections = catalog.sections();
        if (section == null || section.isBlank()) {
            section = sections.isEmpty() ? null : sections.getFirst();
        }

        List<SqlTask> tasks = section == null ? List.of() : catalog.bySection(section);
        SqlTask task = null;
        if (taskId != null && !taskId.isBlank()) {
            task = catalog.findById(taskId).orElse(null);
        }
        if (task != null && section != null && !section.equals(task.section())) {
            task = null;
        }
        if (task == null && !tasks.isEmpty()) {
            task = tasks.getFirst();
        }

        model.addAttribute("sections", sections);
        model.addAttribute("section", section);
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", task);

        if (section != null) {
            List<String> ids = tasks.stream().map(SqlTask::id).toList();
            model.addAttribute("solvedCount", progress.solvedCount(ids));
            model.addAttribute("taskCount", tasks.size());
            model.addAttribute("solvedIds", progress.solvedTaskIds(ids));
        }

        if (task != null) {
            int index = tasks.indexOf(task);
            model.addAttribute("hasPrev", index > 0);
            model.addAttribute("hasNext", index >= 0 && index < tasks.size() - 1);
            model.addAttribute("prevTaskId", index > 0 ? tasks.get(index - 1).id() : null);
            model.addAttribute("nextTaskId", index >= 0 && index < tasks.size() - 1 ? tasks.get(index + 1).id() : null);
            model.addAttribute("solved", progress.isSolved(task.id()));
            model.addAttribute("attempts", progress.attempts(task.id()));
            model.addAttribute("sql", progress.lastSql(task.id()).orElse(task.starter()));
            model.addAttribute("starter", task.starter());
            model.addAttribute("schemaText", task.schema().trim() + "\n\n" + task.data().trim());
        }
        return "trainer";
    }
}
