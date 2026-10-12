package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 1.2 Выбор всех полей с их названиями (products)
 *
 * SELECT названия_полей_через_запятую FROM название_таблицы;
 * После последнего поля запятая не ставится.
 *
 * Задача: выбрать все записи из таблицы products,
 * в выборке должны быть данные всех полей таблицы.
 *
 * Схема:
 * CREATE TABLE products(
 *     id int not null primary key,
 *     name text,
 *     price int,
 *     count int
 * );
 */
public class Select02AllColumnsProducts {

    public String query() {
        return """
                SELECT
                """;
    }
}
