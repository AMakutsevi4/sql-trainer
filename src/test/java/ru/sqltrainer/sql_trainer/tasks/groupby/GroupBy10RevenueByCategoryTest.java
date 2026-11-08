package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy10RevenueByCategoryTest {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy10RevenueByCategory task = new GroupBy10RevenueByCategory();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}