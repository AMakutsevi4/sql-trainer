package ru.sqltrainer.sql_trainer.tasks.where;

/**
 * 1. Where id
 *
 * Теория. Выборка по where id.
 *
 * Нужно получить запись с ID = 1;
 *
 * Схема:
 * CREATE TABLE account(
 *     id int not null primary key,
 *     name text
 * );
 */
public class Where01ById {

    public String query() {
        return """
                SELECT
                """;
    }
}
