package ru.sqltrainer.sql_trainer.support;

import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class SqlTaskTester {

    private SqlTaskTester() {
    }

    public static void assertQuery(
            String schemaSql,
            String dataSql,
            String studentQuery,
            String expectedCsv
    ) throws Exception {
        String actual = SqlQueryExecutor.execute(schemaSql, dataSql, studentQuery).csv();
        assertEquals(
                SqlQueryExecutor.normalize(expectedCsv),
                SqlQueryExecutor.normalize(actual)
        );
    }
}
