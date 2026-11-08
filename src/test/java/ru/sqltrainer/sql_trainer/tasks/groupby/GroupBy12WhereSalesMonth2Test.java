package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy12WhereSalesMonth2Test {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy12WhereSalesMonth2 task = new GroupBy12WhereSalesMonth2();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}