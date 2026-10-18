package ru.sqltrainer.sql_trainer.task;

import org.springframework.stereotype.Service;
import ru.sqltrainer.sql_trainer.engine.QueryTable;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;
import ru.sqltrainer.sql_trainer.progress.AttemptRecorder;

/** Проверяем SQL ученика */
@Service
public class TaskCheckService {

    private final TaskCatalog catalog;
    private final AttemptRecorder attemptRecorder;

    public TaskCheckService(TaskCatalog catalog, AttemptRecorder attemptRecorder) {
        this.catalog = catalog;
        this.attemptRecorder = attemptRecorder;
    }

    /** Выполнить запрос. */
    public CheckResult run(String taskId, String studentSql) {
        return execute(taskId, studentSql, false);
    }

    /** Проверить и записать попытку. */
    public CheckResult check(String taskId, String studentSql) {
        return execute(taskId, studentSql, true);
    }

    private CheckResult execute(String taskId, String studentSql, boolean recordAttempt) {
        SqlTask task = catalog.findById(taskId).orElse(null);
        if (task == null) {
            return CheckResult.error("Задача не найдена: " + taskId);
        }

        try {
            QueryTable table = SqlQueryExecutor.execute(task.schema(), task.data(), studentSql);
            boolean passed = SqlQueryExecutor.normalize(task.expected())
                    .equals(SqlQueryExecutor.normalize(table.csv()));

            if (recordAttempt) {
                attemptRecorder.record(taskId, studentSql, passed);
            }

            return CheckResult.ok(table, passed);
        } catch (Exception e) {
            if (recordAttempt) {
                attemptRecorder.record(taskId, studentSql, false);
            }
            return CheckResult.error(e.getMessage());
        }
    }
}
