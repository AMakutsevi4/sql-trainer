package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.9 Агрегатные функции. SUM(DISTINCT)
 *
 * SELECT SUM(DISTINCT имя_столбца) FROM таблица;
 *
 * Задача: сумма уникальных значений amount.
 * Используйте alias: AS sum
 *
 * Схема:
 * CREATE TABLE transactions(
 *     id int not null primary key,
 *     purpose text,
 *     client_id int,
 *     amount int,
 *     payment_date timestamp
 * );
 */
public class Select27SumDistinct {

    public String query() {
        return """
                SELECT
                """;
    }
}
