package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 5.2 Удаление повторяющихся строк — DISTINCT (products)
 *
 * SELECT DISTINCT столбцы FROM таблица;
 * Если указано несколько столбцов — уникальные комбинации этих столбцов.
 *
 * Задача: выбрать уникальные пары name и price,
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
public class Select14DistinctNamePrice {

    public String query() {
        return """
                SELECT
                """;
    }
}
