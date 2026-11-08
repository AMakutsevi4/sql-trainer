package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy20WherePriceAboveAvgTest {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy20WherePriceAboveAvg task = new GroupBy20WherePriceAboveAvg();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}