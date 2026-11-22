package ru.sqltrainer.sql_trainer.web;

import ru.sqltrainer.sql_trainer.exam.ExamCheckService.ExamSessionResult;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExamSession implements Serializable {

    private String examId;
    private int index;
    private Instant deadline;
    private final Map<String, Set<String>> answers = new HashMap<>();
    private ExamSessionResult result;
    private boolean timedOut;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public Map<String, Set<String>> getAnswers() {
        return answers;
    }

    public ExamSessionResult getResult() {
        return result;
    }

    public void setResult(ExamSessionResult result) {
        this.result = result;
    }

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }
}
