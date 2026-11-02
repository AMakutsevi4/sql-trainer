package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join01InnerJoinBasic extends SqlTask {
    @Override
    public String id() {
        return "join-01";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public String title() {
        return "1.1 Объединение таблиц (INNER JOIN)";
    }

    @Override
    public String description() {
        return """
                В реляционной БД данные обычно распределяются более чем в одной таблице. Чтобы выбрать связанные данные, используется оператор INNER JOIN.
                Для объединения таблиц используется внешний ключ (FOREIGN KEY) — это столбец, который ссылается на первичный ключ другой таблицы.
                Синтаксис запроса:
                SELECT поля FROM таблица_А INNER JOIN таблица_Б ON таблица_А.ключ = таблица_Б.внешний_ключ;

                Задача: Напишите запрос, в выборке которого должны быть данные всех строк.
                Из столбцов необходимо отразить name и description. 
                Связь между таблицами настройте по столбцу id в colors и color_id в actions.
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
                INSERT INTO colors VALUES(2, 'black');

                INSERT INTO actions VALUES (1, 'draw red', 1);
                INSERT INTO actions VALUES (2, 'use black hole', 2);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                name;description;
                red;draw red;
                black;use black hole;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT name, description
                FROM colors
                INNER JOIN actions ON colors.id = actions.color_id
                """;
    }
}
