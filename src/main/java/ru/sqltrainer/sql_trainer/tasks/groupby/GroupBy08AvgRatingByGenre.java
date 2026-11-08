package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy08AvgRatingByGenre extends SqlTask {

    @Override
    public String id() {
        return "groupby-08";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 8;
    }

    @Override
    public String title() {
        return "GROUP BY 8";
    }

    @Override
    public String description() {
        return """
                GROUP BY позволяет разделить данные, которые возвращены с помощью SELECT на группы.

                При этом для каждой из групп можно будет использовать агрегатные функции, например COUNT() для подсчета предметов в каждой из групп

                Синтаксис имеет следующий вид:

                SELECT
                    столбец_1,
                    столбец_2,
                    ...,
                    aggregate_function(столбец_3)
                FROM
                    название_таблицы
                GROUP BY
                    столбец_1,
                    столбец_2,
                    ...;
                Необходимо учесть - любой столбец, который указан в SELECT (столбец, который хранит результат вычисления агрегатных функций, не считается), должен быть указан после GROUP BY.

                Напишите запрос, который вычислит средний рейтинг фильмов для каждого жанра. Используйте таблицу "Фильмы" с полями: название фильма, жанр, рейтинг. Группировка будет по genre.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE movies
                (
                    id INT PRIMARY KEY,
                    title    VARCHAR(100),
                    genre    VARCHAR(50),
                    rating   FLOAT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO movies
                VALUES (1, 'Movie1', 'Action', 8.5),
                       (2, 'Movie2', 'Drama', 7.8),
                       (3, 'Movie3', 'Action', 9.0),
                       (4, 'Movie4', 'Comedy', 6.5),
                       (5, 'Movie5', 'Drama', 8.2);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                genre;avg;
                Comedy;6.5;
                Drama;8.0;
                Action;8.75;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT genre, AVG(rating) AS avg
                FROM movies
                GROUP BY genre
                ORDER BY avg
                """;
    }
}
