package ru.sqltrainer.sql_trainer.tasks.join;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.*;

class Join11RightJoinCarsTest {
    @Test
    void solutionMatchesExpected() throws Exception {
        Join11RightJoinCars task = new Join11RightJoinCars();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}