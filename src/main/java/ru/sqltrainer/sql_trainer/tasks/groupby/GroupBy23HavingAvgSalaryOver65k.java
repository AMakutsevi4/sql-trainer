package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy23HavingAvgSalaryOver65k extends SqlTask {

    @Override
    public String id() {
        return "groupby-23";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 23;
    }

    @Override
    public String title() {
        return "GROUP BY и HAVING 3";
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

                Напишите запрос, который вычислит общую сумму зарплаты по отделам, где средняя зарплата превышает 65000. Группировка будет по department_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE employees
                (
                    id            INT PRIMARY KEY,
                    department_id INT,
                    salary        DECIMAL(10, 2),
                    position      VARCHAR(50)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO employees
                VALUES (1, 1, 70000.00, 'Manager'),
                       (2, 1, 60000.00, 'Developer'),
                       (3, 2, 75000.00, 'Manager'),
                       (4, 2, 55000.00, 'Developer'),
                       (5, 3, 80000.00, 'Manager'),
                       (6, 3, 65000.00, 'Developer');
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                department_id;sum;avg;
                3;145000.00;72500.000000000000;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT department_id, SUM(salary) AS sum, AVG(salary) AS avg
                FROM employees
                GROUP BY department_id
                HAVING AVG(salary) > 65000
                ORDER BY department_id
                """;
    }
}
