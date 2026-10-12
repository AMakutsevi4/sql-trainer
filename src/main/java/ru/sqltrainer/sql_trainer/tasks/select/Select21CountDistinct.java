package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.3 Агрегатные функции. COUNT(DISTINCT столбец)
 *
 * SELECT COUNT(DISTINCT имя_столбца) FROM таблица;
 * Возвращает количество уникальных ненулевых значений.
 *
 * Задача: количество уникальных ненулевых name в products.
 * Используйте alias: AS count
 *
 * Схема:
 * CREATE TABLE products(
 *     id int not null primary key,
 *     name text,
 *     price int,
 *     count int
 * );
 */
public class Select21CountDistinct {

    public String query() {
        return """
                SELECT
                """;
    }
}
