package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join22NaturalJoinNameTrap extends SqlTask {

    @Override
    public String id() {
        return "join-22";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 22;
    }

    @Override
    public String title() {
        return "1.22 NATURAL JOIN 5";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. В свою очередь они бывают разных видов. Рассмотрим NATURAL JOIN.

                NATURAL JOIN – это соединение, которое создает неявное соединение на основе тех столбцов в соединяемых таблицах которые имеют одинаковые имена.

                Например, есть таблицы:

                CREATE TABLE departments (
                    department_id int primary key,
                    name text not null
                );

                CREATE TABLE employees (
                    id int primary key,
                    employee_name text,
                    department_id int references departments(department_id)
                );
                В данном случае department_id в таблице employees – это внешний ключ, который ссылается на первичный ключ таблицы departments. Мы видим, что названия столбцов совпадают и, соответственно, его мы будем использовать для внешнего соединения.

                Тем не менее, следует избегать использования NATURAL JOIN в том случае, если это возможно, поскольку иногда это может привести к неожиданному результату. Например, представленные выше таблицы немного изменим:

                CREATE TABLE departments (
                    department_id int primary key,
                    name text not null
                );

                CREATE TABLE employees (
                    id int primary key,
                    name text,
                    department_id int references departments(department_id)
                );
                Как и ранее обе таблицы имеют один и тот же столбец department_id, следственно мы можем использовать NATURAL JOIN. Однако следующий запрос:

                SELECT *
                FROM departments
                NATURAL JOIN employees;
                Вернет пустой набор результатов. Связано это с тем, что в таблицах имеется одинаковый столбец name, который и будет использован для выполнения NATURAL JOIN.

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
                    name text,
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
                department_id;name;id;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT *
                FROM departments
                NATURAL JOIN employees
                """;
    }
}
