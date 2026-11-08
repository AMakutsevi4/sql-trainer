package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy22HavingAvgPriceOver20Test {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy22HavingAvgPriceOver20 task = new GroupBy22HavingAvgPriceOver20();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}