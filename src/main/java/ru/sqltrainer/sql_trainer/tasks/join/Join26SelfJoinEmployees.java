package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join26SelfJoinEmployees extends SqlTask {

    @Override
    public String id() {
        return "join-26";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 26;
    }

    @Override
    public String title() {
        return "1.26 SELF JOIN 1";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. В свою очередь они бывают разных видов. Рассмотрим SELF JOIN.

                SELF JOIN – это соединение, которое соединяет таблицу с самой собой.

                Чтобы сформировать SELF JOIN, мы указываем одну и ту же таблицу 2 раза с разными псевдонимами таблиц указываем условие объединения после ключевого слова ON.

                Например, если мы используем INNER JOIN, то запрос будет иметь вид:

                SELECT список_столбцов
                FROM table_name t1
                INNER JOIN table_name t2 ON условие_объединения;
                Кроме того, также можно использовать предложения LEFT JOIN и RIGHT JOIN для присоединения таблицы к самой себе.

                Для представленной ниже схемы напишите запрос с использованием INNER JOIN. В итоге вы получите SELF JOIN. Не забудьте про использование псевдонимов – для таблицы к которой присоединяем используем e, и которую присоединяем – m. В качестве условия соединения используйте выражение m.e_id = e.superviser_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE employees (
                    e_id int primary key,
                    name text,
                    superviser_id int references employees(e_id)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO employees VALUES (1, 'Oleg', null);
                INSERT INTO employees VALUES (2, 'Igor', 1);
                INSERT INTO employees VALUES (3, 'Nina', 1);
                INSERT INTO employees VALUES (4, 'Vera', 2);
                INSERT INTO employees VALUES (5, 'Yuri', 2);
                INSERT INTO employees VALUES (6, 'Boris', 3);
                INSERT INTO employees VALUES (7, 'Klim', 3);
                INSERT INTO employees VALUES (8, 'Gleb', 3);
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
                Igor;Oleg;
                Nina;Oleg;
                Vera;Igor;
                Yuri;Igor;
                Boris;Nina;
                Klim;Nina;
                Gleb;Nina;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT e.name, m.name
                FROM employees e
                INNER JOIN employees m ON m.e_id = e.superviser_id
                ORDER BY e.e_id
                """;
    }
}
