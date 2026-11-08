package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy24HavingGenreOver2Movies extends SqlTask {

    @Override
    public String id() {
        return "groupby-24";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 24;
    }

    @Override
    public String title() {
        return "GROUP BY и HAVING 4";
    }

    @Override
    public String description() {
        return """
                GROUP BY позволяет разделить данные, которые возвращены с помощью SELECT на группы.

                При этом для каждой из групп можно будет использовать агрегатные функции, например COUNT() для подсчета предметов в каждой из групп.

                Вместе с GROUP BY можно использовать HAVING. Этот оператор позволяет указать какие группы будут включены в выходной результат. Т.е. просто выполняют фильтрацию групп. Но очень важно усвоить разницу между WHERE и HAVING - в отличие от WHERE оператор HAVING будет выполняться после группировки данных. Т.е. WHERE фильтрует строки, а HAVING - группы после их группировки.

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
                    ...
                HAVING
                    условие_фильтрации_групп;
                Необходимо учесть - любой столбец, который указан в SELECT (столбец, который хранит результат вычисления агрегатных функций, не считается), должен быть указан после GROUP BY.

                Создайте запрос, который вычислит средний рейтинг фильмов для каждого жанра, при условии, что в этом жанре есть более 2 фильмов. Необходимо вывести общее число фильмов, средний рейтинг, минимальный и максимальный рейтинг. Группировка будет по genre.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE movies
                (
                    id           INT PRIMARY KEY,
                    title        VARCHAR(255),
                    genre        VARCHAR(50),
                    rating       DECIMAL(3, 1),
                    release_year INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO movies
                VALUES (1, 'Movie1', 'Drama', 8.5, 2020),
                       (2, 'Movie2', 'Action', 7.8, 2019),
                       (3, 'Movie3', 'Drama', 9.0, 2021),
                       (4, 'Movie4', 'Action', 6.5, 2020),
                       (5, 'Movie5', 'Comedy', 8.2, 2018),
                       (6, 'Movie6', 'Comedy', 7.5, 2019),
                       (7, 'Movie7', 'Drama', 8.0, 2021);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                genre;count;avg;min;max;
                Drama;3;8.5000000000000000;8.0;9.0;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT genre, COUNT(*) AS count, AVG(rating) AS avg, MIN(rating) AS min, MAX(rating) AS max
                FROM movies
                GROUP BY genre
                HAVING COUNT(*) > 2
                ORDER BY genre
                """;
    }
}
