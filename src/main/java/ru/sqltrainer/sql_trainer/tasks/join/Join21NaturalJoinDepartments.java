package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join21NaturalJoinDepartments extends SqlTask {

    @Override
    public String id() {
        return "join-21";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 21;
    }

    @Override
    public String title() {
        return "1.21 NATURAL JOIN 4";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. В свою очередь они бывают разных видов. Рассмотрим NATURAL JOIN.

                NATURAL JOIN – это соединение, которое создает неявное соединение на основе тех столбцов в соединяемых таблицах которые имеют одинаковые имена.

                Например, есть таблицы:

                CREATE TABLE categories (
                    category_id int PRIMARY KEY,
                    category_name text NOT NULL
                );

                CREATE TABLE products (
                    product_id int PRIMARY KEY,
                    product_name text,
                    category_id int references categories(category_id) NOT NULL
                );
                В данном случае category_id в таблице products – это внешний ключ, который ссылается на первичный ключ таблицы categories. Мы видим, что названия столбцов совпадают и, соответственно, его мы будем использовать для внешнего соединения.

                Простейший вариант использования NATURAL JOIN будет иметь вид:

                SELECT *
                FROM categories
                NATURAL JOIN products;
                Удобство NATURAL JOIN заключается в том, что нам не нужно указывать логическое выражение для соединения, поскольку в данном случае используется неявное предложение соединения, которое основывается на совпадении значений общего столбца в таблицах.

                Для представленной ниже схемы напишите запрос с использованием NATURAL JOIN. Это должен быть INNER JOIN. Должны быть в результатах отражены значения всех столбцов, используйте (*).
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE departments (
                    department_id int primary key,
                    name text not null
                );

                CREATE TABLE employees (
                    id int primary key,
                    employee_name text,
                    department_id int references departments(department_id)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO departments VALUES (1, 'Sales');
                INSERT INTO departments VALUES (2, 'Marketing');
                INSERT INTO departments VALUES (3, 'HR');
                INSERT INTO departments VALUES (4, 'IT');
                INSERT INTO departments VALUES (5, 'Production');

                INSERT INTO employees VALUES (1, 'Kirill Lebedev', 1);
                INSERT INTO employees VALUES (2, 'Anton Voronin', 1);
                INSERT INTO employees VALUES (3, 'Daria Frolova', 2);
                INSERT INTO employees VALUES (4, 'Roman Gusev', 3);
                INSERT INTO employees VALUES (5, 'Elena Pavlova', 4);
                INSERT INTO employees VALUES (6, 'Maxim Krylov', null);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                department_id;name;id;employee_name;
                1;Sales;1;Kirill Lebedev;
                1;Sales;2;Anton Voronin;
                2;Marketing;3;Daria Frolova;
                3;HR;4;Roman Gusev;
                4;IT;5;Elena Pavlova;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT *
                FROM departments
                NATURAL JOIN employees
                ORDER BY id
                """;
    }
}
