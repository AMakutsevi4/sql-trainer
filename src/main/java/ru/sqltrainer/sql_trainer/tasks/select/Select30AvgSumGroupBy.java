package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.12 Агрегатные функции. AVG и SUM + GROUP BY
 *
 * Задача: среднее и сумма amount по каждой purpose,
 * отсортировать по purpose по возрастанию.
 * В выборке: purpose, avg, sum.
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
public class Select30AvgSumGroupBy {

    public String query() {
        return """
                SELECT
                """;
    }
}
