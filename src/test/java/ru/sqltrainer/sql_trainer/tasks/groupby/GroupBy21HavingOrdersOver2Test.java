package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy21HavingOrdersOver2Test {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy21HavingOrdersOver2 task = new GroupBy21HavingOrdersOver2();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}