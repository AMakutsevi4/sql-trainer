package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 3.3 Использование псевдонимов для столбцов (subjects)
 *
 * SELECT имя_столбца AS имя_псевдонима FROM название_таблицы;
 *
 * Задача: выбрать столбцы name и grade.
 * Для name используйте псевдоним name_subject,
 * для grade — total_grade.
 *
 * Схема:
 * CREATE TABLE subjects(
 *     id int not null primary key,
 *     name text,
 *     grade int,
 *     start_date timestamp
 * );
 */
public class Select09AliasSubjects {

    public String query() {
        return """
                SELECT
                """;
    }
}
