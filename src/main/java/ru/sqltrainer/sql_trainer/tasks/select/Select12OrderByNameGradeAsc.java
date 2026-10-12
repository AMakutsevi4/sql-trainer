package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 4.3 Сортировка результатов выборки (subjects)
 *
 * Можно сортировать по нескольким столбцам через запятую.
 * Столбцы учитываются в том порядке, в котором указаны.
 *
 * Задача: выбрать все столбцы из subjects,
 * отсортировать по name и grade по возрастанию.
 *
 * Схема:
 * CREATE TABLE subjects(
 *     id int not null primary key,
 *     name text,
 *     grade int,
 *     start_date timestamp
 * );
 */
public class Select12OrderByNameGradeAsc {

    public String query() {
        return """
                SELECT
                """;
    }
}
