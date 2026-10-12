package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 5.3 Удаление повторяющихся строк — DISTINCT и NULL (subjects)
 *
 * DISTINCT не игнорирует NULL: null считается отдельным значением.
 *
 * Задача: выбрать уникальные пары name и grade.
 * Добавьте ORDER BY grade NULLS LAST.
 *
 * Схема:
 * CREATE TABLE subjects(
 *     id int not null primary key,
 *     name text,
 *     grade int,
 *     start_date timestamp
 * );
 */
public class Select15DistinctNameGrade {

    public String query() {
        return """
                SELECT
                """;
    }
}
