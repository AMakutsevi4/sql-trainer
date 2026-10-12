package ru.sqltrainer.sql_trainer.tasks.where;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Where10InNamesTest {

    @Test
    void shouldSelectByInNames() throws Exception {
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
                INSERT INTO customers VALUES(1, 'Ann', 'Belova', true, 'ann@mail.test');
                INSERT INTO customers VALUES(2, 'Sergey', 'Kozlov', false, 'sergey@mail.test');
                INSERT INTO customers VALUES(3, 'Anne', 'Morozova', true, 'anne@mail.test');
                INSERT INTO customers VALUES(4, 'Elena', 'Volkova', false, 'elena@mail.test');
                INSERT INTO customers VALUES(5, 'Annie', 'Orlova', true, 'annie@mail.test')
                """;

        String expected = """
                id;first_name;last_name;active;email;
                1;Ann;Belova;true;ann@mail.test;
                3;Anne;Morozova;true;anne@mail.test;
                5;Annie;Orlova;true;annie@mail.test;
                """;

        SqlTaskTester.assertQuery(schema, data, new Where10InNames().query(), expected);
    }
}
