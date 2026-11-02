package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join02InnerJoinCars extends SqlTask {


    @Override
    public String id() {
        return "join-02";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public String title() {
        return "1.2 Объединение таблиц автомобилей и двигателей";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется INNER JOIN.
                
                Чтобы выполнить объединение таблиц, необходимо:
                1. Указать необходимые столбцы из обеих таблиц в блоке SELECT;
                2. Указать первую таблицу в блоке FROM;
                3. Указать вторую таблицу в предложении INNER JOIN и условие соединения после ключевого слова ON.

                Задача: Выполните запрос, который выведет все строки. В результатах должны быть отражены данные столбцов id, model, volume, power. 
                Связь между таблицами осуществляется с помощью столбцов id в таблице cars и car_id в таблице engines.
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
                INSERT INTO cars VALUES (2, 'Renault Sandero');

                INSERT INTO engines VALUES (1234, 2.5, 181, 1);
                INSERT INTO engines VALUES (5678, 1.2, 75, 2);
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
                id;model;volume;power;
                1;Toyota Camry;2.5;181;
                2;Renault Sandero;1.2;75;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT id, model, volume, power 
                FROM cars 
                INNER JOIN engines ON cars.id = engines.car_id
                """;
    }
}
