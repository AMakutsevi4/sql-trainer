package ru.sqltrainer.sql_trainer.tasks.join;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Join20NaturalRightJoinCarsTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Join20NaturalRightJoinCars task = new Join20NaturalRightJoinCars();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}