package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy18WhereCity1Orders extends SqlTask {

    @Override
    public String id() {
        return "groupby-18";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 18;
    }

    @Override
    public String title() {
        return "GROUP BY и WHERE 8";
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

                Разработайте запрос, который подсчитает количество заказов, сделанных клиентами из определенного города - 'City_1', а также максимальное число товара, купленного каждым клиентом из этого города. Группировка будет по customer_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE orders
                (
                    id          SERIAL PRIMARY KEY,
                    customer_id INT,
                    city        VARCHAR(50),
                    amount      INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO orders (customer_id, city, amount)
                VALUES (1, 'City_1', 100),
                       (2, 'City_2', 150),
                       (3, 'City_3', 200),
                       (1, 'City_1', 200),
                       (4, 'City_4', 50),
                       (5, 'City_1', 120),
                       (6, 'City_2', 100),
                       (5, 'City_1', 150),
                       (7, 'City_3', 200),
                       (1, 'City_1', 50),
                       (4, 'City_4', 120);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                customer_id;count;max;
                1;3;200;
                5;2;150;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT customer_id, COUNT(*) AS count, MAX(amount) AS max
                FROM orders
                WHERE city = 'City_1'
                GROUP BY customer_id
                ORDER BY customer_id
                """;
    }
}
