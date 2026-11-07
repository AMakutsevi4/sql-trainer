package ru.sqltrainer.sql_trainer.tasks.join;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Join13FullOuterJoinColorsTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Join13FullOuterJoinColors task = new Join13FullOuterJoinColors();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}