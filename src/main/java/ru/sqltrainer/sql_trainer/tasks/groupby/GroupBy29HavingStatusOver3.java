package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy29HavingStatusOver3 extends SqlTask {

    @Override
    public String id() {
        return "groupby-29";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 29;
    }

    @Override
    public String title() {
        return "GROUP BY и HAVING 9";
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

                Вывести статус заказа (order_status) и количество заказов с каждым статусом. Использовать GROUP BY и HAVING для отображения только тех статусов, у которых количество заказов больше 3. Группировка будет по status.
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
                    status      VARCHAR(50)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO orders
                VALUES (1, 101, '2023-01-05', 'Pending'),
                       (2, 102, '2023-02-10', 'Shipped'),
                       (3, 103, '2023-03-15', 'Delivered'),
                       (4, 101, '2023-04-20', 'Pending'),
                       (5, 104, '2023-05-25', 'Delivered'),
                       (6, 102, '2023-06-30', 'Shipped'),
                       (7, 105, '2023-07-05', 'Delivered'),
                       (8, 103, '2023-08-10', 'Shipped'),
                       (9, 101, '2023-09-15', 'Delivered'),
                       (10, 104, '2023-10-20', 'Shipped');
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                status;count;
                Delivered;4;
                Shipped;4;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT status, COUNT(*) AS count
                FROM orders
                GROUP BY status
                HAVING COUNT(*) > 3
                ORDER BY status
                """;
    }
}
