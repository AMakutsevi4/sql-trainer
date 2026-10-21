package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where19BetweenAsCompare extends SqlTask {

    @Override
    public String id() {
        return "where-19";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 19;
    }

    @Override
    public String title() {
        return "18. Предложение WHERE с оператором BETWEEN: замена на логическое выражение";
    }

    @Override
    public String description() {
        return """
                 Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
                 в SQL используется WHERE предложение.
                
                 При этом иногда необходимо сделать выборку строк чтобы при проверке условия
                 значение в столбце находилось в каком-то диапазоне значений.
                 В этом случае нам поможет оператор BETWEEN.
                
                 Необходимо отметить, если значение в столбце для проверки больше или равно значению_1
                 и меньше или равно значению_2, то логическое выражение вернет true.
                
                 Таким образом оператор BETWEEN можно заменить на использование обычных логических выражений:
                 SELECT имена_столбцов FROM название_таблицы
                 WHERE столбец_для_проверки >= значение_1 AND столбец_для_проверки <= значение_2;
                
                 Задача: Для представленной ниже схемы напишите запрос, который вернет только те строки,
                 в которых значения в столбце grade больше или равно 65 и меньше или равно 80.
                 Не используйте в запросе BETWEEN.
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
                2;History;75;2026-10-12 00:00:00.0;
                3;Biology;65;2026-10-12 00:00:00.0;
                5;Geography;70;2026-10-12 00:00:00.0;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM subjects WHERE grade >= 65 AND grade <= 80";
    }
}
