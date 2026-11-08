package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy38RevenueMonth3Test {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy38RevenueMonth3 task = new GroupBy38RevenueMonth3();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}