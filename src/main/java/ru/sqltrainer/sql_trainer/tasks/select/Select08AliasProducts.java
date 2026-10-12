package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 3.2 Использование псевдонимов для столбцов (products)
 *
 * SELECT имя_столбца AS имя_псевдонима FROM название_таблицы;
 *
 * Задача: выбрать столбцы name и count.
 * Для name используйте псевдоним product_name,
 * для count — actual_count.
 *
 * Схема:
 * CREATE TABLE products(
 *     id int not null primary key,
 *     name text,
 *     price int,
 *     count int
 * );
 */
public class Select08AliasProducts {

    public String query() {
        return """
                SELECT
                """;
    }
}
