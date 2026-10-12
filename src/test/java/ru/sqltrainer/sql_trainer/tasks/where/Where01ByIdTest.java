package ru.sqltrainer.sql_trainer.tasks.where;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Where01ByIdTest {

    @Test
    void shouldSelectAccountById() throws Exception {
        String schema = """
                CREATE TABLE account(
                    id int not null primary key,
                    name varchar(255)
                )
                """;

        String data = """
                INSERT INTO account VALUES (1, 'Ivan Petrov');
                INSERT INTO account VALUES (2, 'Maria Sokolova');
                INSERT INTO account VALUES (3, 'Dmitry Orlov')
                """;

        String expected = """
                id;name;
                1;Ivan Petrov;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Where01ById().query(),
                expected
        );
    }
}
