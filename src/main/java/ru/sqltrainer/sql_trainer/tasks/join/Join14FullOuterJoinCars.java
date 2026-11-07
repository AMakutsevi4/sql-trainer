package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join14FullOuterJoinCars extends SqlTask {

    @Override
    public String id() {
        return "join-14";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 14;
    }

    @Override
    public String title() {
        return "1.14 FULL OUTER JOIN 2";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. При этом бывают не только внутренние, но и внешние JOIN. В свою очередь они бывают разных видов. Рассмотрим FULL OUTER JOIN.

                Например, мы хотим выполнить полное внешнее соединение двух таблиц: А и В. Синтаксис будет иметь вид:

                SELECT * FROM A
                FULL OUTER JOIN B on A.id = B.id;
                Полное внешнее соединение объединяет результаты как левого, так и правого соединения.

                Если строки в объединенной таблице не совпадают, то полное внешнее соединение устанавливает значения NULL для каждого столбца, в котором нет совпадающей строки.

                Если строка из одной таблицы имеет соответствие строке в другой таблице, то результирующая строка будет содержать столбцы, которые заполнены данными из обеих таблиц.

                Таким образом, результат включает совпадающие строки из обеих таблиц, а также несовпадающие строки.

                Для представленной ниже схемы напишите запрос с использованием FULL OUTER JOIN. В результатах должны быть отражены значения всех столбцов обоих таблиц. Левая таблица – cars, правая – engines.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE cars (
                    id int primary key,
                    model text
                );

                CREATE TABLE engines (
                    number int primary key,
                    volume decimal,
                    power int,
                    car_id int references cars(id)
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
                id;model;number;volume;power;car_id;
                1;Toyota Camry;1234;2.5;181;1;
                null;null;1479;1.6;123;null;
                null;null;5072;3.0;231;null;
                4;Renault Sandero;5678;1.2;75;4;
                2;Kia Rio;null;null;null;null;
                3;Audi A6;null;null;null;null;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT cars.id, cars.model, engines.number, engines.volume, engines.power, engines.car_id
                FROM cars
                FULL OUTER JOIN engines ON cars.id = engines.car_id
                ORDER BY engines.number ASC NULLS LAST, cars.id ASC
                """;
    }
}
