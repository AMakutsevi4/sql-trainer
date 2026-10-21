package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select09AliasSubjects extends SqlTask {

    @Override
    public String id() {
        return "select-09";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 9;
    }

    @Override
    public String title() {
        return "3.3 Использование псевдонимов для столбцов (subjects)";
    }

    @Override
    public String description() {
        return """
                 SELECT имя_столбца AS имя_псевдонима FROM название_таблицы;
                
                 Задача: выбрать столбцы name и grade.
                 Для name используйте псевдоним name_subject,
                 для grade — total_grade.
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
                name_subject;total_grade;
                Physics;70;
                History;55;
                Biology;80;
                Chemistry;65;
                Geography;90;
                """;
    }

    @Override
    public String solution() {
        return "SELECT name AS name_subject, grade AS total_grade FROM subjects";
    }
}
