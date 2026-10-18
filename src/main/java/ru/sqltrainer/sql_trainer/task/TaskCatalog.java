package ru.sqltrainer.sql_trainer.task;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class TaskCatalog {

    private final List<SqlTask> tasks;

    public TaskCatalog(List<SqlTask> tasks) {
        this.tasks = tasks.stream()
                .sorted(Comparator.comparing(SqlTask::section).thenComparingInt(SqlTask::order))
                .toList();
    }

    public List<String> sections() {
        return tasks.stream().map(SqlTask::section).distinct().toList();
    }

    public List<SqlTask> bySection(String section) {
        return tasks.stream().filter(t -> t.section().equals(section)).toList();
    }

    public Optional<SqlTask> findById(String id) {
        return tasks.stream().filter(t -> t.id().equals(id)).findFirst();
    }

    public List<SqlTask> all() {
        return tasks;
    }
}
