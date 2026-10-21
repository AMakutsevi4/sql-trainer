package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where02ActiveTrue extends SqlTask {

    @Override
    public String id() {
        return "where-02";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public String title() {
        return "1.1. Предложение WHERE 1";
    }

    @Override
    public String description() {
        return """
                 Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
                 в SQL используется WHERE предложение.
                
                 Синтаксис имеет следующий вид:
                 SELECT имена_столбцов_через_запятую FROM название_таблицы
                 WHERE условие ORDER BY условие_сортировки
                 ORDER BY в данном случае опциональное предложение, указывать необязательно.
                
                 Условие должно оцениваться как истина, ложь или неизвестность.
                 Это может быть логическое выражение или комбинация логических выражений.
                
                 В итоге запрос возвращает только те строки, которые удовлетворяют условию в WHERE.
                
                 Задача: Для представленной ниже схемы customers напишите запрос, который вернет только те записи,
                 у которых значение в столбце active равно true.
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
                INSERT INTO customers VALUES(1, 'Ivan', 'Petrov', true, 'ivan@mail.test');
                INSERT INTO customers VALUES(2, 'Sergey', 'Sokolova', false, 'sergey@mail.test');
                INSERT INTO customers VALUES(3, 'Oleg', 'Smirnov', true, 'oleg@mail.test');
                INSERT INTO customers VALUES(4, 'Elena', 'Volkova', false, 'elena@mail.test');
                INSERT INTO customers VALUES(5, 'Anna', 'Kozlova', true, 'anna@mail.test')
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
                1;Ivan;Petrov;true;ivan@mail.test;
                3;Oleg;Smirnov;true;oleg@mail.test;
                5;Anna;Kozlova;true;anna@mail.test;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM customers WHERE active = true ORDER BY active";
    }
}
