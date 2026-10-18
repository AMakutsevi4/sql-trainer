package ru.sqltrainer.sql_trainer.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** SQL на H2 */
public final class SqlQueryExecutor {

    private SqlQueryExecutor() {
    }

    public static QueryTable execute(String schemaSql, String dataSql, String studentQuery) throws Exception {
        String url = "jdbc:h2:mem:sqltrainer_" + UUID.randomUUID()
                + ";MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE";

        try (Connection connection = DriverManager.getConnection(url, "sa", "");
             Statement statement = connection.createStatement()) {

            statement.execute(schemaSql);
            for (String insert : dataSql.split(";")) {
                String sql = insert.trim();
                if (!sql.isEmpty()) {
                    statement.execute(sql);
                }
            }

            try (ResultSet resultSet = statement.executeQuery(studentQuery)) {
                return toTable(resultSet);
            }
        }
    }

    public static boolean matches(String schemaSql, String dataSql, String query, String expectedCsv)
            throws Exception {
        QueryTable actual = execute(schemaSql, dataSql, query);
        return normalize(expectedCsv).equals(normalize(actual.csv()));
    }

    public static String normalize(String csv) {
        return csv.replace("\r\n", "\n").trim();
    }

    private static QueryTable toTable(ResultSet resultSet) throws Exception {
        ResultSetMetaData meta = resultSet.getMetaData();
        int columnCount = meta.getColumnCount();

        List<String> columns = new ArrayList<>();
        StringBuilder csv = new StringBuilder();
        for (int i = 1; i <= columnCount; i++) {
            String label = meta.getColumnLabel(i).toLowerCase();
            columns.add(label);
            csv.append(label).append(';');
        }
        csv.append('\n');

        List<List<String>> rows = new ArrayList<>();
        while (resultSet.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                Object value = resultSet.getObject(i);
                String text = value == null ? "null" : String.valueOf(value);
                row.add(text);
                csv.append(text).append(';');
            }
            csv.append('\n');
            rows.add(row);
        }
        return new QueryTable(List.copyOf(columns), List.copyOf(rows), csv.toString());
    }
}
