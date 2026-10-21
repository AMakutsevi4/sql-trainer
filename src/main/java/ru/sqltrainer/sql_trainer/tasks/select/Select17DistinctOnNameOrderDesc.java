package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select17DistinctOnNameOrderDesc extends SqlTask {

    @Override
    public String id() {
        return "select-17";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 17;
    }

    @Override
    public String title() {
        return "6.2 Предложение DISTINCT ON (subjects)";
    }

    @Override
    public String description() {
        return """
                 Хорошая практика: всегда использовать ORDER BY вместе с DISTINCT ON,
                 чтобы результат был предсказуемым.
                
                 Задача: уникальные строки по name,
                 в выборке: id, name, grade.
                 Сортировка: ORDER BY name DESC, id.
                
                 Внимание: DISTINCT ON есть только в PostgreSQL.
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
                id;name;grade;
                1;Math;75;
                2;English;85;
                4;CS;95;
                """;
    }

    @Override
    public String solution() {
        return "SELECT DISTINCT ON (name) id, name, grade FROM subjects ORDER BY name DESC, id";
    }
}
