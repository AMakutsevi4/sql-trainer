package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select19CountAllTest {

    @Test
    void shouldCountAllRows() throws Exception {
        String schema = """
                CREATE TABLE accounts(
                    id int not null primary key,
                    name varchar(255),
                    login varchar(255),
                    password varchar(255)
                )
                """;

        String data = """
                INSERT INTO accounts VALUES(1, 'Ivan Petrov', 'ipetrov', 'qwerty');
                INSERT INTO accounts VALUES(2, 'Maria Sokolova', 'msokol', 'pass01');
                INSERT INTO accounts VALUES(3, 'Dmitry Orlov', 'dorlov', 'secret');
                INSERT INTO accounts VALUES(4, 'Anna Volkova', 'avolk', 'admin1');
                INSERT INTO accounts VALUES(5, 'Sergey Kozlov', 'skozlov', 'sql123')
                """;

        String expected = """
                count;
                5;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select19CountAll().query(),
                expected
        );
    }
}