package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where11InGrades extends SqlTask {

    @Override
    public String id() {
        return "where-11";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 11;
    }

    @Override
    public String title() {
        return "1.10. Предложение WHERE с оператором IN 1";
    }

    @Override
    public String description() {
        return """
                 Если мы хотим сопоставить какое-то значение в столбце с любым значением в списке
                 мы можем использовать оператор IN. Синтаксис будет следующим:
                 SELECT имена_столбцов_через_запятую FROM название_таблицы
                 WHERE столбец_для_проверки IN (значение1, значение2, и т.д.);
                 Если значение в проверяемом столбце совпадет с любой из строк в операторе IN,
                 то такая строка попадет в результаты выборки.
                
                Задача: Для представленной ниже схемы напишите запрос, который вернет только те строки,
                 у которых в столбце grade будет значение 50, 60 или 70.
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
                INSERT INTO subjects VALUES(1, 'Physics', 50, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(2, 'History', 75, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(3, 'Biology', 65, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(4, 'Chemistry', 60, TIMESTAMP '2026-10-12 00:00:00');
                INSERT INTO subjects VALUES(5, 'Geography', 70, TIMESTAMP '2026-10-12 00:00:00')
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
                1;Physics;50;2026-10-12 00:00:00.0;
                4;Chemistry;60;2026-10-12 00:00:00.0;
                5;Geography;70;2026-10-12 00:00:00.0;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM subjects WHERE grade IN (50, 60, 70)";
    }
}
