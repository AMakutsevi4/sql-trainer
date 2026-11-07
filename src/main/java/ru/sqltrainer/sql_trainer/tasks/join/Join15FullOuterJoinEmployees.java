package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join15FullOuterJoinEmployees extends SqlTask {

    @Override
    public String id() {
        return "join-15";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 15;
    }

    @Override
    public String title() {
        return "1.15 FULL OUTER JOIN 3";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. При этом бывают не только внутренние, но и внешние JOIN. В свою очередь они бывают разных видов. Рассмотрим FULL OUTER JOIN.

                Например, мы хотим выполнить полное внешнее соединение двух таблиц: А и В. Синтаксис будет иметь вид:

                SELECT * FROM A
                FULL OUTER JOIN B on A.id = B.id;
                Полное внешнее соединение объединяет результаты как левого, так и правого соединения.

                Для представленной ниже схемы напишите запрос с использованием FULL OUTER JOIN. Результирующий набор должен включать всех сотрудников, принадлежащих отделу, и всех отделов, в которых есть сотрудник. Кроме того, результат должен включать каждого сотрудника, не принадлежащего отделу, и каждый отдел, в котором нет сотрудников. В результат отражаем значения столбцов name из каждой таблицы. Левая таблица – employees, правая – departments.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE departments (
                    id int primary key,
                    name text not null
                );

                CREATE TABLE employees (
                    id int primary key,
                    name text,
                    department_id int references departments(id)
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
                name;name;
                Kirill Lebedev;Sales;
                Anton Voronin;Sales;
                Daria Frolova;Marketing;
                Roman Gusev;HR;
                Elena Pavlova;IT;
                Maxim Krylov;null;
                null;Production;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT e.name, d.name
                FROM employees e
                FULL OUTER JOIN departments d ON e.department_id = d.id
                ORDER BY e.id ASC NULLS LAST, d.id ASC
                """;
    }
}
