package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 5.1 Удаление повторяющихся строк — DISTINCT (accounts)
 *
 * SELECT DISTINCT столбцы FROM таблица;
 * Если указан один столбец — вернутся уникальные значения этого столбца.
 *
 * Задача: выбрать уникальные значения name.
 * Добавьте ORDER BY name для стабильного порядка.
 *
 * Схема:
 * CREATE TABLE accounts(
 *     id int not null primary key,
 *     name text,
 *     login text,
 *     password text
 * );
 */
public class Select13DistinctName {

    public String query() {
        return """
                SELECT
                """;
    }
}
