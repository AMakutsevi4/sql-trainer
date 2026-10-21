package ru.sqltrainer.sql_trainer.tasks.where;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Where06AndConditionsTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Where06AndConditions task = new Where06AndConditions();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}
