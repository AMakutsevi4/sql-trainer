package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy03SumByClient extends SqlTask {

    @Override
    public String id() {
        return "groupby-03";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 3;
    }

    @Override
    public String title() {
        return "GROUP BY 3";
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

                Разработайте запрос, который вычислит общую сумму заказов для каждого клиента. Используйте таблицу "Заказы" с полями: клиент, сумма. Группировка будет по id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE orders
                (
                    id INT,
                    amount    INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO orders
                VALUES (1, 100),
                       (2, 150),
                       (1, 200),
                       (3, 50),
                       (2, 120);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                id;sum;
                3;50;
                2;270;
                1;300;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT id, SUM(amount) AS sum
                FROM orders
                GROUP BY id
                ORDER BY sum
                """;
    }
}
