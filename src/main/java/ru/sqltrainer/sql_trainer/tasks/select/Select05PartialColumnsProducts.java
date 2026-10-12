package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 2.2 Выбор части полей с их названиями (products)
 *
 * SELECT названия_полей_через_запятую FROM название_таблицы;
 * Можно указать не все поля, если они в выборке не нужны.
 *
 * Задача: выбрать все записи из products,
 * только поля id и price.
 *
 * Схема:
 * CREATE TABLE products(
 *     id int not null primary key,
 *     name text,
 *     price int,
 *     count int
 * );
 */
public class Select05PartialColumnsProducts {

    public String query() {
        return """
                SELECT
                """;
    }
}
