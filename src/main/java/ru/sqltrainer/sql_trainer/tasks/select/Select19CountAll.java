package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.1 Агрегатные функции. COUNT(*)
 *
 * SELECT COUNT(*) FROM таблица;
 * COUNT(*) возвращает число строк, включая null и дубликаты.
 *
 * Задача: вернуть количество всех строк в accounts.
 * Используйте alias: COUNT(*) AS count
 *
 * Схема:
 * CREATE TABLE accounts(
 *     id int not null primary key,
 *     name text,
 *     login text,
 *     password text
 * );
 */
public class Select19CountAll {

    public String query() {
        return """
                SELECT
                """;
    }
}
