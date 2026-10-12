package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select26SumTest {

    @Test
    void shouldSelectSum() throws Exception {
        String schema = """
                CREATE TABLE transactions(
                    id int not null primary key,
                    purpose varchar(255),
                    client_id int,
                    amount int,
                    payment_date timestamp
                )
                """;

        String data = """
                INSERT INTO transactions VALUES(1, 'Credit', 1, 120, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(2, 'Current payment', 2, 80, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(3, 'Service', 3, 200, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(4, 'Vacation', 4, 250, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(5, 'Delivery', 3, 150, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(6, 'Current payment', 4, 70, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(7, 'Service', 1, 130, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(8, 'Credit', 2, 600, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(9, 'Vacation', 1, 210, '2022-06-01 12:00:00')
                """;

        String expected = """
                sum;
                1810;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select26Sum().query(),
                expected
        );
    }
}