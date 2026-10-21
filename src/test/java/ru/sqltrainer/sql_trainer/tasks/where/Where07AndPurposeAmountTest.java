package ru.sqltrainer.sql_trainer.tasks.where;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Where07AndPurposeAmountTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Where07AndPurposeAmount task = new Where07AndPurposeAmount();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}
