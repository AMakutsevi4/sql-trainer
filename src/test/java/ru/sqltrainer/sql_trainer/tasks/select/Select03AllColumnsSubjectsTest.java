package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select03AllColumnsSubjectsTest {

    @Test
    void shouldSelectAllColumnsFromSubjects() throws Exception {
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
                INSERT INTO subjects VALUES(2, 'History', 55, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(3, 'Biology', 80, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(4, 'Chemistry', 65, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(5, 'Geography', 90, TIMESTAMP '2026-10-12 00:00:00')
                """;

        String expected = """
                id;name;grade;start_date;
                1;Physics;70;2026-10-12 00:00:00.0;
                2;History;55;2026-10-12 00:00:00.0;
                3;Biology;80;2026-10-12 00:00:00.0;
                4;Chemistry;65;2026-10-12 00:00:00.0;
                5;Geography;90;2026-10-12 00:00:00.0;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select03AllColumnsSubjects().query(),
                expected
        );
    }
}