package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.4 Агрегатные функции. COUNT + GROUP BY
 *
 * SELECT столбец, COUNT(столбец) FROM таблица GROUP BY столбец;
 *
 * Задача: количество фильмов в каждой категории (category_id).
 * Отсортируйте по category_id по возрастанию.
 * Используйте alias: AS count
 *
 * Схема:
 * CREATE TABLE movies(
 *     id int not null primary key,
 *     name text,
 *     producer text,
 *     category_id int
 * );
 */
public class Select22CountGroupBy {

    public String query() {
        return """
                SELECT
                """;
    }
}
