package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy28HavingSumOver1500Test {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy28HavingSumOver1500 task = new GroupBy28HavingSumOver1500();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}