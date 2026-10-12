package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.5 Агрегатные функции. AVG
 *
 * SELECT AVG(имя_столбца) FROM таблица;
 *
 * Задача: среднее значение по столбцу amount.
 * Используйте alias: AS avg
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
public class Select23Avg {

    public String query() {
        return """
                SELECT
                """;
    }
}
