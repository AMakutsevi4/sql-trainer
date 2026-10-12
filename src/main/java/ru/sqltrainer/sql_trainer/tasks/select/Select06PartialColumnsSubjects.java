package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 2.3 Выбор части полей с их названиями (subjects)
 *
 * SELECT названия_полей_через_запятую FROM название_таблицы;
 * Можно указать не все поля, если они в выборке не нужны.
 *
 * Задача: выбрать все записи из subjects,
 * только поля name и start_date.
 *
 * Схема:
 * CREATE TABLE subjects(
 *     id int not null primary key,
 *     name text,
 *     grade int,
 *     start_date timestamp
 * );
 */
public class Select06PartialColumnsSubjects {

    public String query() {
        return """
                SELECT
                """;
    }
}
