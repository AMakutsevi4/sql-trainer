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

                Рассмотренные темы:
                %s

                В экзамен входят %d вопросов. Вопросы составлены в виде заданий, где надо выбрать один или несколько правильных ответов.

                После прохождения экзамена вы увидите результат в процентах и список вопросов с отметкой верно/неверно.

                На решение даётся %d минут. Когда время истечёт, экзамен завершится автоматически.

                При прохождении экзамена используйте блокнот и карандаш. Не стоит набирать и выполнять код из заданий, чтобы получить правильный ответ. Все вопросы можно решить в уме.

                Повторно пройти экзамен можно через %d часа после предыдущей попытки.

                Для начала экзамена нажмите кнопку «Пройти».
                """.formatted(
                summary,
                topicsBlock,
                questions.size(),
                timeLimitMinutes,
                retakeCooldownHours
        );
    }
}
