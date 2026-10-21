package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Select16DistinctOnNamePersonTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Select16DistinctOnNamePerson task = new Select16DistinctOnNamePerson();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}