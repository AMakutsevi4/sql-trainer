package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.14 Агрегатные функции. MAX + GROUP BY
 *
 * SELECT столбец_группировки, MAX(столбец) FROM таблица
 * GROUP BY столбец_группировки;
 *
 * Задача: максимум amount для каждой purpose,
 * отсортировать по purpose.
 * Используйте alias: AS max
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
public class Select32MaxGroupBy {

    public String query() {
        return """
                SELECT
                """;
    }
}
