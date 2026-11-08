package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy04CountByCategory extends SqlTask {

    @Override
    public String id() {
        return "groupby-04";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 4;
    }

    @Override
    public String title() {
        return "GROUP BY 4";
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

                Создайте запрос, который определит количество продуктов в каждой категории. Используйте таблицу "Продукты" с полями: продукт, категория. Группировка будет по category.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE products
                (
                    id INT,
                    category   VARCHAR(50)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO products
                VALUES (1, 'Electronics'),
                       (3, 'Books'),
                       (4, 'Clothing'),
                       (4, 'Books'),
                       (5, 'Electronics'),
                       (6, 'Books');
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
                Clothing;1;
                Books;3;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT category, COUNT(*) AS count
                FROM products
                GROUP BY category
                ORDER BY category DESC
                """;
    }
}
