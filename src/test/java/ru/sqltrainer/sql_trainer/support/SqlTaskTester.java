package ru.sqltrainer.sql_trainer.support;

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
        String actual = SqlQueryExecutor.execute(schemaSql, dataSql, studentQuery);
        assertEquals(
                SqlQueryExecutor.normalize(expectedCsv),
                SqlQueryExecutor.normalize(actual)
        );
    }
}
