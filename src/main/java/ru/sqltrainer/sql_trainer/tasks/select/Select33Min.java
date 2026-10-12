package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.15 Агрегатные функции. MIN
 *
 * SELECT MIN(имя_столбца) FROM таблица;
 *
 * Задача: найти минимальное значение amount.
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
public class Select33Min {

    public String query() {
        return """
                SELECT
                """;
    }
}
