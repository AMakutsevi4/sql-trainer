package ru.sqltrainer.sql_trainer.progress;

import com.vaadin.flow.server.VaadinSession;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Прогресс ученика в рамках сессии. */
public final class TraineeProgress {

    private final Set<String> solved = new HashSet<>();
    private final Map<String, Integer> attempts = new HashMap<>();
    private final Map<String, Instant> openedAt = new HashMap<>();

    private TraineeProgress() {
    }

    public static TraineeProgress current() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session == null) {
            return new TraineeProgress();
        }
        TraineeProgress progress = session.getAttribute(TraineeProgress.class);
        if (progress == null) {
            progress = new TraineeProgress();
            session.setAttribute(TraineeProgress.class, progress);
        }
        return progress;
    }

    public void openTask(String taskId) {
        openedAt.putIfAbsent(taskId, Instant.now());
    }

    public void registerAttempt(String taskId, boolean passed) {
        attempts.merge(taskId, 1, Integer::sum);
        if (passed) {
            solved.add(taskId);
        }
    }

    public boolean isSolved(String taskId) {
        return solved.contains(taskId);
    }

    public int attempts(String taskId) {
        return attempts.getOrDefault(taskId, 0);
    }

    public Duration timeSpent(String taskId) {
        Instant start = openedAt.get(taskId);
        if (start == null) {
            return Duration.ZERO;
        }
        return Duration.between(start, Instant.now());
    }

    public long solvedIn(Iterable<String> taskIds) {
        long count = 0;
        for (String id : taskIds) {
            if (solved.contains(id)) {
                count++;
            }
        }
        return count;
    }
}
