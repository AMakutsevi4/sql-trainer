package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select12OrderByNameGradeAsc extends SqlTask {

    @Override
    public String id() {
        return "select-12";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 12;
    }

    @Override
    public String title() {
        return "4.3 Сортировка результатов выборки (subjects)";
    }

    @Override
    public String description() {
        return """
                 Можно сортировать по нескольким столбцам через запятую.
                 Столбцы учитываются в том порядке, в котором указаны.
                
                 Задача: выбрать все столбцы из subjects,
                 отсортировать по name и grade по возрастанию.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE subjects(
                    id int not null primary key,
                    name varchar(255),
                    grade int,
                    start_date timestamp
                )
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO subjects VALUES(1, 'Math', 75, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(2, 'English', 85, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(3, 'Math', 50, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(4, 'CS', 95, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(5, 'English', 75, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(6, 'CS', 85, TIMESTAMP '2026-10-12 00:00:00')
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                id;name;grade;start_date;
                6;CS;85;2026-10-12 00:00:00.0;
                4;CS;95;2026-10-12 00:00:00.0;
                5;English;75;2026-10-12 00:00:00.0;
                2;English;85;2026-10-12 00:00:00.0;
                3;Math;50;2026-10-12 00:00:00.0;
                1;Math;75;2026-10-12 00:00:00.0;
                """;

    }

    @Override
    public String solution() {
        return "SELECT * FROM subjects ORDER BY name ASC, grade ASC";
    }
}
