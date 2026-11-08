package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy19WhereDeptTwoChars extends SqlTask {

    @Override
    public String id() {
        return "groupby-19";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 19;
    }

    @Override
    public String title() {
        return "GROUP BY и WHERE 9";
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

                Напишите запрос, который вычислит общую сумму зарплаты по отделам, названия которых состоят только из двух символов. Группировка будет по department.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE employees
                (
                    id         SERIAL PRIMARY KEY,
                    name       VARCHAR(50),
                    department VARCHAR(50),
                    salary     INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO employees (name, department, salary)
                VALUES ('John', 'HR', 58000),
                       ('Jane', 'IT', 62000),
                       ('Bob', 'Finance', 63000),
                       ('Alice', 'IT', 60000),
                       ('Charlie', 'HR', 59000);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                department;sum;
                IT;122000;
                HR;117000;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT department, SUM(salary) AS sum
                FROM employees
                WHERE LENGTH(department) = 2
                GROUP BY department
                ORDER BY sum DESC
                """;
    }
}
