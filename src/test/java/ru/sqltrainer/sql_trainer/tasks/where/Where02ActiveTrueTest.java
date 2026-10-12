package ru.sqltrainer.sql_trainer.tasks.where;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Where02ActiveTrueTest {

    @Test
    void shouldSelectActiveCustomers() throws Exception {
        String schema = """
                CREATE TABLE customers(
                    id int not null primary key,
                    first_name varchar(255),
                    last_name varchar(255),
                    active boolean,
                    email varchar(255)
                )
                """;

        String data = """
                INSERT INTO customers VALUES(1, 'Ivan', 'Petrov', true, 'ivan@mail.test');
                INSERT INTO customers VALUES(2, 'Sergey', 'Sokolova', false, 'sergey@mail.test');
                INSERT INTO customers VALUES(3, 'Oleg', 'Smirnov', true, 'oleg@mail.test');
                INSERT INTO customers VALUES(4, 'Elena', 'Volkova', false, 'elena@mail.test');
                INSERT INTO customers VALUES(5, 'Anna', 'Kozlova', true, 'anna@mail.test')
                """;

        String expected = """
                id;first_name;last_name;active;email;
                1;Ivan;Petrov;true;ivan@mail.test;
                3;Oleg;Smirnov;true;oleg@mail.test;
                5;Anna;Kozlova;true;anna@mail.test;
                """;

        SqlTaskTester.assertQuery(schema, data, new Where02ActiveTrue().query(), expected);
    }
}
