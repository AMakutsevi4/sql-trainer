package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy27HavingAvgRatingOver9Test {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy27HavingAvgRatingOver9 task = new GroupBy27HavingAvgRatingOver9();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}