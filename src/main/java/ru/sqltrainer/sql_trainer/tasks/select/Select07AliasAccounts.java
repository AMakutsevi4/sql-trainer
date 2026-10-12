package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 3.1 Использование псевдонимов для столбцов (accounts)
 *
 * SELECT имя_столбца AS имя_псевдонима FROM название_таблицы;
 * Псевдонимы пишутся в стиле snake_case.
 *
 * Задача: выбрать столбцы name и password.
 * Для name используйте псевдоним person_name,
 * для password — account_password.
 *
 * Схема:
 * CREATE TABLE accounts(
 *     id int not null primary key,
 *     name text,
 *     login text,
 *     password text
 * );
 */
public class Select07AliasAccounts {

    public String query() {
        return """
                SELECT
                """;
    }
}
