package ru.sqltrainer.sql_trainer.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sqltrainer.sql_trainer.engine.QueryTable;
import ru.sqltrainer.sql_trainer.progress.ProgressService;
import ru.sqltrainer.sql_trainer.task.CheckResult;
import ru.sqltrainer.sql_trainer.task.SqlTask;
import ru.sqltrainer.sql_trainer.task.TaskCatalog;
import ru.sqltrainer.sql_trainer.task.TaskCheckService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trainer")
public class TrainerApiController {

    private final TaskCatalog catalog;
    private final TaskCheckService checkService;
    private final ProgressService progress;

    public TrainerApiController(TaskCatalog catalog, TaskCheckService checkService, ProgressService progress) {
        this.catalog = catalog;
        this.checkService = checkService;
        this.progress = progress;
    }

    @PostMapping("/run")
    public ResponseEntity<?> run(@RequestBody SqlRequest request) {
        return execute(request, false);
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody SqlRequest request) {
        return execute(request, true);
    }

    private ResponseEntity<?> execute(SqlRequest request, boolean check) {
        if (request == null || request.taskId() == null || request.taskId().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "ok", false,
                    "status", "Задача не выбрана",
                    "tone", "is-bad"
            ));
        }
        SqlTask task = catalog.findById(request.taskId()).orElse(null);
        if (task == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "ok", false,
                    "status", "Задача не найдена",
                    "tone", "is-bad"
            ));
        }

        String sql = request.sql() == null ? "" : request.sql();
        CheckResult outcome = check ? checkService.check(task.id(), sql) : checkService.run(task.id(), sql);

        if (outcome.hasError()) {
            return ResponseEntity.ok(Map.of(
                    "ok", false,
                    "status", "Ошибка SQL",
                    "tone", "is-bad",
                    "error", outcome.errorMessage() == null ? "" : outcome.errorMessage(),
                    "solved", progress.isSolved(task.id()),
                    "attempts", progress.attempts(task.id())
            ));
        }

        if (!check) {
            return ResponseEntity.ok(Map.of(
                    "ok", true,
                    "status", "Запрос выполнен",
                    "tone", "is-info",
                    "result", toDto(outcome.table()),
                    "solved", progress.isSolved(task.id()),
                    "attempts", progress.attempts(task.id())
            ));
        }

        if (outcome.passed()) {
            return ResponseEntity.ok(Map.of(
                    "ok", true,
                    "passed", true,
                    "status", "Верно",
                    "tone", "is-ok",
                    "result", toDto(outcome.table()),
                    "solved", true,
                    "attempts", progress.attempts(task.id())
            ));
        }

        return ResponseEntity.ok(Map.of(
                "ok", true,
                "passed", false,
                "status", "Неверно",
                "tone", "is-bad",
                "result", toDto(outcome.table()),
                "expected", toDto(QueryTable.fromCsv(task.expected())),
                "solved", progress.isSolved(task.id()),
                "attempts", progress.attempts(task.id())
        ));
    }

    private static Map<String, Object> toDto(QueryTable table) {
        return Map.of(
                "columns", table.columns(),
                "rows", table.rows()
        );
    }

    public record SqlRequest(String taskId, String sql) {
    }
}
