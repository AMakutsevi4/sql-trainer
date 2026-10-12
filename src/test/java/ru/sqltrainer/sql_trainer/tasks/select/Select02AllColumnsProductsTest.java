package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select02AllColumnsProductsTest {

    @Test
    void shouldSelectAllColumnsFromProducts() throws Exception {
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
                INSERT INTO products VALUES(3, 'orange', 80, 12);
                INSERT INTO products VALUES(4, 'yogurt', 90, 18);
                INSERT INTO products VALUES(5, 'coffee', 350, 6)
                """;

        String expected = """
                id;name;price;count;
                1;apple;40;20;
                2;banana;60;25;
                3;orange;80;12;
                4;yogurt;90;18;
                5;coffee;350;6;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select02AllColumnsProducts().query(),
                expected
        );
    }
}