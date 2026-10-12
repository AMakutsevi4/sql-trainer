package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select21CountDistinctTest {

    @Test
    void shouldCountDistinctNames() throws Exception {
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

        String expected = """
                count;
                4;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select21CountDistinct().query(),
                expected
        );
    }
}