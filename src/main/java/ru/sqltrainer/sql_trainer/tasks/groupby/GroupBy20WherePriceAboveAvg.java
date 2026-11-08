package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy20WherePriceAboveAvg extends SqlTask {

    @Override
    public String id() {
        return "groupby-20";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 20;
    }

    @Override
    public String title() {
        return "GROUP BY и WHERE 10";
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

                Создайте запрос, который определит количество продуктов в каждой категории с ценой выше средней цены по всем продуктам. Группировка будет по category.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE products
                (
                    id       SERIAL PRIMARY KEY,
                    name     VARCHAR(100),
                    category VARCHAR(50),
                    price    INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO products (name, category, price)
                VALUES ('Laptop', 'Electronics', 1200),
                       ('T-shirt', 'Clothing', 30),
                       ('Smartphone', 'Electronics', 800),
                       ('Book', 'Books', 20);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                category;count;
                Electronics;2;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT category, COUNT(*) AS count
                FROM products
                WHERE price > (SELECT AVG(price) FROM products)
                GROUP BY category
                ORDER BY category
                """;
    }
}
