package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Select06PartialColumnsSubjectsTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Select06PartialColumnsSubjects task = new Select06PartialColumnsSubjects();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}