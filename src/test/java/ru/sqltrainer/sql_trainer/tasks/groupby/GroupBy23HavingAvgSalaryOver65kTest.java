package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy23HavingAvgSalaryOver65kTest {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy23HavingAvgSalaryOver65k task = new GroupBy23HavingAvgSalaryOver65k();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}