package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 4.2 Сортировка результатов выборки (products)
 *
 * SELECT столбцы FROM таблица ORDER BY столбец ASC | DESC;
 *
 * Задача: выбрать все столбцы из products,
 * отсортировать по price по убыванию.
 *
 * Схема:
 * CREATE TABLE products(
 *     id int not null primary key,
 *     name text,
 *     price int,
 *     count int
 * );
 */
public class Select11OrderByPriceDesc {

    public String query() {
        return """
                SELECT
                """;
    }
}
