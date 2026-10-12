package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select14DistinctNamePriceTest {

    @Test
    void shouldSelectDistinctNamePrice() throws Exception {
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
                name;price;
                sugar;105;
                milk;75;
                milk;68;
                apple;40;
                apple;35;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select14DistinctNamePrice().query(),
                expected
        );
    }
}