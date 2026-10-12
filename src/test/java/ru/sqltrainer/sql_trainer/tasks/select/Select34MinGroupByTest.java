package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select34MinGroupByTest {

    @Test
    void shouldSelectMinGroupBy() throws Exception {
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
                INSERT INTO transactions VALUES(3, 'Service', 3, 190, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(4, 'Vacation', 4, 250, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(5, 'Credit', 1, 240, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(6, 'Current payment', 2, 100, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(7, 'Service', 3, 170, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(8, 'Vacation', 4, 190, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(9, 'Credit', 1, 180, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(10, 'Current payment', 2, 60, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(11, 'Service', 3, 150, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(12, 'Vacation', 4, 140, '2022-06-01 12:00:00')
                """;

        String expected = """
                purpose;min;
                Credit;120;
                Current payment;60;
                Service;150;
                Vacation;140;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select34MinGroupBy().query(),
                expected
        );
    }
}