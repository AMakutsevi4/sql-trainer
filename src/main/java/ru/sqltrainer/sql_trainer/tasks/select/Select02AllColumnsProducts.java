package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select02AllColumnsProducts extends SqlTask {

    @Override
    public String id() {
        return "select-02";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public String title() {
        return "1.2 Выбор всех полей с их названиями (products)";
    }

    @Override
    public String description() {
        return """
                SELECT названия_полей_через_запятую FROM название_таблицы;
                После последнего поля запятая не ставится.

                Задача: выбрать все записи из таблицы products,
                в выборке должны быть данные всех полей таблицы.
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
        return """
                SELECT
                """;
    }

    @Override
    public String expected() {
        return """
                id;name;price;count;
                1;apple;40;20;
                2;banana;60;25;
                3;orange;80;12;
                4;yogurt;90;18;
                5;coffee;350;6;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT * FROM products
                """;
    }
}
