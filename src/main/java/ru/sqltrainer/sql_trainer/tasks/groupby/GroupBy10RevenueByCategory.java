package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy10RevenueByCategory extends SqlTask {

    @Override
    public String id() {
        return "groupby-10";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 10;
    }

    @Override
    public String title() {
        return "GROUP BY 10";
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

                Создайте запрос, который вычислит общий доход от продаж для каждой категории товаров. Используйте таблицы: - "Продукты" с полями: товар, категория, цена. - "Продажи" с полями: продажа, id товара, количество. Группировка будет по category.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE products
                (
                    id INT PRIMARY KEY,
                    category   VARCHAR(50),
                    price      INT
                );

                CREATE TABLE sales
                (
                    sale_id    INT PRIMARY KEY,
                    product_id INT references products(id),
                    quantity   INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO products
                VALUES (1, 'Electronics', 500),
                       (2, 'Clothing', 30),
                       (3, 'Electronics', 700),
                       (4, 'Books', 20);

                INSERT INTO sales
                VALUES (1, 1, 10),
                       (2, 2, 5),
                       (3, 3, 8),
                       (4, 4, 12);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                category;sum;
                Electronics;10600;
                Clothing;150;
                Books;240;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT products.category, SUM(products.price * sales.quantity) AS sum
                FROM products
                JOIN sales ON products.id = sales.product_id
                GROUP BY products.category
                ORDER BY products.category DESC
                """;
    }
}
