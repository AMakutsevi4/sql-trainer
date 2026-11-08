package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy16WhereAboveAvgGrade extends SqlTask {

    @Override
    public String id() {
        return "groupby-16";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 16;
    }

    @Override
    public String title() {
        return "GROUP BY и WHERE 6";
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

                Напишите запрос, который подсчитает количество оценок по каждому студенту, учитывая только те, которые превышают среднюю оценку в таблице grades. Группировка будет по student_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE grades
                (
                    id         SERIAL PRIMARY KEY,
                    student_id INT,
                    subject    VARCHAR(50),
                    grade      INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO grades (student_id, subject, grade)
                VALUES (1, 'Math', 85),
                       (2, 'Math', 78),
                       (1, 'Physics', 90),
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
                student_id;count;
                3;2;
                1;1;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT student_id, COUNT(*) AS count
                FROM grades
                WHERE grade > (SELECT AVG(grade) FROM grades)
                GROUP BY student_id
                ORDER BY count DESC, student_id DESC
                """;
    }
}
