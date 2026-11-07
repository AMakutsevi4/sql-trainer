package ru.sqltrainer.sql_trainer.engine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SqlQueryExecutor {

    private static final int NUMERIC_SCALE = 10;

    private SqlQueryExecutor() {
    }

    public static QueryTable execute(String schemaSql, String dataSql, String studentQuery) throws Exception {
        String url = env("SQLTRAINER_ENGINE_URL", "jdbc:postgresql://localhost:5433/sqltrainer");
        String user = env("SQLTRAINER_ENGINE_USER", "sqltrainer");
        String password = env("SQLTRAINER_ENGINE_PASSWORD", "sqltrainer");

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                String schema = "task_" + UUID.randomUUID().toString().replace("-", "");
                statement.execute("CREATE SCHEMA " + schema);
                statement.execute("SET search_path TO " + schema);

                for (String sql : splitSql(schemaSql)) {
                    statement.execute(sql);
                }
                for (String sql : splitSql(dataSql)) {
                    statement.execute(sql);
                }

                try (ResultSet resultSet = statement.executeQuery(studentQuery)) {
                    return toTable(resultSet);
                }
            } finally {
                connection.rollback();
            }
        }
    }

    public static boolean matches(String schemaSql, String dataSql, String query, String expectedCsv)
            throws Exception {
        QueryTable actual = execute(schemaSql, dataSql, query);
        return normalize(expectedCsv).equals(normalize(actual.csv()));
    }

    public static String normalize(String csv) {
        String trimmed = csv.replace("\r\n", "\n").trim();
        if (trimmed.isEmpty()) {
            return trimmed;
        }
        StringBuilder out = new StringBuilder();
        String[] lines = trimmed.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (i > 0) {
                out.append('\n');
            }
            String[] cells = lines[i].split(";", -1);
            for (int j = 0; j < cells.length; j++) {
                if (j > 0) {
                    out.append(';');
                }
                out.append(normalizeCell(cells[j]));
            }
        }
        return out.toString();
    }

    private static String normalizeCell(String cell) {
        if (cell == null || cell.isEmpty() || "null".equals(cell)) {
            return cell;
        }
        try {
            return formatDecimal(new BigDecimal(cell));
        } catch (NumberFormatException ignored) {
            return cell;
        }
    }

    private static String formatDecimal(BigDecimal value) {
        return value.setScale(NUMERIC_SCALE, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString();
    }

    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            value = System.getProperty(key);
        }
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private static List<String> splitSql(String sql) {
        List<String> parts = new ArrayList<>();
        for (String part : sql.split(";")) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                parts.add(trimmed);
            }
        }
        return parts;
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
                String text = formatValue(value);
                row.add(text);
                csv.append(text).append(';');
            }
            csv.append('\n');
            rows.add(row);
        }
        return new QueryTable(List.copyOf(columns), List.copyOf(rows), csv.toString());
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof BigDecimal decimal) {
            return formatDecimal(decimal);
        }
        if (value instanceof Double || value instanceof Float) {
            return formatDecimal(BigDecimal.valueOf(((Number) value).doubleValue()));
        }
        return String.valueOf(value);
    }
}
