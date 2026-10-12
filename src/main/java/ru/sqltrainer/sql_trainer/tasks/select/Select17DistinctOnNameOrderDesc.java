package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 6.2 Предложение DISTINCT ON (subjects) — PostgreSQL
 *
 * Хорошая практика: всегда использовать ORDER BY вместе с DISTINCT ON,
 * чтобы результат был предсказуемым.
 *
 * Задача: уникальные строки по name,
 * в выборке: id, name, grade.
 * Сортировка: ORDER BY name DESC, id.
 *
 * Внимание: DISTINCT ON есть только в PostgreSQL.
 * Тест помечен @Disabled на H2.
 *
 * Схема:
 * CREATE TABLE subjects(
 *     id int not null primary key,
 *     name text,
 *     grade int,
 *     start_date timestamp
 * );
 */
public class Select17DistinctOnNameOrderDesc {

    public String query() {
        return """
                SELECT
                """;
    }
}
