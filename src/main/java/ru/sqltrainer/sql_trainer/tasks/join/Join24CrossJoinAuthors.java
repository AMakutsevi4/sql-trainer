package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join24CrossJoinAuthors extends SqlTask {

    @Override
    public String id() {
        return "join-24";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 24;
    }

    @Override
    public String title() {
        return "1.24 CROSS JOIN 2";
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
                Если table_1 имеет n строк, а table_2 – m строк, то результирующий набор будет содержать n * m строк. Например, table_1 имеет 1000 строк, а table_2 – также 1000 строк, то в результирующем наборе будет уже 1_000_000 строк.

                Для приведенной ниже схемы напишите запрос с использованием CROSS JOIN. В результатах должны быть отражены значения столбцов a_name, b_name.
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
                FROM authors
                CROSS JOIN books
                ORDER BY a_id, b_id
                """;
    }
}
