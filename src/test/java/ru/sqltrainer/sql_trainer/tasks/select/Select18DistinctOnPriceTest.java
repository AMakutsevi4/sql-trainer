package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select18DistinctOnPriceTest {

    @Test
    @Disabled("H2 does not support DISTINCT ON - needs PostgreSQL")
    void shouldSelectDistinctOnPrice() throws Exception {
        String schema = """
                CREATE TABLE products(
                    id int not null primary key,
                    name varchar(255),
                    price int,
                    count int
                )
                """;

        String data = """
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

        String expected = """
                name;price;count;
                apple;35;20;
                apple;40;20;
                milk;68;15;
                milk;75;15;
                sugar;105;30;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select18DistinctOnPrice().query(),
                expected
        );
    }
}