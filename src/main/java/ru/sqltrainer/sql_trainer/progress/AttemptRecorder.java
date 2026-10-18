package ru.sqltrainer.sql_trainer.progress;

/** Сохраняем попытки ученика. */
public interface AttemptRecorder {

    void record(String taskId, String studentSql, boolean passed);
}
