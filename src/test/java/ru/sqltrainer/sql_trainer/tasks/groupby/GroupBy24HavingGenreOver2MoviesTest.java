package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy24HavingGenreOver2MoviesTest {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy24HavingGenreOver2Movies task = new GroupBy24HavingGenreOver2Movies();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}