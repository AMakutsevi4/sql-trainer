package ru.sqltrainer.sql_trainer.tasks.where;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Where21BetweenAndNotBetweenTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Where21BetweenAndNotBetween task = new Where21BetweenAndNotBetween();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}
