package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy08AvgRatingByGenreTest {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy08AvgRatingByGenre task = new GroupBy08AvgRatingByGenre();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}