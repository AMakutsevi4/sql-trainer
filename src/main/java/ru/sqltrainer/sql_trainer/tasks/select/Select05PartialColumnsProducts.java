package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select05PartialColumnsProducts extends SqlTask {

    @Override
    public String id() {
        return "select-05";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 5;
    }

    @Override
    public String title() {
        return "2.2 Выбор части полей с их названиями (products)";
    }

    @Override
    public String description() {
        return """
                 SELECT названия_полей_через_запятую FROM название_таблицы;
                 Можно указать не все поля, если они в выборке не нужны.
                
                 Задача: выбрать все записи из products,
                 только поля id и price.
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
                id;price;
                1;40;
                2;60;
                3;80;
                4;90;
                5;350;
                """;
    }

    @Override
    public String solution() {
        return "SELECT id, price FROM products";
    }
}
