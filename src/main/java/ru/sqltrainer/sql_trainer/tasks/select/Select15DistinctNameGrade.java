package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select15DistinctNameGrade extends SqlTask {

    @Override
    public String id() {
        return "select-15";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 15;
    }

    @Override
    public String title() {
        return "5.3 Удаление повторяющихся строк — DISTINCT и NULL (subjects)";
    }

    @Override
    public String description() {
        return """
                 DISTINCT не игнорирует NULL: null считается отдельным значением.
                
                 Задача: выбрать уникальные пары name и grade.
                 Добавьте ORDER BY grade NULLS LAST.
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
                INSERT INTO subjects VALUES(2, 'Math', null, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(3, 'Math', 75, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(4, 'Math', 85, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(5, 'Math', null, TIMESTAMP '2026-10-12 00:00:00')
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                name;grade;
                Math;75;
                Math;85;
                Math;null;
                """;
    }

    @Override
    public String solution() {
        return "SELECT DISTINCT name, grade FROM subjects ORDER BY grade NULLS LAST";
    }
}
