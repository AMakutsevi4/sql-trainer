package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join05JoinOmitInner extends SqlTask {


    @Override
    public String id() {
        return "join-05";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 5;
    }

    @Override
    public String title() {
        return "1.5 Сокращенный синтаксис (JOIN по умолчанию)";
    }

    @Override
    public String description() {
        return """
                Если в SQL-запросе не указан тип соединения по умолчанию, то выполняется внутреннее объединение (INNER JOIN).
                Слово INNER в запросе можно опустить, получив абсолютно аналогичный результат:
                SELECT поля FROM таблица_А JOIN таблица_Б ON условие;

                Задача: Напишите запрос, который вернет все строки с использованием сокращенного синтаксиса JOIN.
                В результатах выборки должны быть отражены столбцы id из таблицы cars, model, volume, power.
                Объединение происходит по столбцам id из таблицы cars и столбцу car_id из таблицы engines. В качестве псевдонимов используйте первые буквы названия таблиц (c и e).
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
                    id int primary key,
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

                INSERT INTO engines VALUES (1, 2.5, 181, 1);
                INSERT INTO engines VALUES (2, 1.2, 75, 2);
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
                SELECT c.id, c.model, e.volume, e.power 
                FROM cars c 
                JOIN engines e ON c.id = e.car_id
                """;
    }
}
