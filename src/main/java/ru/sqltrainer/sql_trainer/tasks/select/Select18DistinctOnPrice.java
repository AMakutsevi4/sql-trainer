package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select18DistinctOnPrice extends SqlTask {

    @Override
    public String id() {
        return "select-18";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 18;
    }

    @Override
    public String title() {
        return "6.3 Предложение DISTINCT ON (products)";
    }

    @Override
    public String description() {
        return """
                 Если в ORDER BY несколько столбцов, самыми левыми
                 должны идти столбцы из DISTINCT ON.
                
                 Задача: уникальные строки по price,
                 в выборке: name, price, count.
                 Сортировка: ORDER BY price, name, count.
                
                 Внимание: DISTINCT ON есть только в PostgreSQL.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE products(
                    id int not null primary key,
                    name varchar(255),
                    price int,
                    count int
                )
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO products VALUES(1, 'apple', 40, 20);
                INSERT INTO products VALUES(2, 'milk', 75, 15);
                INSERT INTO products VALUES(3, 'sugar', 105, 30);
                INSERT INTO products VALUES(4, 'apple', 40, 20);
                INSERT INTO products VALUES(5, 'milk', 68, 15);
                INSERT INTO products VALUES(6, 'sugar', 105, 30);
                INSERT INTO products VALUES(7, 'apple', 35, 20);
                INSERT INTO products VALUES(8, 'milk', 75, 15);
                INSERT INTO products VALUES(9, 'sugar', 105, 30)
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                name;price;count;
                apple;35;20;
                apple;40;20;
                milk;68;15;
                milk;75;15;
                sugar;105;30;
                """;
    }

    @Override
    public String solution() {
        return "SELECT DISTINCT ON (price) name, price, count FROM products ORDER BY price, name, count";
    }
}
