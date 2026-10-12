package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select20CountColumnTest {

    @Test
    void shouldCountNonNullNames() throws Exception {
        String schema = """
                CREATE TABLE subjects(
                    id int not null primary key,
                    name varchar(255),
                    grade int,
                    start_date timestamp
                )
                """;

        String data = """
                INSERT INTO subjects VALUES(1, 'Physics', 70, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(2, null, 55, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(3, 'Biology', 80, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(4, null, 65, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(5, 'Geography', 90, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(6, null, 50, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(7, 'History', 75, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(8, null, 60, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(9, 'Chemistry', 85, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(10, null, 95, TIMESTAMP '2026-10-12 00:00:00')
                """;

        String expected = """
                count;
                5;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select20CountColumn().query(),
                expected
        );
    }
}