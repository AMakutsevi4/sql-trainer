package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select13DistinctNameTest {

    @Test
    void shouldSelectDistinctNames() throws Exception {
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
                INSERT INTO accounts VALUES(3, 'Anna Volkova', 'avolk', 'admin1');
                INSERT INTO accounts VALUES(4, 'Ivan Petrov', 'ivan_p', 'qwerty');
                INSERT INTO accounts VALUES(5, 'Maria Sokolova', 'maria_s', 'pass01');
                INSERT INTO accounts VALUES(6, 'Anna Volkova', 'anna_v', 'admin1')
                """;

        String expected = """
                name;
                Anna Volkova;
                Ivan Petrov;
                Maria Sokolova;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select13DistinctName().query(),
                expected
        );
    }
}