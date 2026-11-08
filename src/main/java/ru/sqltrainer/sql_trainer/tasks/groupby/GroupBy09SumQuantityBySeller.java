package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy09SumQuantityBySeller extends SqlTask {

    @Override
    public String id() {
        return "groupby-09";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 9;
    }

    @Override
    public String title() {
        return "GROUP BY 9";
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

                Разработайте запрос, который определит количество проданных товаров для каждого продавца. Используйте таблицу "Продажи" с полями: продавец, товар, количество. Группировка будет по seller_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE sales
                (
                    seller_id  INT,
                    product_id INT,
                    quantity   INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO sales
                VALUES (1, 1, 10),
                       (2, 2, 5),
                       (1, 3, 8),
                       (3, 4, 12),
                       (2, 1, 15);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                seller_id;sum;
                3;12;
                2;20;
                1;18;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT seller_id, SUM(quantity) AS sum
                FROM sales
                GROUP BY seller_id
                ORDER BY seller_id DESC
                """;
    }
}
