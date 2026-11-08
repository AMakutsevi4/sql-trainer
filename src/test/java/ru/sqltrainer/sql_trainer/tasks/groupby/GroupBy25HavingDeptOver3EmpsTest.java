package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy25HavingDeptOver3EmpsTest {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy25HavingDeptOver3Emps task = new GroupBy25HavingDeptOver3Emps();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}