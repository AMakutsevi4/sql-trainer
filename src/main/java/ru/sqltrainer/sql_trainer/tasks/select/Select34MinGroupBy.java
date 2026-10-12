package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.16 Агрегатные функции. MIN + GROUP BY
 *
 * SELECT столбец_группировки, MIN(столбец) FROM таблица
 * GROUP BY столбец_группировки;
 *
 * Задача: минимум amount для каждой purpose,
 * отсортировать по purpose.
 * Используйте alias: AS min
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
public class Select34MinGroupBy {

    public String query() {
        return """
                SELECT
                """;
    }
}
