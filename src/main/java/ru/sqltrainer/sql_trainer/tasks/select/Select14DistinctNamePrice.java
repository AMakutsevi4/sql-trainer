package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select14DistinctNamePrice extends SqlTask {

    @Override
    public String id() {
        return "select-14";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 14;
    }

    @Override
    public String title() {
        return "5.2 Удаление повторяющихся строк — DISTINCT (products)";
    }

    @Override
    public String description() {
        return """
                 SELECT DISTINCT столбцы FROM таблица;
                 Если указано несколько столбцов — уникальные комбинации этих столбцов.
                
                 Задача: выбрать уникальные пары name и price,
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
                name;price;
                sugar;105;
                milk;75;
                milk;68;
                apple;40;
                apple;35;
                """;
    }

    @Override
    public String solution() {
        return "SELECT DISTINCT name, price FROM products ORDER BY price DESC";
    }
}
