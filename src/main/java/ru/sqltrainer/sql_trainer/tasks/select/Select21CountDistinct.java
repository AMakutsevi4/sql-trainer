package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select21CountDistinct extends SqlTask {

    @Override
    public String id() {
        return "select-21";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 21;
    }

    @Override
    public String title() {
        return "7.3 Агрегатные функции. COUNT(DISTINCT столбец)";
    }

    @Override
    public String description() {
        return """
                 SELECT COUNT(DISTINCT имя_столбца) FROM таблица;
                 Возвращает количество уникальных ненулевых значений.
                
                 Задача: количество уникальных ненулевых name в products.
                 Используйте alias: AS count
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
                INSERT INTO products VALUES(3, 'milk', 75, 15);
                INSERT INTO products VALUES(4, 'yogurt', 90, 18);
                INSERT INTO products VALUES(5, 'apple', 40, 20);
                INSERT INTO products VALUES(6, 'banana', 60, 25);
                INSERT INTO products VALUES(7, 'milk', 75, 15);
                INSERT INTO products VALUES(8, 'apple', 40, 20);
                INSERT INTO products VALUES(9, 'banana', 60, 25);
                INSERT INTO products VALUES(10, 'apple', 40, 20)
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                count;
                4;
                """;
    }

    @Override
    public String solution() {
        return "SELECT COUNT(DISTINCT name) AS count FROM products";
    }
}
