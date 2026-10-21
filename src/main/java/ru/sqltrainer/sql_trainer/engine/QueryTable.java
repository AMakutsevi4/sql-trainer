package ru.sqltrainer.sql_trainer.engine;

import java.util.ArrayList;
import java.util.List;

public record QueryTable(
        List<String> columns,
        List<List<String>> rows,
        String csv
) {
    public static QueryTable empty() {
        return new QueryTable(List.of(), List.of(), "");
    }

    public static QueryTable fromCsv(String csv) {
        String normalized = SqlQueryExecutor.normalize(csv);
        if (normalized.isEmpty()) {
            return empty();
        }
        String[] lines = normalized.split("\n");
        List<String> columns = splitRow(lines[0]);
        List<List<String>> rows = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            if (!lines[i].isBlank()) {
                rows.add(splitRow(lines[i]));
            }
        }
        return new QueryTable(columns, rows, normalized + "\n");
    }

    private static List<String> splitRow(String line) {
        List<String> cells = new ArrayList<>();
        String[] parts = line.split(";", -1);
        for (String part : parts) {
            if (part.isEmpty() && cells.size() == parts.length - 1) {
                continue;
            }
            cells.add(part);
        }
        if (!cells.isEmpty() && cells.getLast().isEmpty()) {
            cells.removeLast();
        }
        return cells;
    }
}
