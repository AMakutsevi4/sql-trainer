package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where13LikeSuffix extends SqlTask {

    @Override
    public String id() {
        return "where-13";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 13;
    }

    @Override
    public String title() {
        return "1.12. Предложение WHERE с оператором LIKE 2";
    }

    @Override
    public String description() {
        return """
                 Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
                 в SQL используется WHERE предложение.
                
                 При этом иногда необходимо сделать выборку строк чтобы при проверке условия
                 значение в столбце соответствовало какому-то интересующему нас шаблону.
                 В этом случае используется оператор LIKE. Этот оператор используется вместе с символом "%" -
                 он соответствует любой последовательности символов.
                 Синтаксис будет выглядеть следующим образом:
                 SELECT имена_столбцов_через_запятую FROM название_таблицы
                 WHERE столбец_для_проверки LIKE '%подстрока';
                 Шаблон в операторе LIKE всегда покрывает всю анализируемую строку.
                
                 Задача: Для представленной ниже схемы airplanes напишите запрос, который вернет все строки
                 у которой в столбце model будет в конце строки последовательность символов 200.
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
                6;Boeing 777-200;9700;310;
                9;Bombardier CRJ-200;2700;50;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM airplanes WHERE model LIKE '%200'";
    }
}
