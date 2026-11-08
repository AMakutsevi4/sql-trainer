package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy28HavingSumOver1500 extends SqlTask {

    @Override
    public String id() {
        return "groupby-28";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 28;
    }

    @Override
    public String title() {
        return "GROUP BY и HAVING 8";
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

                Вывести идентификатор клиента (customer_id) и общую сумму его заказов. Использовать GROUP BY и HAVING для отображения только тех клиентов, у которых общая сумма заказов превышает 1500. Группировка будет по customer_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE orders
                (
                    id          INT PRIMARY KEY,
                    customer_id INT,
                    date        DATE,
                    amount      DECIMAL(10, 2)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO orders
                VALUES (1, 1, '2023-01-05', 500.00),
                       (2, 2, '2023-02-10', 750.50),
                       (3, 3, '2023-03-15', 1200.25),
                       (4, 1, '2023-04-20', 300.75),
                       (5, 4, '2023-05-25', 900.80),
                       (6, 2, '2023-06-30', 1100.00),
                       (7, 5, '2023-07-05', 800.50),
                       (8, 3, '2023-08-10', 950.25),
                       (9, 1, '2023-09-15', 600.30),
                       (10, 4, '2023-10-20', 400.50);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                customer_id;sum;
                3;2150.50;
                2;1850.50;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT customer_id, SUM(amount) AS sum
                FROM orders
                GROUP BY customer_id
                HAVING SUM(amount) > 1500
                ORDER BY sum DESC
                """;
    }
}
