package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 2.1 Выбор части полей с их названиями (accounts)
 *
 * SELECT названия_полей_через_запятую FROM название_таблицы;
 * Можно указать не все поля, если они в выборке не нужны.
 *
 * Задача: выбрать все записи из accounts,
 * только поля name и login.
 *
 * Схема:
 * CREATE TABLE accounts(
 *     id int not null primary key,
 *     name text,
 *     login text,
 *     password text
 * );
 */
public class Select04PartialColumnsAccounts {

    public String query() {
        return """
                SELECT
                """;
    }
}
