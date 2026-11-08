package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy39CustomerAvgSpend extends SqlTask {

    @Override
    public String id() {
        return "groupby-39";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 39;
    }

    @Override
    public String title() {
        return "GROUP BY, WHERE и HAVING 9";
    }

    @Override
    public String description() {
        return """
                GROUP BY позволяет разделить данные, которые возвращены с помощью SELECT на группы.

                При этом для каждой из групп можно будет использовать агрегатные функции, например COUNT() для подсчета предметов в каждой из групп.

                Вместе с GROUP BY можно использовать HAVING. Этот оператор позволяет указать какие группы будут включены в выходной результат. Т.е. просто выполняют фильтрацию групп. Но очень важно усвоить разницу между WHERE и HAVING - в отличие от WHERE оператор HAVING будет выполняться после группировки данных. Т.е. WHERE фильтрует строки, а HAVING - группы после их группировки.

                В одном запросе одновременно могут использоваться и WHERE и HAVING. Главное не забывать про порядок выполнения этих операторов.

                Синтаксис имеет следующий вид:

                SELECT
                    столбец_1,
                    столбец_2,
                    ...,
                    aggregate_function(столбец_3)
                FROM
                    название_таблицы
                WHERE
                    условие_выборки
                GROUP BY
                    столбец_1,
                    столбец_2,
                    ...
                HAVING
                    условие_фильтрации_групп;
                Необходимо учесть - любой столбец, который указан в SELECT (столбец, который хранит результат вычисления агрегатных функций, не считается), должен быть указан после GROUP BY.

                Необходимо найти среднюю цену продуктов, купленных каждым клиентом, у которых общие затраты превышают 125. Необходимо выводить id и имя клиента, среднюю цену, общие затраты. Группировка будет по id и имени клиента
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE products
                (
                    id    SERIAL PRIMARY KEY,
                    name  VARCHAR(255)   NOT NULL,
                    price DECIMAL(10, 2) NOT NULL
                );

                CREATE TABLE customers
                (
                    id   SERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL
                );

                CREATE TABLE sales
                (
                    id     SERIAL PRIMARY KEY,
                    product_id  INT REFERENCES products (id),
                    customer_id INT REFERENCES customers (id),
                    amount      INT NOT NULL,
                    sale_year   INT,
                    sale_month  INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO products (name, price)
                VALUES ('Product A', 10.99),
                       ('Product B', 20.49),
                       ('Product C', 15.75),
                       ('Product D', 8.99),
                       ('Product E', 12.50),
                       ('Product F', 18.99),
                       ('Product G', 22.00);

                INSERT INTO customers (name)
                VALUES ('Customer 1'),
                       ('Customer 2'),
                       ('Customer 3'),
                       ('Customer 4'),
                       ('Customer 5');

                INSERT INTO sales (product_id, customer_id, amount, sale_year, sale_month)
                VALUES (1, 1, 2, 2023, 1),
                       (2, 2, 1, 2023, 2),
                       (3, 3, 3, 2023, 3),
                       (4, 4, 4, 2023, 4),
                       (5, 5, 2, 2023, 5),
                       (6, 1, 1, 2023, 4),
                       (7, 2, 3, 2023, 3),
                       (1, 3, 2, 2023, 1),
                       (2, 4, 4, 20235, 5),
                       (3, 5, 1, 2023, 2),
                       (4, 1, 3, 2023, 1),
                       (5, 2, 2, 2023, 3),
                       (6, 3, 1, 2023, 5),
                       (7, 4, 4, 2023, 4),
                       (1, 5, 3, 2023, 2),
                       (2, 1, 2, 2023, 1),
                       (3, 2, 1, 2023, 3);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                id;name;avg;sum;
                4;Customer 4;17.1600000000000000;205.92;
                2;Customer 2;17.6850000000000000;127.24;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT customers.id, customers.name, AVG(products.price) AS avg, SUM(products.price * sales.amount) AS sum
                FROM sales
                JOIN products ON sales.product_id = products.id
                JOIN customers ON sales.customer_id = customers.id
                GROUP BY customers.id, customers.name
                HAVING SUM(products.price * sales.amount) > 125
                ORDER BY sum DESC
                """;
    }
}
