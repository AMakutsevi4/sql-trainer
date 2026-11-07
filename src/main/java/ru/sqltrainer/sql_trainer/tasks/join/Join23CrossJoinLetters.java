package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join23CrossJoinLetters extends SqlTask {

    @Override
    public String id() {
        return "join-23";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 23;
    }

    @Override
    public String title() {
        return "1.23 CROSS JOIN 1";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. В свою очередь они бывают разных видов. Рассмотрим CROSS JOIN.

                Предложение CROSS JOIN позволяет нам производить декартово произведение строк в двух или более таблиц.

                В отличие от других предложений JOIN (LEFT JOIN или INNER JOIN), CROSS JOIN не имеет предиката соединения.

                Таким образом синтаксис этого соединения достаточно прост:

                SELECT список_столбцов
                FROM table_1
                CROSS JOIN table_2;
                Для приведенной ниже схемы напишите запрос с использованием CROSS JOIN. В результатах должны быть отражены значения всех столбцов.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE letters (
                    l_id int primary key,
                    label char(1)
                );

                CREATE TABLE numbers (
                    n_id int primary key,
                    score int
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO letters VALUES (1, 'A');
                INSERT INTO letters VALUES (2, 'B');

                INSERT INTO numbers VALUES (1, 1);
                INSERT INTO numbers VALUES (2, 2);
                INSERT INTO numbers VALUES (3, 3);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                l_id;label;n_id;score;
                1;A;1;1;
                2;B;1;1;
                1;A;2;2;
                2;B;2;2;
                1;A;3;3;
                2;B;3;3;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT *
                FROM letters
                CROSS JOIN numbers
                ORDER BY n_id, l_id
                """;
    }
}
