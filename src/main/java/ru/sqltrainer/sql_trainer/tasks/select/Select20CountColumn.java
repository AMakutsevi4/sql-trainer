package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select20CountColumn extends SqlTask {

    @Override
    public String id() {
        return "select-20";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 20;
    }

    @Override
    public String title() {
        return "7.2 Агрегатные функции. COUNT(столбец)";
    }

    @Override
    public String description() {
        return """
                 SELECT COUNT(имя_столбца) FROM таблица;
                 COUNT(столбец) не учитывает null в этом столбце.
                
                 Задача: посчитать количество строк, где name не null.
                 Используйте alias: COUNT(name) AS count
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
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                count;
                5;
                """;
    }

    @Override
    public String solution() {
        return "SELECT COUNT(name) AS count FROM subjects";
    }
}
