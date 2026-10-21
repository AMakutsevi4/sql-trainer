package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select22CountGroupBy extends SqlTask {

    @Override
    public String id() {
        return "select-22";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 22;
    }

    @Override
    public String title() {
        return "7.4 Агрегатные функции. COUNT + GROUP BY";
    }

    @Override
    public String description() {
        return """
                 SELECT столбец, COUNT(столбец) FROM таблица GROUP BY столбец;
                
                 Задача: количество фильмов в каждой категории (category_id).
                 Отсортируйте по category_id по возрастанию.
                 Используйте alias: AS count
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE movies(
                    id int not null primary key,
                    name varchar(255),
                    producer varchar(255),
                    category_id int
                )
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO movies VALUES(1, 'film_1', 'prod_1', 1);
                INSERT INTO movies VALUES(2, 'film_2', 'prod_2', 2);
                INSERT INTO movies VALUES(3, 'film_3', 'prod_3', 3);
                INSERT INTO movies VALUES(4, 'film_4', 'prod_4', 4);
                INSERT INTO movies VALUES(5, 'film_5', 'prod_3', 1);
                INSERT INTO movies VALUES(6, 'film_6', 'prod_2', 2);
                INSERT INTO movies VALUES(7, 'film_7', 'prod_4', 3);
                INSERT INTO movies VALUES(8, 'film_8', 'prod_1', 1);
                INSERT INTO movies VALUES(9, 'film_9', 'prod_3', 2);
                INSERT INTO movies VALUES(10, 'film_10', 'prod_1', 1)
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                category_id;count;
                1;4;
                2;3;
                3;2;
                4;1;
                """;
    }

    @Override
    public String solution() {
        return "SELECT category_id, COUNT(category_id) AS count FROM movies GROUP BY category_id";
    }
}
