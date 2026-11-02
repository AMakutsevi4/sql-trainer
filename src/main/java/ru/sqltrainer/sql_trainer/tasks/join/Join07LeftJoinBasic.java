package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join07LeftJoinBasic extends SqlTask {

    @Override
    public String id() {
        return "join-07";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 7;
    }

    @Override
    public String title() {
        return "1.7 Левое внешнее объединение (LEFT JOIN)";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, когда в одной из них могут отсутствовать связанные строки, используется LEFT JOIN (левое внешнее объединение).
                
                Этот оператор возвращает все строки из левой таблицы (указанной во FROM), даже если для них нет соответствующих записей в правой таблице.
                Для несовпадающих строк значения столбцов правой таблицы будут заполнены значениями NULL.
                Синтаксис запроса:
                SELECT поля FROM таблица_А LEFT JOIN таблица_Б ON таблица_А.ключ = таблица_Б.внешний_ключ;

                Задача: Выполните запрос с использованием LEFT JOIN. В выборке должны быть отражены значения столбцов id, name, number, description.
                Объединение производите по столбцу id из таблицы colors и color_id из таблицы actions. В качестве псевдонимов используйте первые буквы названий таблиц (c и a).
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
                INSERT INTO actions VALUES (2, 'use black hole', 2);
                """;
    }

    @Override
    public String starter() {
        return """
                SELECT
                """;
    }

    @Override
    public String expected() {
        return """
                id;name;number;description;
                1;red;1;draw red;
                2;white;2;use black hole;
                4;purple;null;null;
                3;black;null;null;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT c.id, c.name, a.number, a.description
                FROM colors c
                LEFT JOIN actions a ON c.id = a.color_id
                ORDER BY CASE WHEN a.number IS NULL THEN 1 ELSE 0 END, a.number ASC, c.id DESC
                """;
    }
}
