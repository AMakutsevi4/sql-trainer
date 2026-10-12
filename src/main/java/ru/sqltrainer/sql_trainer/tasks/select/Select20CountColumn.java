package ru.sqltrainer.sql_trainer.tasks.select;

/**
 * 7.2 Агрегатные функции. COUNT(столбец)
 *
 * SELECT COUNT(имя_столбца) FROM таблица;
 * COUNT(столбец) не учитывает null в этом столбце.
 *
 * Задача: посчитать количество строк, где name не null.
 * Используйте alias: COUNT(name) AS count
 *
 * Схема:
 * CREATE TABLE subjects(
 *     id int not null primary key,
 *     name text,
 *     grade int,
 *     start_date timestamp
 * );
 */
public class Select20CountColumn {

    public String query() {
        return """
                SELECT
                """;
    }
}
