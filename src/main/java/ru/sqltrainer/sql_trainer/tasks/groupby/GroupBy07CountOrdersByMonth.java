package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy07CountOrdersByMonth extends SqlTask {

    @Override
    public String id() {
        return "groupby-07";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 7;
    }

    @Override
    public String title() {
        return "GROUP BY 7";
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

                Создайте запрос, который подсчитает общее количество заказов для каждого месяца. Используйте таблицу "Заказы" с полями: заказ, номер месяца, количество. Группировка будет по number_month.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE orders
                (
                    id           INT PRIMARY KEY,
                    number_month INT,
                    amount       INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO orders
                VALUES (1, 1, 100),
                       (2, 2, 150),
                       (3, 1, 200),
                       (4, 2, 50),
                       (5, 3, 120);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                number_month;count;
                3;1;
                2;2;
                1;2;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT number_month, COUNT(*) AS count
                FROM orders
                GROUP BY number_month
                ORDER BY number_month DESC
                """;
    }
}
