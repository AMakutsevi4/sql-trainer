package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join25CrossJoinAuthorsAlt extends SqlTask {

    @Override
    public String id() {
        return "join-25";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 25;
    }

    @Override
    public String title() {
        return "1.25 CROSS JOIN 3";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется JOIN. В свою очередь они бывают разных видов. Рассмотрим CROSS JOIN.

                Предложение CROSS JOIN позволяет нам производить декартово произведение строк в двух или более таблиц.

                Синтаксис этого соединения достаточно прост:

                SELECT список_столбцов
                FROM table_1
                CROSS JOIN table_2;
                Однако это выражение можно переписать без использования CROSS JOIN:

                SELECT список_столбцов
                FROM table_1, table_2;
                Т.е. если во FROM перечислять таблицы через запятую, то PostgreSQL по умолчанию будет выполнять CROSS JOIN.

                К тому же, для имитации перекрестного соединения можно использовать INNER JOIN с условием, которое будет всегда оцениваться как true:

                SELECT список_столбцов
                FROM table_1
                INNER JOIN table_2 ON true;
                Для приведенной ниже схемы напишите запрос с имитацией поведения CROSS JOIN, выбирайте любой из представленных выше вариантов. В результатах должны быть отражены значения столбцов a_name, b_name.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE authors (
                    a_id int primary key,
                    a_name text
                );

                CREATE TABLE books (
                    b_id int primary key,
                    b_name text,
                    author_id int references authors(a_id)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO authors VALUES (1, 'James Gosling');
                INSERT INTO authors VALUES (2, 'Herbert Schildt');
                INSERT INTO authors VALUES (3, 'David Patterson');

                INSERT INTO books VALUES (1, 'Modern Operating System', 3);
                INSERT INTO books VALUES (2, 'Thinking in Java', 1);
                INSERT INTO books VALUES (3, 'Computer Architecture', 3);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                a_name;b_name;
                James Gosling;Modern Operating System;
                James Gosling;Thinking in Java;
                James Gosling;Computer Architecture;
                Herbert Schildt;Modern Operating System;
                Herbert Schildt;Thinking in Java;
                Herbert Schildt;Computer Architecture;
                David Patterson;Modern Operating System;
                David Patterson;Thinking in Java;
                David Patterson;Computer Architecture;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT a_name, b_name
                FROM authors, books
                ORDER BY a_id, b_id
                """;
    }
}
