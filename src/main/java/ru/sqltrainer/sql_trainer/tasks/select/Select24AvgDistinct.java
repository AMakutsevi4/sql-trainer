package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.6 Агрегатные функции. AVG(DISTINCT)
 *
 * SELECT AVG(DISTINCT имя_столбца) FROM таблица;
 * Считает среднее только среди уникальных значений.
 *
 * Задача: среднее по уникальным значениям amount.
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
public class Select24AvgDistinct {

    public String query() {
        return """
                SELECT
                """;
    }
}
