package ru.sqltrainer.sql_trainer.tasks.where;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Where20NotBetweenTest {

    @Test
    void shouldSelectByNotBetween() throws Exception {
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
                INSERT INTO transactions VALUES(2, 'Current payment', 2, 90, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(3, 'Service', 3, 185, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(4, 'Vacation', 4, 260, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(5, 'Credit', 1, 230, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(6, 'Current payment', 2, 110, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(7, 'Service', 3, 165, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(8, 'Vacation', 4, 190, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(9, 'Credit', 1, 195, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(10, 'Current payment', 2, 75, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(11, 'Service', 3, 145, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(12, 'Vacation', 4, 140, '2022-06-01 12:00:00')
                """;

        String expected = """
                id;purpose;client_id;amount;payment_date;
                2;Current payment;2;90;2022-06-01 12:00:00.0;
                4;Vacation;4;260;2022-06-01 12:00:00.0;
                5;Credit;1;230;2022-06-01 12:00:00.0;
                10;Current payment;2;75;2022-06-01 12:00:00.0;
                """;

        SqlTaskTester.assertQuery(schema, data, new Where20NotBetween().query(), expected);
    }
}
