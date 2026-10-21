package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where14LikePattern extends SqlTask {

    @Override
    public String id() {
        return "where-14";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 14;
    }

    @Override
    public String title() {
        return "1.13. Предложение WHERE с оператором LIKE 3";
    }

    @Override
    public String description() {
        return """
                 Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
                 в SQL используется WHERE предложение.
                
                 При этом иногда необходимо сделать выборку строк чтобы при проверке условия
                 значение в столбце соответствовало какому-то интересующему нас шаблону.
                 В этом случае используется оператор LIKE. Также используется символ "_" -
                 он соответствует в точности одному любому символу.
                 Синтаксис будет выглядеть следующим образом (указан лишь пример):
                 SELECT имена_столбцов_через_запятую FROM название_таблицы
                 WHERE столбец_для_проверки LIKE '__подстрока%';
                 Т.е. если разбирать этот шаблон, то искомая строка должна начинаться с 2 символов,
                 после которых следует искомая подстрока, а дальше - любой набор символов.
                
                 Задача: Для представленной ниже схемы airplanes напишите запрос, который вернет строки,
                 в которых в столбце model будет строка которая начинается гарантировано из трех любых символов,
                 дальше идет буква 'b' и далее - любой набор символов.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE airplanes(
                    id int not null primary key,
                    model varchar(255),
                    range int,
                    capacity int
                )
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO airplanes VALUES(1, 'Airbus-A320-200', 6300, 190);
                INSERT INTO airplanes VALUES(2, 'Airbus-A321-200', 7400, 230);
                INSERT INTO airplanes VALUES(3, 'Airbus-A220-300', 6200, 160);
                INSERT INTO airplanes VALUES(4, 'Embraer E190', 4500, 110);
                INSERT INTO airplanes VALUES(5, 'Boeing 787-9', 14100, 290);
                INSERT INTO airplanes VALUES(6, 'Boeing 777-200', 9700, 310);
                INSERT INTO airplanes VALUES(7, 'Boeing 737-800', 5400, 180);
                INSERT INTO airplanes VALUES(8, 'Tupolev Tu-214', 6500, 210);
                INSERT INTO airplanes VALUES(9, 'Bombardier CRJ-200', 2700, 50)
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                id;model;range;capacity;
                1;Airbus-A320-200;6300;190;
                2;Airbus-A321-200;7400;230;
                3;Airbus-A220-300;6200;160;
                9;Bombardier CRJ-200;2700;50;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM airplanes WHERE model LIKE '%___b___%'";
    }
}
