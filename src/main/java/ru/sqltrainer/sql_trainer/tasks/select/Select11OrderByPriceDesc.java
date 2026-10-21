package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select11OrderByPriceDesc extends SqlTask {

    @Override
    public String id() {
        return "select-11";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 11;
    }

    @Override
    public String title() {
        return "4.2 Сортировка результатов выборки (products)";
    }

    @Override
    public String description() {
        return """
                 SELECT столбцы FROM таблица ORDER BY столбец ASC | DESC;
                
                 Задача: выбрать все столбцы из products,
                 отсортировать по price по убыванию.
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
                INSERT INTO products VALUES(2, 'banana', 60, 25);
                INSERT INTO products VALUES(3, 'orange', 80, 12);
                INSERT INTO products VALUES(4, 'yogurt', 90, 18);
                INSERT INTO products VALUES(5, 'coffee', 350, 6)
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                id;name;price;count;
                5;coffee;350;6;
                4;yogurt;90;18;
                3;orange;80;12;
                2;banana;60;25;
                1;apple;40;20;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM products ORDER BY price DESC";
    }
}
