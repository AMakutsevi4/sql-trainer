package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy01AvgBySubject extends SqlTask {

    @Override
    public String id() {
        return "groupby-01";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public String title() {
        return "GROUP BY 1";
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

                Создайте запрос, который вычислит средний балл студентов по каждому предмету. Таблица "Оценки" содержит поля: студент, предмет, оценка. Группировка будет по subject.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE students
                (
                    id INT,
                    subject    VARCHAR(50),
                    grade      INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO students
                VALUES (1, 'Math', 85),
                       (1, 'Physics', 90),
                       (2, 'Math', 78),
                       (2, 'Physics', 88),
                       (3, 'Math', 92),
                       (3, 'Physics', 95);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                subject;avg;
                Math;85.0000000000000000;
                Physics;91.0000000000000000;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT subject, AVG(grade) AS avg
                FROM students
                GROUP BY subject
                ORDER BY subject
                """;
    }
}
