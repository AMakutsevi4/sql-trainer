package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.13 Агрегатные функции. MAX
 *
 * SELECT MAX(имя_столбца) FROM таблица;
 *
 * Задача: найти максимальное значение amount.
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
public class Select31Max {

    public String query() {
        return """
                SELECT
                """;
    }
}
