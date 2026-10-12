package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.11 Агрегатные функции. AVG и SUM вместе
 *
 * SELECT AVG(столбец), SUM(столбец) FROM таблица;
 *
 * Задача: среднее и сумма по amount.
 * Используйте alias: AS avg, AS sum
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
public class Select29AvgAndSum {

    public String query() {
        return """
                SELECT
                """;
    }
}
