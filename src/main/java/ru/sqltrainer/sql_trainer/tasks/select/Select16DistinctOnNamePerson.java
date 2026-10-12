package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 6.1 Предложение DISTINCT ON (accounts) — PostgreSQL
 *
 * SELECT DISTINCT ON (столбцы_уникальности) столбцы_выборки FROM таблица;
 * Уникальность учитывается только по столбцам в скобках,
 * а в результат попадают и другие указанные столбцы.
 *
 * Задача: уникальные записи по name_person,
 * в выборке: name_person, login, password.
 * Используйте ORDER BY name_person, id.
 *
 * Внимание: DISTINCT ON есть только в PostgreSQL.
 * Тест помечен @Disabled на H2.
 *
 * Схема:
 * CREATE TABLE accounts(
 *     id int not null primary key,
 *     name_person text,
 *     login text,
 *     password text
 * );
 */
public class Select16DistinctOnNamePerson {

    public String query() {
        return """
                SELECT
                """;
    }
}
