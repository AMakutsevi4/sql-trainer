package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join11RightJoinCars extends SqlTask {
    @Override
    public String id() {
        return "join-11";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 11;
    }

    @Override
    public String title() {
        return "1.11 Правое внешнее соединение (RIGHT JOIN) 2";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. При этом бывают не только внутренние, но и внешние JOIN.
                В свою очередь они бывают разных видов. Рассмотрим RIGHT JOIN.
                
                Например, у нас есть следующие таблицы:
                
                CREATE TABLE films (
                    film_id int  PRIMARY KEY,
                    title text
                );
                
                CREATE TABLE film_reviews (
                    review_id int PRIMARY KEY,
                    review text,
                    film_id int
                );
                
                Т.е. у фильма может быть ноль или много рецензий, а рецензия принадлежит нолю или одному фильму. Столбец film_id в films ссылается на столбец film_id в film_reviews таблице.
                
                Для того, чтобы сделать выборку данных из таблицы films_reviews, которые могут иметь или не иметь соответствующие строки в таблице films, то необходимо использовать предложение RIGHT JOIN. Делается это следующим образом:
                
                - сначала необходимо указать столбцы в обеих таблицах, из которых мы хотим выбирать данные;
                - необходимо указать левую таблицу (таблица films) в предложении FROM;
                - указываем правую таблицу (таблицу films_reviews) в предложении RIGHT JOIN и условие соединения после ключевого слова ON.
                
                Таким образом, запрос будет иметь вид:
                
                SELECT review, title
                FROM films
                RIGHT JOIN film_reviews on films.film_id = film_reviews.film_id;
                
                Для сокращения можно использовать псевдонимы имен таблиц. Также можно писать RIGHT OUTER JOIN, хотя использование OUTER не является обязательным.
                
                Задача: Напишите запрос с использованием RIGHT JOIN для схемы, представленной ниже. В результатах должны быть отражены значения столбцов id, model, number, volume, power. 
                Объединением происходит по столбцу id из cars и car_id из engines.
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
                INSERT INTO engines VALUES (1479, 1.6, 123, null);
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
                id;model;number;volume;power;
                1;Toyota Camry;1234;2.5;181;
                2;Renault Sandero;5678;1.2;75;
                null;null;1479;1.6;123;
                null;null;5072;3;231;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT cars.id, cars.model, engines.number, engines.volume, engines.power
                FROM cars
                RIGHT JOIN engines ON cars.id = engines.car_id
                ORDER BY cars.id ASC NULLS LAST, engines.number
                """;
    }
}
