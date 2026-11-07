package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join13FullOuterJoinColors extends SqlTask {

    @Override
    public String id() {
        return "join-13";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 13;
    }

    @Override
    public String title() {
        return "1.13 FULL OUTER JOIN 1";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. При этом бывают не только внутренние, но и внешние JOIN. В свою очередь они бывают разных видов. Рассмотрим FULL OUTER JOIN.

                Например, мы хотим выполнить полное внешнее соединение двух таблиц: А и В. Синтаксис будет иметь вид:

                SELECT * FROM A
                FULL OUTER JOIN B on A.id = B.id;
                В этом синтаксисе ключевое слово OUTER является необязательным.

                Полное внешнее соединение объединяет результаты как левого, так и правого соединения.

                Для представленной ниже схемы напишите запрос с использованием FULL OUTER JOIN. В результатах должны быть отражены значения всех столбцов обоих таблиц. Левая таблица – colors, правая – actions.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE colors (
                    id int primary key,
                    name text
                );

                CREATE TABLE actions (
                    number int primary key,
                    description text,
                    color_id int references colors(id)
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
                id;name;number;description;color_id;
                1;red;1;draw red;1;
                null;null;2;pink panther;null;
                null;null;3;use for painting;null;
                2;white;4;use black hole;2;
                4;purple;null;null;null;
                3;black;null;null;null;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT colors.id, colors.name, actions.number, actions.description, actions.color_id
                FROM colors
                FULL OUTER JOIN actions ON colors.id = actions.color_id
                ORDER BY actions.number ASC NULLS LAST, colors.id DESC
                """;
    }
}
