package ru.sqltrainer.sql_trainer.task;

import ru.sqltrainer.sql_trainer.engine.QueryTable;

public record CheckResult(
        boolean passed,
        QueryTable table,
        String errorMessage
) {
    public static CheckResult ok(QueryTable table, boolean passed) {
        return new CheckResult(passed, table, null);
    }

    public static CheckResult error(String message) {
        return new CheckResult(false, QueryTable.empty(), message);
    }

    public boolean hasError() {
        return errorMessage != null;
    }
}
