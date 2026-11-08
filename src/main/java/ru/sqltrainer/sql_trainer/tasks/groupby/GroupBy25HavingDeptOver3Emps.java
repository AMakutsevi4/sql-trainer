package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy25HavingDeptOver3Emps extends SqlTask {

    @Override
    public String id() {
        return "groupby-25";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 25;
    }

    @Override
    public String title() {
        return "GROUP BY и HAVING 5";
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

                Вывести идентификатор отдела (department_id), общее число сотрудников и минимальный возраст сотрудников в каждом отделе. Использовать GROUP BY и HAVING для отображения только тех отделов, у которых количество сотрудников больше 3. Группировка будет по department_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE employees
                (
                    id            INT PRIMARY KEY,
                    name          VARCHAR(255),
                    department_id INT,
                    age           INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO employees
                VALUES (1, 'John Parker', 1, 25),
                       (2, 'Jane Collins', 1, 28),
                       (3, 'Bob Wilson', 2, 32),
                       (4, 'Alice Morgan', 2, 29),
                       (5, 'Charlie Hayes', 1, 26),
                       (6, 'David Reed', 3, 31),
                       (7, 'Eva Brooks', 3, 27),
                       (8, 'Frank Cooper', 2, 30),
                       (9, 'Grace Turner', 1, 29),
                       (10, 'Henry Walsh', 3, 33);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                department_id;count;min;
                1;4;25;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT department_id, COUNT(*) AS count, MIN(age) AS min
                FROM employees
                GROUP BY department_id
                HAVING COUNT(*) > 3
                ORDER BY department_id
                """;
    }
}
