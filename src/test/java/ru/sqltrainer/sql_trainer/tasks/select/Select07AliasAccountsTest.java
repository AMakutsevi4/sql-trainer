package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Select07AliasAccountsTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Select07AliasAccounts task = new Select07AliasAccounts();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}