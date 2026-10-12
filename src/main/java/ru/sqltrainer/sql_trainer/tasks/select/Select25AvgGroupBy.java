package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.7 Агрегатные функции. AVG + GROUP BY
 *
 * SELECT столбец_группировки, AVG(столбец) FROM таблица
 * GROUP BY столбец_группировки;
 *
 * Задача: среднее amount по каждой purpose,
 * отсортировать по purpose по возрастанию.
 * В выборке только purpose и avg (alias AS avg).
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
public class Select25AvgGroupBy {

    public String query() {
        return """
                SELECT
                """;
    }
}
