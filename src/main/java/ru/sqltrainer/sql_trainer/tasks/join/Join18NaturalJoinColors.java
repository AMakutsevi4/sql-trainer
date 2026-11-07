package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join18NaturalJoinColors extends SqlTask {

    @Override
    public String id() {
        return "join-18";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 18;
    }

    @Override
    public String title() {
        return "1.18 NATURAL JOIN 1";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. В свою очередь они бывают разных видов. Рассмотрим NATURAL JOIN.

                NATURAL JOIN – это соединение, которое создает неявное соединение на основе тех столбцов в соединяемых таблицах которые имеют одинаковые имена.

                Ниже представлен синтаксис natural join в Postgres:

                SELECT список_столбцов
                FROM table_1
                NATURAL [INNER, LEFT, RIGHT] JOIN table_2;
                То, что указано в квадратных скобках не является обязательным параметром.

                Для представленной ниже схемы выполните запрос с использованием NATURAL JOIN. В результатах отражаем значения всех столбцов.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE colors (
                    color_id int primary key,
                    name text
                );

                CREATE TABLE actions (
                    number int primary key,
                    description text,
                    color_id int references colors(color_id)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO colors VALUES(1, 'red');
                INSERT INTO colors VALUES(2, 'white');
                INSERT INTO colors VALUES(3, 'black');
                INSERT INTO colors VALUES(4, 'purple');

                INSERT INTO actions VALUES (1, 'draw red', 1);
                INSERT INTO actions VALUES (2, 'pink panther', null);
                INSERT INTO actions VALUES (3, 'use for painting', null);
                INSERT INTO actions VALUES (4, 'use black hole', 2);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                color_id;name;number;description;
                1;red;1;draw red;
                2;white;4;use black hole;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT *
                FROM colors
                NATURAL JOIN actions
                ORDER BY color_id
                """;
    }
}
