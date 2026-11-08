package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy17WhereGenreStartsD extends SqlTask {

    @Override
    public String id() {
        return "groupby-17";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 17;
    }

    @Override
    public String title() {
        return "GROUP BY и WHERE 7";
    }

    @Override
    public String description() {
        return """
                GROUP BY позволяет разделить данные, которые возвращены с помощью SELECT на группы.

                При этом для каждой из групп можно будет использовать агрегатные функции, например COUNT() для подсчета предметов в каждой из групп.

                Как и в других запросах, с GROUP BY можно использовать WHERE. Важно уяснить правило - WHERE обрабатывается после FROM и до GROUP BY. Таким образом, данные сначала фильтруются, а потом выполняется их группировка.

                Синтаксис имеет следующий вид:

                SELECT
                    столбец_1,
                    столбец_2,
                    ...,
                    aggregate_function(столбец_3)
                FROM
                    название_таблицы
                WHERE
                    условие выборки
                GROUP BY
                    столбец_1,
                    столбец_2,
                    ...;
                Необходимо учесть - любой столбец, который указан в SELECT (столбец, который хранит результат вычисления агрегатных функций, не считается), должен быть указан после GROUP BY.

                Создайте запрос, который вычислит средний рейтинг фильмов для жанров, которые начинаются на 'D'. Группировка будет по genre.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE movies
                (
                    movie_id SERIAL PRIMARY KEY,
                    title    VARCHAR(100),
                    genre    VARCHAR(50),
                    rating   FLOAT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO movies (title, genre, rating)
                VALUES ('Movie1', 'Action', 8.5),
                       ('Movie2', 'Documentary', 7.3),
                       ('Movie3', 'Drama', 7.8),
                       ('Movie4', 'Detective', 9.0),
                       ('Movie5', 'Action', 8.0),
                       ('Movie6', 'Documentary', 8.1),
                       ('Movie7', 'Comedy', 6.5),
                       ('Movie8', 'Detective', 7.5),
                       ('Movie9', 'Comedy', 5.9),
                       ('Movie10', 'Documentary', 9.8),
                       ('Movie11', 'Drama', 8.2);
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
                Detective;8.25;
                Documentary;8.4;
                Drama;8.0;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT genre, AVG(rating) AS avg
                FROM movies
                WHERE genre LIKE 'D%'
                GROUP BY genre
                ORDER BY genre
                """;
    }
}
