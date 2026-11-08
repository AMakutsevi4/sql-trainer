package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy21HavingOrdersOver2 extends SqlTask {

    @Override
    public String id() {
        return "groupby-21";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 21;
    }

    @Override
    public String title() {
        return "GROUP BY и HAVING 1";
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

                Подсчитать общее количество заказов для каждого клиента из таблицы "orders". Вывести только тех клиентов, у которых количество заказов превышает 2. Группировка будет по customer_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE orders
                (
                    id          SERIAL PRIMARY KEY,
                    customer_id INT            NOT NULL,
                    date        DATE           NOT NULL,
                    total       DECIMAL(10, 2) NOT NULL
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO orders (customer_id, date, total)
                VALUES (1, '2023-01-15', 150.50),
                       (2, '2023-02-10', 200.00),
                       (1, '2023-02-28', 300.75),
                       (3, '2023-03-05', 120.00),
                       (2, '2023-03-20', 250.30),
                       (1, '2023-04-02', 180.20),
                       (3, '2023-04-18', 90.50),
                       (2, '2023-05-01', 300.00),
                       (5, '2023-05-15', 200.80),
                       (3, '2023-06-02', 150.25);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                customer_id;count;
                3;3;
                2;3;
                1;3;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT customer_id, COUNT(*) AS count
                FROM orders
                GROUP BY customer_id
                HAVING COUNT(*) > 2
                ORDER BY customer_id DESC
                """;
    }
}
