package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy34MostFlightsAirlineTest {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy34MostFlightsAirline task = new GroupBy34MostFlightsAirline();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}