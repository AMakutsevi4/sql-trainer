package ru.sqltrainer.sql_trainer.ui;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
public class TaskCatalog {

    private static final Pattern TEXT_BLOCK = Pattern.compile(
            "String\\s+(schema|data|expected)\\s*=\\s*\"\"\"(.*?)\"\"\"",
            Pattern.DOTALL
    );

    private final List<SqlTask> tasks;

    public TaskCatalog() {
        this.tasks = loadAll();
    }

    public List<String> sections() {
        return tasks.stream().map(SqlTask::section).distinct().sorted().toList();
    }

    public List<SqlTask> bySection(String section) {
        return tasks.stream().filter(t -> t.section().equals(section)).toList();
    }

    public Optional<SqlTask> findById(String id) {
        return tasks.stream().filter(t -> t.id().equals(id)).findFirst();
    }

    private List<SqlTask> loadAll() {
        Path root = findProjectRoot();
        List<SqlTask> result = new ArrayList<>();
        result.addAll(loadPackage(root, "select", "SELECT"));
        result.addAll(loadPackage(root, "where", "WHERE"));
        result.sort(Comparator.comparing(SqlTask::id));
        return List.copyOf(result);
    }

    private List<SqlTask> loadPackage(Path root, String folder, String section) {
        Path dir = root.resolve("src/main/java/ru/sqltrainer/sql_trainer/tasks").resolve(folder);
        if (!Files.isDirectory(dir)) {
            return List.of();
        }

        try (Stream<Path> files = Files.list(dir)) {
            return files
                    .filter(p -> p.getFileName().toString().endsWith(".java"))
                    .map(p -> loadOne(root, folder, section, p))
                    .flatMap(Optional::stream)
                    .toList();
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать задачи из " + dir, e);
        }
    }

    private Optional<SqlTask> loadOne(Path root, String folder, String section, Path taskFile) {
        String className = taskFile.getFileName().toString().replace(".java", "");
        Path testFile = root.resolve("src/test/java/ru/sqltrainer/sql_trainer/tasks")
                .resolve(folder)
                .resolve(className + "Test.java");

        if (!Files.isRegularFile(testFile)) {
            return Optional.empty();
        }

        try {
            String taskSource = Files.readString(taskFile);
            String testSource = Files.readString(testFile);

            String schema = extractBlock(testSource, "schema");
            String data = extractBlock(testSource, "data");
            String expected = extractBlock(testSource, "expected");
            if (schema.isBlank() || data.isBlank() || expected.isBlank()) {
                return Optional.empty();
            }

            return Optional.of(new SqlTask(
                    className,
                    section,
                    extractTitle(taskSource, className),
                    extractDescription(taskSource),
                    schema,
                    data,
                    invokeQuery(folder, className),
                    expected
            ));
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка загрузки задачи " + className, e);
        }
    }

    private String invokeQuery(String folder, String className) throws Exception {
        Class<?> type = Class.forName("ru.sqltrainer.sql_trainer.tasks." + folder + "." + className);
        Object instance = type.getDeclaredConstructor().newInstance();
        Method query = type.getMethod("query");
        return String.valueOf(query.invoke(instance));
    }

    private static String extractBlock(String source, String name) {
        Matcher matcher = TEXT_BLOCK.matcher(source);
        while (matcher.find()) {
            if (name.equals(matcher.group(1))) {
                return matcher.group(2).stripIndent().trim();
            }
        }
        return "";
    }

    private static String extractTitle(String source, String fallback) {
        int start = source.indexOf("/**");
        if (start < 0) {
            return fallback;
        }
        int end = source.indexOf("*/", start);
        if (end < 0) {
            return fallback;
        }
        for (String line : source.substring(start, end).split("\n")) {
            String cleaned = line.replace("/**", "").replace("*", "").trim();
            if (!cleaned.isEmpty()) {
                return cleaned;
            }
        }
        return fallback;
    }

    private static String extractDescription(String source) {
        int start = source.indexOf("/**");
        if (start < 0) {
            return "";
        }
        int end = source.indexOf("*/", start);
        if (end < 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String line : source.substring(start + 3, end).split("\n")) {
            String cleaned = line.replaceFirst("^\\s*\\*", "").trim();
            sb.append(cleaned).append('\n');
        }
        return sb.toString().trim();
    }

    private static Path findProjectRoot() {
        Path current = Path.of("").toAbsolutePath();
        Path p = current;
        while (p != null) {
            if (Files.isRegularFile(p.resolve("pom.xml"))) {
                return p;
            }
            p = p.getParent();
        }
        throw new IllegalStateException(
                "Не найден pom.xml от " + current + ". Запускай приложение из корня проекта."
        );
    }
}
