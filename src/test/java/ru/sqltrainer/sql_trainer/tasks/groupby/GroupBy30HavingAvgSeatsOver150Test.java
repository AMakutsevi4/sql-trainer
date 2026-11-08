package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy30HavingAvgSeatsOver150Test {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy30HavingAvgSeatsOver150 task = new GroupBy30HavingAvgSeatsOver150();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}