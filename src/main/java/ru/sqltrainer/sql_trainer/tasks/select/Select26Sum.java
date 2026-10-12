package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.8 Агрегатные функции. SUM
 *
 * SELECT SUM(имя_столбца) FROM таблица;
 * SUM игнорирует NULL.
 *
 * Задача: общая сумма значений amount.
 * Используйте alias: AS sum
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
public class Select26Sum {

    public String query() {
        return """
                SELECT
                """;
    }
}
