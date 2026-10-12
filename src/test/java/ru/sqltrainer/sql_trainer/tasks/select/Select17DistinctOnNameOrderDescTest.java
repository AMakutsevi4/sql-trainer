package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select17DistinctOnNameOrderDescTest {

    @Test
    @Disabled("H2 does not support DISTINCT ON - needs PostgreSQL")
    void shouldSelectDistinctOnName() throws Exception {
        String schema = """
                CREATE TABLE subjects(
                    id int not null primary key,
                    name varchar(255),
                    grade int,
                    start_date timestamp
                )
                """;

        String data = """
                INSERT INTO subjects VALUES(1, 'Math', 75, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(2, 'English', 85, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(3, 'Math', 50, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(4, 'CS', 95, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(5, 'English', 75, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(6, 'CS', 85, TIMESTAMP '2026-10-12 00:00:00')
                """;

        String expected = """
                id;name;grade;
                1;Math;75;
                2;English;85;
                4;CS;95;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select17DistinctOnNameOrderDesc().query(),
                expected
        );
    }
}