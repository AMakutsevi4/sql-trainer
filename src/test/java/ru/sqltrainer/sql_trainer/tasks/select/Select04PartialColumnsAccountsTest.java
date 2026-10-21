package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Select04PartialColumnsAccountsTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Select04PartialColumnsAccounts task = new Select04PartialColumnsAccounts();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}