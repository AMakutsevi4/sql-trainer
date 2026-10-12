package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 6.3 Предложение DISTINCT ON (products) — PostgreSQL
 *
 * Если в ORDER BY несколько столбцов, самыми левыми
 * должны идти столбцы из DISTINCT ON.
 *
 * Задача: уникальные строки по price,
 * в выборке: name, price, count.
 * Сортировка: ORDER BY price, name, count.
 *
 * Внимание: DISTINCT ON есть только в PostgreSQL.
 * Тест помечен @Disabled на H2.
 *
 * Схема:
 * CREATE TABLE products(
 *     id int not null primary key,
 *     name text,
 *     price int,
 *     count int
 * );
 */
public class Select18DistinctOnPrice {

    public String query() {
        return """
                SELECT
                """;
    }
}
