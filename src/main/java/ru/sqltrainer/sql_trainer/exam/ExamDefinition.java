package ru.sqltrainer.sql_trainer.exam;

import java.util.List;

public record ExamDefinition(
        String id,
        String title,
        String summary,
        List<String> topics,
        int timeLimitMinutes,
        int retakeCooldownHours,
        String attemptTaskId,
        List<ExamQuestion> questions
) {
    public String introText() {
        String topicsBlock = topics.stream()
                .map(t -> "• " + t)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
        return """
                %s

                Темы:
                %s

                Вопросов: %d
                Время: %d мин
                Повтор: через %d ч
                """.formatted(
                summary,
                topicsBlock,
                questions.size(),
                timeLimitMinutes,
                retakeCooldownHours
        );
    }
}
