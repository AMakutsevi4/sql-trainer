package ru.sqltrainer.sql_trainer.tasks.where;

/**
 * 17. Предложение WHERE с оператором BETWEEN и NOT BETWEEN
 *
 * Для представленной ниже схемы напишите запрос, который будет сочетать в себе 2 условия с помощью AND:
 *
 * - в результаты выборки должны попасть только те строки, в которых в поле amount
 *   находятся значения от 80 до 190
 * - в результаты должны попасть только те строки, в которых в поле payment_date
 *   не находятся значения в пределах от '2022-02-01 12:00:00' до '2022-08-01 12:00:00'.
 *   Вам понадобится оператор NOT BETWEEN.
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
public class Where21BetweenAndNotBetween {

    public String query() {
        return """
                SELECT
                """;
    }
}
