package ru.sqltrainer.sql_trainer.tasks.join;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Join18NaturalJoinColorsTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Join18NaturalJoinColors task = new Join18NaturalJoinColors();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}