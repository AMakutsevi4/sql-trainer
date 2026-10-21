package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select08AliasProducts extends SqlTask {

    @Override
    public String id() {
        return "select-08";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 8;
    }

    @Override
    public String title() {
        return "3.2 Использование псевдонимов для столбцов (products)";
    }

    @Override
    public String description() {
        return """
                 SELECT имя_столбца AS имя_псевдонима FROM название_таблицы;
                
                  Задача: выбрать столбцы name и count.
                  Для name используйте псевдоним product_name,
                  для count — actual_count.
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
                product_name;actual_count;
                apple;20;
                banana;25;
                orange;12;
                yogurt;18;
                coffee;6;
                """;
    }

    @Override
    public String solution() {
        return "SELECT name AS product_name, count AS actual_count FROM products";
    }
}
