package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select06PartialColumnsSubjects extends SqlTask {

    @Override
    public String id() {
        return "select-06";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 6;
    }

    @Override
    public String title() {
        return "2.3 Выбор части полей с их названиями (subjects)";
    }

    @Override
    public String description() {
        return """
                SELECT названия_полей_через_запятую FROM название_таблицы;
                Можно указать не все поля, если они в выборке не нужны.
                
                Задача: выбрать все записи из subjects,
                только поля name и start_date.
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
                INSERT INTO subjects VALUES(1, 'Physics', 70, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(2, 'History', 55, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(3, 'Biology', 80, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(4, 'Chemistry', 65, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(5, 'Geography', 90, TIMESTAMP '2026-10-12 00:00:00')
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                name;start_date;
                Physics;2026-10-12 00:00:00.0;
                History;2026-10-12 00:00:00.0;
                Biology;2026-10-12 00:00:00.0;
                Chemistry;2026-10-12 00:00:00.0;
                Geography;2026-10-12 00:00:00.0;
                """;
    }

    @Override
    public String solution() {
        return "SELECT name, start_date FROM subjects";
    }
}
