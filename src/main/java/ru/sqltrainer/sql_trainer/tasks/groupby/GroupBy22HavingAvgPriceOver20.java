package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy22HavingAvgPriceOver20 extends SqlTask {

    @Override
    public String id() {
        return "groupby-22";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 22;
    }

    @Override
    public String title() {
        return "GROUP BY и HAVING 2";
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

                Рассчитать среднюю стоимость заказов для каждого продукта в таблице "order_details". Вывести только те продукты, у которых средняя цена заказа выше 20. Группировка будет по product_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE details
                (
                    id         INT PRIMARY KEY,
                    product_id INT           NOT NULL,
                    price      DECIMAL(8, 2) NOT NULL,
                    quantity   INT           NOT NULL
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO details (id, product_id, price, quantity)
                VALUES (1, 2, 25.60, 2),
                       (2, 1, 15.8, 3),
                       (3, 3, 50.00, 1),
                       (4, 4, 10.00, 5),
                       (5, 5, 30.20, 2),
                       (6, 2, 18.50, 4),
                       (7, 2, 22.80, 3),
                       (8, 1, 15.00, 6),
                       (9, 3, 40.00, 1),
                       (10, 4, 12.50, 4);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                product_id;avg;
                3;45.0000000000000000;
                5;30.2000000000000000;
                2;22.3000000000000000;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT product_id, AVG(price) AS avg
                FROM details
                GROUP BY product_id
                HAVING AVG(price) > 20
                ORDER BY avg DESC
                """;
    }
}
