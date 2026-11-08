package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy27HavingAvgRatingOver9 extends SqlTask {

    @Override
    public String id() {
        return "groupby-27";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 27;
    }

    @Override
    public String title() {
        return "GROUP BY и HAVING 7";
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

                Вывести жанр фильма (genre), количество фильмов и средний рейтинг фильмов каждого жанра. Использовать GROUP BY и HAVING для отображения только тех жанров, у которых средний рейтинг больше 9.0. Группировка будет по genre.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE movies
                (
                    id     INT PRIMARY KEY,
                    title  VARCHAR(255),
                    genre  VARCHAR(50),
                    rating DECIMAL(3, 1)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO movies
                VALUES (1, 'The Shawshank Redemption', 'Drama', 9.3),
                       (2, 'The Godfather', 'Crime', 9.2),
                       (3, 'Pulp Fiction', 'Crime', 8.9),
                       (4, 'The Dark Knight', 'Action', 9.0),
                       (5, 'Schindler''s List', 'Biography', 8.9),
                       (6, 'Forrest Gump', 'Drama', 8.8),
                       (7, 'The Matrix', 'Action', 8.7),
                       (8, 'Inception', 'Action', 8.8),
                       (9, 'The Silence of the Lambs', 'Crime', 8.6),
                       (10, 'The Departed', 'Crime', 8.5);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                genre;count;avg;
                Drama;2;9.0500000000000000;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT genre, COUNT(*) AS count, AVG(rating) AS avg
                FROM movies
                GROUP BY genre
                HAVING AVG(rating) > 9.0
                ORDER BY genre
                """;
    }
}
