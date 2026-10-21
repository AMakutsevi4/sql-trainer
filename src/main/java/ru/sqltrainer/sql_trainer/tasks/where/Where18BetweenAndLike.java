package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where18BetweenAndLike extends SqlTask {

    @Override
    public String id() {
        return "where-18";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 18;
    }

    @Override
    public String title() {
        return "17. Предложение WHERE с оператором BETWEEN и LIKE";
    }

    @Override
    public String description() {
        return """
                 Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
                 в SQL используется WHERE предложение.
                
                 При этом иногда необходимо сделать выборку строк чтобы при проверке условия
                 значение в столбце находилось в каком-то диапазоне значений.
                 В этом случае нам поможет оператор BETWEEN.
                
                 При этом важно понимать, что если значение в столбце для проверки больше или равно значению_1
                 и меньше или равно значению_2, то логическое выражение вернет true.
                
                 Задача: Для представленной ниже схемы напишите запрос, который вернет только те строки,
                 в которых значения в столбце first_name начинается с подстроки An,
                 и длина всей строки в это столбце находится в пределах от 3 до 5 символов
                 (используйте функцию length()).
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
                INSERT INTO customers VALUES(2, 'Andrey', 'Kozlov', false, 'andrey@mail.test');
                INSERT INTO customers VALUES(3, 'Anne', 'Morozova', true, 'anne@mail.test');
                INSERT INTO customers VALUES(4, 'Elena', 'Volkova', false, 'elena@mail.test');
                INSERT INTO customers VALUES(5, 'Annie', 'Orlova', true, 'annie@mail.test');
                INSERT INTO customers VALUES(6, 'Lana', 'Smirnova', true, 'lana@mail.test')
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
        return "SELECT * FROM customers WHERE first_name LIKE 'An%' AND LENGTH(first_name) BETWEEN 3 AND 5";
    }
}
