package ru.sqltrainer.sql_trainer.tasks.where;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Where11InGradesTest {

    @Test
    void shouldSelectByInGrades() throws Exception {
        String schema = """
                CREATE TABLE subjects(
                    id int not null primary key,
                    name varchar(255),
                    grade int,
                    start_date timestamp
                )
                """;

        String data = """
                INSERT INTO subjects VALUES(1, 'Physics', 50, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(2, 'History', 75, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(3, 'Biology', 65, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(4, 'Chemistry', 60, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(5, 'Geography', 70, TIMESTAMP '2026-10-12 00:00:00')
                """;

        String expected = """
                id;name;grade;start_date;
                1;Physics;50;2026-10-12 00:00:00.0;
                4;Chemistry;60;2026-10-12 00:00:00.0;
                5;Geography;70;2026-10-12 00:00:00.0;
                """;

        SqlTaskTester.assertQuery(schema, data, new Where11InGrades().query(), expected);
    }
}
