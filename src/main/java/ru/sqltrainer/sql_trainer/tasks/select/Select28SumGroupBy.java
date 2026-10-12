package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.10 Агрегатные функции. SUM + GROUP BY
 *
 * SELECT столбец_группировки, SUM(столбец) FROM таблица
 * GROUP BY столбец_группировки;
 *
 * Задача: сумма amount по каждой purpose,
 * отсортировать по purpose по убыванию.
 * В выборке только purpose и sum (alias AS sum).
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
public class Select28SumGroupBy {

    public String query() {
        return """
                SELECT
                """;
    }
}
