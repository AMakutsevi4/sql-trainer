package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 4.1 Сортировка результатов выборки (accounts)
 *
 * SELECT столбцы FROM таблица ORDER BY столбец ASC | DESC;
 * ASC — по возрастанию (по умолчанию), DESC — по убыванию.
 *
 * Задача: выбрать все столбцы из accounts,
 * отсортировать по name по возрастанию.
 *
 * Схема:
 * CREATE TABLE accounts(
 *     id int not null primary key,
 *     name text,
 *     login text,
 *     password text
 * );
 */
public class Select10OrderByNameAsc {

    public String query() {
        return """
                SELECT
                """;
    }
}
