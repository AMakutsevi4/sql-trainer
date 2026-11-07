package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join20NaturalRightJoinCars extends SqlTask {

    @Override
    public String id() {
        return "join-20";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 20;
    }

    @Override
    public String title() {
        return "1.20 NATURAL JOIN 3";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. В свою очередь они бывают разных видов. Рассмотрим NATURAL JOIN.

                NATURAL JOIN – это соединение, которое создает неявное соединение на основе тех столбцов в соединяемых таблицах которые имеют одинаковые имена.

                Ниже представлен синтаксис natural join в Postgres:

                SELECT список_столбцов
                FROM table_1
                NATURAL [INNER, LEFT, RIGHT] JOIN table_2;
                То, что указано в квадратных скобках не является обязательным параметром. При этом, если не уточнять тип запроса, то по умолчанию будет использован INNER JOIN.

                Если вместо списка выбираемых столбцов указывать звездочку (*), то результат будет содержать столбцы:

                - значения общих столбцов. Естественно в единственном экземпляре.

                - каждый из столбцов из обеих таблиц, который не является общим столбцов для каждой из таблиц.

                Для представленной ниже схемы выполните запрос с использованием NATURAL JOIN. Это должен быть RIGHT JOIN. В результатах отражаем значения всех столбцов – используйте оператор звездочки (*).
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE cars (
                    car_id int primary key,
                    model text
                );

                CREATE TABLE engines (
                    number int primary key,
                    volume decimal,
                    power int,
                    car_id int references cars(car_id)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO cars VALUES (1, 'Toyota Camry');
                INSERT INTO cars VALUES (2, 'Kia Rio');
                INSERT INTO cars VALUES (3, 'Audi A6');
                INSERT INTO cars VALUES (4, 'Renault Sandero');

                INSERT INTO engines VALUES (1234, 2.5, 181, 1);
                INSERT INTO engines VALUES (1479, 1.6, 123, null);
                INSERT INTO engines VALUES (5678, 1.2, 75, 4);
                INSERT INTO engines VALUES (5072, 3.0, 231, null);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                car_id;model;number;volume;power;
                1;Toyota Camry;1234;2.5;181;
                null;null;1479;1.6;123;
                null;null;5072;3.0;231;
                4;Renault Sandero;5678;1.2;75;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT *
                FROM cars
                NATURAL RIGHT JOIN engines
                ORDER BY number
                """;
    }
}
