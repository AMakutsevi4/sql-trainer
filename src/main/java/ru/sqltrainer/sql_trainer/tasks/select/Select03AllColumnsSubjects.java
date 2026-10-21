package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;


@Component
public class Select03AllColumnsSubjects extends SqlTask {

    @Override
    public String id() {
        return "select-03";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 3;
    }

    @Override
    public String title() {
        return "1.3 Выбор всех полей с их названиями (subjects)";
    }

    @Override
    public String description() {
        return """
                 SELECT названия_полей_через_запятую FROM название_таблицы;
                 Если нужны все столбцы, вместо перечисления можно поставить *.
                
                 Задача: выбрать все записи из таблицы subjects,
                 в выборке должны быть данные всех полей таблицы.
                 Поля можно указывать в произвольном порядке.
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
        return """
                SELECT
                """;
    }

    @Override
    public String expected() {
        return """
                id;name;grade;start_date;
                1;Physics;70;2026-10-12 00:00:00.0;
                2;History;55;2026-10-12 00:00:00.0;
                3;Biology;80;2026-10-12 00:00:00.0;
                4;Chemistry;65;2026-10-12 00:00:00.0;
                5;Geography;90;2026-10-12 00:00:00.0;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT * FROM subjects
                """;
    }
}
