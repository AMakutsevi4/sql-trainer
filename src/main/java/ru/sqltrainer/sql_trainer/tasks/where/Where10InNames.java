package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where10InNames extends SqlTask {

    @Override
    public String id() {
        return "where-10";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 10;
    }

    @Override
    public String title() {
        return "1.9. Предложение WHERE с оператором IN";
    }

    @Override
    public String description() {
        return """
                 Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
                 в SQL используется WHERE предложение.
                
                 Если мы хотим сопоставить строку с любой строкой в списке мы можем использовать оператор IN.
                 Синтаксис будет следующим:
                 SELECT имена_столбцов_через_запятую FROM название_таблицы
                 WHERE столбец_для_проверки IN (строка1, строка2, и т.д.);
                 Если значение в проверяемом столбце совпадет с любой из строк в операторе IN,
                 то такая строка попадет в результаты выборки.
                
                Задача: Для представленной ниже схемы напишите запрос, который вернет только те строки,
                 у которых в столбце first_name будут строки Ann, Anne, Annie.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE customers(
                    id int not null primary key,
                    first_name varchar(255),
                    last_name varchar(255),
                    active boolean,
                    email varchar(255)
                )
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO customers VALUES(1, 'Ann', 'Belova', true, 'ann@mail.test');
                INSERT INTO customers VALUES(2, 'Sergey', 'Kozlov', false, 'sergey@mail.test');
                INSERT INTO customers VALUES(3, 'Anne', 'Morozova', true, 'anne@mail.test');
                INSERT INTO customers VALUES(4, 'Elena', 'Volkova', false, 'elena@mail.test');
                INSERT INTO customers VALUES(5, 'Annie', 'Orlova', true, 'annie@mail.test')
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                id;first_name;last_name;active;email;
                1;Ann;Belova;true;ann@mail.test;
                3;Anne;Morozova;true;anne@mail.test;
                5;Annie;Orlova;true;annie@mail.test;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM customers WHERE first_name IN ('Ann', 'Anne', 'Annie')";
    }
}
