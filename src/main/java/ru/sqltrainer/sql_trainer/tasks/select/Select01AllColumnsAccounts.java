package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 1.1 Выбор всех полей с их названиями (accounts)
 *
 * Чтобы выбрать записи из таблицы используется синтаксис:
 * SELECT названия_полей_через_запятую FROM название_таблицы;
 * После последнего поля запятая не ставится.
 *
 * Задача: выбрать все записи из таблицы accounts,
 * в выборке должны быть данные всех полей таблицы.
 *
 * Схема:
 * CREATE TABLE accounts(
 *     id int not null primary key,
 *     name text,
 *     login text,
 *     password text
 * );
 */
public class Select01AllColumnsAccounts {

    public String query() {
        return """
                SELECT
                """;
    }
}
