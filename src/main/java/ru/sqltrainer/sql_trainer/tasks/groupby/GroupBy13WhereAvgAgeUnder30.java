package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy13WhereAvgAgeUnder30 extends SqlTask {

    @Override
    public String id() {
        return "groupby-13";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 13;
    }

    @Override
    public String title() {
        return "GROUP BY и WHERE 3";
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

                Напишите запрос, который вычислит средний возраст сотрудников для каждого отдела, оставив только тех, кто младше 30 лет. Группировка будет по department.
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
                    age        INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO employees (name, department, age)
                VALUES ('John', 'HR', 28),
                       ('Jane', 'IT', 32),
                       ('Bob', 'Finance', 29),
                       ('Alice', 'IT', 26),
                       ('Charlie', 'HR', 30);
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
                Finance;29.0000000000000000;
                HR;28.0000000000000000;
                IT;26.0000000000000000;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT department, AVG(age) AS avg
                FROM employees
                WHERE age < 30
                GROUP BY department
                ORDER BY avg DESC
                """;
    }
}
