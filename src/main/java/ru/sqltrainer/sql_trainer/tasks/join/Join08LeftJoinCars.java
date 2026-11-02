package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join08LeftJoinCars extends SqlTask {

    @Override
    public String id() {
        return "join-08";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 8;
    }

    @Override
    public String title() {
        return "1.8 Левое внешнее объединение (LEFT JOIN) 2";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. При этом бывают не только внутренние, но и внешние JOIN. 
                В свою очередь они бывают разных видов. Рассмотрим LEFT JOIN.
                
                Например, у нас есть две таблицы:                
                CREATE TABLE A (                CREATE TABLE B (
                    pka int primary key,            pkb int primary key,
                    c1 text                         c2 text,
                );                                  fka int references A(pka)
                                                );
                Каждая строка в таблице А может иметь много соответствующих строк в таблице В или не иметь вообще. 
                В это же время каждая строка в таблице В, имеет одну и только одну соответствующую в таблице А.
                
                Для того, чтобы сделать выборку данных из таблицы А, которые могут иметь или не иметь соответствующие строки в таблице В, 
                то необходимо использовать предложение LEFT JOIN. Делается это следующим образом:
                
                - сначала необходимо указать столбцы в обеих таблицах, из которых мы хотим выбирать данные;                
                - необходимо указать левую таблицу (таблица А) в предложении FROM;                
                - указываем правую таблицу (таблицу В) в предложении LEFT JOIN и условие соединения после ключевого слова ON.
                
                Таким образом, запрос будет иметь вид:
                
                SELECT pka, c1, pkb, c2
                FROM A
                LEFT JOIN B ON pka = fka;
                При этом также можно указать LEFT OUTER JOIN, указание OUTER не является обязательным.
                
                Задание: Напишите запрос для таблиц ниже, который выполнит LEFT JOIN. Левая таблица – таблица cars. 
                Объединение осуществляется по столбцам id и car_id. В результатах выборки должны быть отражены значения столбцов id, model, number, volume, power.

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
                INSERT INTO engines VALUES (5678, 1.2, 75, 4);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                id;model;number;volume;power;
                1;Toyota Camry;1234;2.5;181;
                4;Renault Sandero;5678;1.2;75;
                2;Kia Rio;null;null;null;
                3;Audi A6;null;null;null;
                """;
    }

    @Override
    public String solution() {
        return "SELECT c.id, c.model, e.number, e.volume, e.power FROM cars c LEFT JOIN engines e ON c.id = e.car_id ORDER BY volume DESC";
    }
}
