package ru.sqltrainer.sql_trainer.exam;

import java.util.List;
import java.util.Set;

public record ExamQuestion(
        String id,
        String section,
        int order,
        String title,
        String prompt,
        boolean multiSelect,
        List<ExamOption> options,
        Set<String> correctOptionIds
) {
}
