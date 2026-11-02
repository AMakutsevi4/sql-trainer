package ru.sqltrainer.sql_trainer.tasks.join;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.*;

class Join02InnerJoinCarsTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Join02InnerJoinCars task = new Join02InnerJoinCars();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}