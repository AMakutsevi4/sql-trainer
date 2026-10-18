package ru.sqltrainer.sql_trainer.progress;

import org.springframework.stereotype.Component;

/** Попытки в прогресс сессии. */
@Component
public class NoOpAttemptRecorder implements AttemptRecorder {

    @Override
    public void record(String taskId, String studentSql, boolean passed) {
        TraineeProgress.current().registerAttempt(taskId, passed);
    }
}
