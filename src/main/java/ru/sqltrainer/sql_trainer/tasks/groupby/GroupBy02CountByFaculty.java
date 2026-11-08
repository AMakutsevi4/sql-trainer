package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy02CountByFaculty extends SqlTask {

    @Override
    public String id() {
        return "groupby-02";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public String title() {
        return "GROUP BY 2";
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

                Напишите запрос, который подсчитает количество студентов на каждом факультете. Используйте таблицу "Студенты" с полями: студент, факультет. Группировка будет по faculty.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE students
                (
                    id INT,
                    faculty    VARCHAR(50)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO students
                VALUES (1, 'Engineering'),
                       (2, 'Arts'),
                       (3, 'Engineering'),
                       (4, 'Science'),
                       (5, 'Arts'),
                       (6, 'Engineering');
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                faculty;count;
                Arts;2;
                Engineering;3;
                Science;1;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT faculty, COUNT(*) AS count
                FROM students
                GROUP BY faculty
                ORDER BY faculty
                """;
    }
}
