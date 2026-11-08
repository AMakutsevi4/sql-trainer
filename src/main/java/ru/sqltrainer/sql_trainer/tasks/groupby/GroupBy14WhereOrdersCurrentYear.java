package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy14WhereOrdersCurrentYear extends SqlTask {

    @Override
    public String id() {
        return "groupby-14";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 14;
    }

    @Override
    public String title() {
        return "GROUP BY и WHERE 4";
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

                Создайте запрос, который подсчитает общее количество заказов для каждого клиента за текущий год. Группировка будет по client_id. Результаты выборки отсортируйте по убыванию client_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE orders
                (
                    id         SERIAL PRIMARY KEY,
                    client_id  INT,
                    order_year INT,
                    amount     INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO orders (client_id, order_year, amount)
                VALUES (1, 2023, 100),
                       (2, 2023, 150),
                       (1, 2023, 200),
                       (3, 2023, 50),
                       (2, 2023, 120);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                client_id;count;
                3;1;
                2;2;
                1;2;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT client_id, COUNT(*) AS count
                FROM orders
                WHERE order_year = 2023
                GROUP BY client_id
                ORDER BY client_id DESC
                """;
    }
}
