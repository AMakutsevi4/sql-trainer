package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy15WhereScoresAbove85 extends SqlTask {

    @Override
    public String id() {
        return "groupby-15";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 15;
    }

    @Override
    public String title() {
        return "GROUP BY и WHERE 5";
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

                Разработайте запрос, который найдет количество оценок и максимальную оценку по каждому предмету, учитывая только те которые больше 85. Группировка будет по subject.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE scores
                (
                    student_id   SERIAL PRIMARY KEY,
                    student_name VARCHAR(50),
                    subject      VARCHAR(50),
                    score        INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO scores (student_name, subject, score)
                VALUES ('Alice', 'Math', 85),
                       ('Bob', 'Math', 78),
                       ('Alice', 'Physics', 90),
                       ('Bob', 'Physics', 88),
                       ('Charlie', 'Math', 92),
                       ('Charlie', 'Physics', 95);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                subject;count;max_grade;
                Math;1;92;
                Physics;3;95;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT subject, COUNT(*) AS count, MAX(score) AS max_grade
                FROM scores
                WHERE score > 85
                GROUP BY subject
                ORDER BY subject
                """;
    }
}
