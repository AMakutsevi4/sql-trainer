package ru.sqltrainer.sql_trainer.tasks.join;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.*;

class Join06InnerJoinThreeTablesTest {

    @Test
    void solutionMatchesExpected() throws Exception {
        Join06InnerJoinThreeTables task = new Join06InnerJoinThreeTables();
        assertTrue(SqlQueryExecutor.matches(
                task.schema(),
                task.data(),
                task.solution(),
                task.expected()
        ));
    }
}