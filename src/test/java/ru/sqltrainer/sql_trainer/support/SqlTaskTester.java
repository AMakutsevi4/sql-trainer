package ru.sqltrainer.sql_trainer.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.UUID;

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
        String url = "jdbc:h2:mem:sqltrainer_" + UUID.randomUUID()
                + ";MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE";
        try (Connection connection = DriverManager.getConnection(url, "sa", "")) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(schemaSql);
                for (String insert : dataSql.split(";")) {
                    String sql = insert.trim();
                    if (!sql.isEmpty()) {
                        statement.execute(sql);
                    }
                }

                try (ResultSet resultSet = statement.executeQuery(studentQuery)) {
                    String actual = toCsv(resultSet);
                    assertEquals(normalize(expectedCsv), normalize(actual));
                }
            }
        }
    }

    private static String toCsv(ResultSet resultSet) throws Exception {
        ResultSetMetaData meta = resultSet.getMetaData();
        int columnCount = meta.getColumnCount();

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= columnCount; i++) {
            sb.append(meta.getColumnLabel(i).toLowerCase()).append(';');
        }
        sb.append('\n');

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                Object value = resultSet.getObject(i);
                sb.append(value == null ? "null" : value).append(';');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private static String normalize(String csv) {
        return csv.replace("\r\n", "\n").trim();
    }
}
