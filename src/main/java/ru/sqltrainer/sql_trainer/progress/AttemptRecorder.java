package ru.sqltrainer.sql_trainer.progress;

public interface AttemptRecorder {

    void record(String taskId, String studentSql, boolean passed);
}
