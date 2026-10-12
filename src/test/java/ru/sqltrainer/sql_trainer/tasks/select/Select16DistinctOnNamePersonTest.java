package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select16DistinctOnNamePersonTest {

    @Test
    @Disabled("H2 does not support DISTINCT ON - needs PostgreSQL")
    void shouldSelectDistinctOnNamePerson() throws Exception {
        String schema = """
                CREATE TABLE accounts(
                    id int not null primary key,
                    name_person varchar(255),
                    login varchar(255),
                    password varchar(255)
                )
                """;

        String data = """
                INSERT INTO accounts VALUES(1, 'Ivan Petrov', 'ipetrov', 'qwerty');
                INSERT INTO accounts VALUES(2, 'Maria Sokolova', 'msokol', 'pass01');
                INSERT INTO accounts VALUES(3, 'Anna Volkova', 'avolk', 'admin1');
                INSERT INTO accounts VALUES(4, 'Ivan Petrov', 'ivan_p', 'qwerty');
                INSERT INTO accounts VALUES(5, 'Maria Sokolova', 'maria_s', 'pass01');
                INSERT INTO accounts VALUES(6, 'Anna Volkova', 'anna_v', 'admin1')
                """;

        String expected = """
                name_person;login;password;
                Anna Volkova;avolk;admin1;
                Ivan Petrov;ipetrov;qwerty;
                Maria Sokolova;msokol;pass01;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select16DistinctOnNamePerson().query(),
                expected
        );
    }
}