package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where03LengthGreater extends SqlTask {

    @Override
    public String id() {
        return "where-03";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 3;
    }

    @Override
    public String title() {
        return "1.2. Предложение WHERE 2";
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
                
                 В итоге запрос возвращает только те строки, которые удовлетворяют условию в WHERE.
                
                 Для формирования условия в WHERE предложении используются операторы сравнения: >, =, >=, <=.
                
                 Задача: Для представленной ниже схемы напишите запрос, который выполнит выборку всех записей,
                 которые удовлетворяют условию - длина строки в столбце first_name должна быть строго больше 4.
                 Для того чтобы получить длину строки используйте функцию length(название_столбца).
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
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM customers WHERE LENGTH(first_name) > 4";
    }
}
