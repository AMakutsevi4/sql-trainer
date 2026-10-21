package ru.sqltrainer.sql_trainer.progress;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ru.sqltrainer.sql_trainer.user.AppUser;

import java.time.Instant;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "task_id", nullable = false, length = 64)
    private String taskId;

    @Column(name = "sql_text", nullable = false, columnDefinition = "TEXT")
    private String sqlText;

    @Column(nullable = false)
    private boolean passed;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected Submission() {
    }

    public Submission(AppUser user, String taskId, String sqlText, boolean passed) {
        this.user = user;
        this.taskId = taskId;
        this.sqlText = sqlText;
        this.passed = passed;
    }

    public Long getId() {
        return id;
    }

    public AppUser getUser() {
        return user;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getSqlText() {
        return sqlText;
    }

    public boolean isPassed() {
        return passed;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
