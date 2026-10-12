package ru.sqltrainer.sql_trainer.tasks.where;

/**
 * 1.6. Предложение WHERE с несколькими условиями с помощью AND 2
 *
 * Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
 * в SQL используется WHERE предложение.
 *
 * При этом условия можно комбинировать и проверять несколько условий.
 * Для этого используется логическая операция AND (И). Синтаксис имеет следующий вид:
 * SELECT имена_столбцов_через_запятую FROM название_таблицы
 * WHERE условие1 AND условие2 AND условие3;
 * В итоге запрос вернет только те строки, которые удовлетворяют всем условиям,
 * которые объединены с помощью AND.
 *
 * Для представленной ниже схемы transactions напишите запрос, который выполнит выборку всех записей,
 * удовлетворяющих условиям - столбец purpose не равно 'Vacation' и amount меньше 150 усл. ед.
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
public class Where07AndPurposeAmount {

    public String query() {
        return """
                SELECT
                """;
    }
}
