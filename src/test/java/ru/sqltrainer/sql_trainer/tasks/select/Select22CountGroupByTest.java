package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select22CountGroupByTest {

    @Test
    void shouldCountByCategory() throws Exception {
        String schema = """
                CREATE TABLE movies(
                    id int not null primary key,
                    name varchar(255),
                    producer varchar(255),
                    category_id int
                )
                """;

        String data = """
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

        String expected = """
                category_id;count;
                1;4;
                2;3;
                3;2;
                4;1;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select22CountGroupBy().query(),
                expected
        );
    }
}