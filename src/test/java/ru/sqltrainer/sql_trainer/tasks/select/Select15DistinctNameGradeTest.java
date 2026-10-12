package ru.sqltrainer.sql_trainer.tasks.select;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Select15DistinctNameGradeTest {

    @Test
    void shouldSelectDistinctNameGrade() throws Exception {
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
                INSERT INTO subjects VALUES(2, 'Math', null, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(3, 'Math', 75, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(4, 'Math', 85, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(5, 'Math', null, TIMESTAMP '2026-10-12 00:00:00')
                """;

        String expected = """
                name;grade;
                Math;75;
                Math;85;
                Math;null;
                """;

        SqlTaskTester.assertQuery(
                schema,
                data,
                new Select15DistinctNameGrade().query(),
                expected
        );
    }
}