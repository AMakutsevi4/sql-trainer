package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where08OrConditions extends SqlTask {

    @Override
    public String id() {
        return "where-08";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 8;
    }

    @Override
    public String title() {
        return "1.7. Предложение WHERE с несколькими условиями с помощью OR 1";
    }

    @Override
    public String description() {
        return """
                 Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
                 в SQL используется WHERE предложение.
                
                 При этом условия можно комбинировать и проверять несколько условий.
                 Для этого используется логическая операция OR (ИЛИ). Синтаксис имеет следующий вид:
                 SELECT имена_столбцов_через_запятую FROM название_таблицы
                 WHERE условие1 OR условие2 OR условие3;
                 В итоге запрос вернет только те строки, удовлетворяющих хотя бы одному из условий,
                 которые объединены с помощью OR.
                
                Задача: Для представленной ниже схемы customers напишите запрос, который выполнит выборку всех записей,
                 удовлетворяющих хотя бы одному из условий - значение в столбце id больше 3
                 или значение в столбце active равно false.
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
                2;Sergey;Sokolova;false;sergey@mail.test;
                4;Elena;Volkova;false;elena@mail.test;
                5;Anna;Kozlova;true;anna@mail.test;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM customers WHERE id > 3 OR active = false";
    }
}
