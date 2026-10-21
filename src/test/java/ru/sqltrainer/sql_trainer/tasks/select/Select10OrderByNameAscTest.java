package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Select10OrderByNameAscTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Select10OrderByNameAsc task = new Select10OrderByNameAsc();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}