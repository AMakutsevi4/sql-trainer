package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy05AvgAgeByDept extends SqlTask {

    @Override
    public String id() {
        return "groupby-05";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 5;
    }

    @Override
    public String title() {
        return "GROUP BY 5";
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

                Напишите запрос, который вычислит средний возраст сотрудников для каждого отдела. Используйте таблицу "Сотрудники" с полями: сотрудник, отдел, возраст. Группировка будет по department.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE employees
                (
                    id INT,
                    department  VARCHAR(50),
                    age         INT,
                    position    VARCHAR(50),
                    salary      INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO employees
                VALUES (1, 'HR', 30, 'Manager', 50000),
                       (2, 'IT', 25, 'Developer', 60000),
                       (3, 'Finance', 35, 'Analyst', 70000),
                       (4, 'IT', 28, 'Developer', 55000),
                       (5, 'HR', 32, 'Manager', 55000);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                department;avg;
                Finance;35.0000000000000000;
                IT;26.5000000000000000;
                HR;31.0000000000000000;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT department, AVG(age) AS avg
                FROM employees
                GROUP BY department
                ORDER BY MAX(id)
                """;
    }
}
