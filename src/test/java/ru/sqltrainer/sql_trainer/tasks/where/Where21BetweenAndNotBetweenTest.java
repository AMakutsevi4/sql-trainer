package ru.sqltrainer.sql_trainer.tasks.where;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Where21BetweenAndNotBetweenTest {

    @Test
    void shouldSelectByBetweenAndNotBetween() throws Exception {
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
                INSERT INTO transactions VALUES(1, 'Credit', 1, 125, '2021-05-10 12:00:00');
                INSERT INTO transactions VALUES(2, 'Current payment', 2, 88, '2021-11-20 12:00:00');
                INSERT INTO transactions VALUES(3, 'Service', 3, 194, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(4, 'Vacation', 4, 254, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(5, 'Credit', 1, 244, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(6, 'Current payment', 2, 114, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(7, 'Service', 3, 178, '2022-09-15 12:00:00');
                INSERT INTO transactions VALUES(8, 'Vacation', 4, 196, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(9, 'Credit', 1, 198, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(10, 'Current payment', 2, 78, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(11, 'Service', 3, 156, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(12, 'Vacation', 4, 150, '2022-01-05 12:00:00')
                """;

        String expected = """
                id;purpose;client_id;amount;payment_date;
                1;Credit;1;125;2021-05-10 12:00:00.0;
                2;Current payment;2;88;2021-11-20 12:00:00.0;
                7;Service;3;178;2022-09-15 12:00:00.0;
                12;Vacation;4;150;2022-01-05 12:00:00.0;
                """;

        SqlTaskTester.assertQuery(schema, data, new Where21BetweenAndNotBetween().query(), expected);
    }
}
