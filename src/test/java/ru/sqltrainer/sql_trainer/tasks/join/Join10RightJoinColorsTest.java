package ru.sqltrainer.sql_trainer.tasks.join;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.*;

class Join10RightJoinColorsTest {
    @Test
    void solutionMatchesExpected() throws Exception {
        Join10RightJoinColors task = new Join10RightJoinColors();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}