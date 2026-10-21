package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where16NotLikeLimit extends SqlTask {

    @Override
    public String id() {
        return "where-16";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 16;
    }

    @Override
    public String title() {
        return "1.15. Предложение WHERE с оператором NOT LIKE и предложением LIMIT";
    }

    @Override
    public String description() {
        return """
                 Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
                 в SQL используется WHERE предложение.
                
                 При этом иногда необходимо сделать выборку строк чтобы при проверке условия
                 значение в столбце не соответствовало какому-то интересующему нас шаблону.
                 В этом случае используется оператор NOT LIKE.
                
                 При этом чтобы ограничить количество строк в выборке используется предложение LIMIT,
                 которое указывается в конце запроса и после него указывается количество строк,
                 которое должно попасть в результаты выборки.
                
                 Задача: Для представленной ниже схемы airplanes напишите запрос, который вернет только те строки,
                 в которых в столбце model не содержится подстрока Airbus и Boeing.
                 Ограничить результаты выборки необходимо одной строкой.
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
                4;Embraer E190;4500;110;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM airplanes WHERE model NOT LIKE '%Airbus%' AND model NOT LIKE '%Boeing%' LIMIT 1";
    }
}
