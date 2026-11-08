package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy15WhereScoresAbove85Test {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy15WhereScoresAbove85 task = new GroupBy15WhereScoresAbove85();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}