package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 1.3 Выбор всех полей с их названиями (subjects)
 *
 * SELECT названия_полей_через_запятую FROM название_таблицы;
 * Если нужны все столбцы, вместо перечисления можно поставить *.
 *
 * Задача: выбрать все записи из таблицы subjects,
 * в выборке должны быть данные всех полей таблицы.
 * Поля можно указывать в произвольном порядке.
 *
 * Схема:
 * CREATE TABLE subjects(
 *     id int not null primary key,
 *     name text,
 *     grade int,
 *     start_date timestamp
 * );
 */
public class Select03AllColumnsSubjects {

    public String query() {
        return """
                SELECT
                """;
    }
}
