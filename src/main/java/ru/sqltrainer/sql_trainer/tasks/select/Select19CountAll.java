package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select19CountAll extends SqlTask {

    @Override
    public String id() {
        return "select-19";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 19;
    }

    @Override
    public String title() {
        return "7.1 Агрегатные функции. COUNT(*)";
    }

    @Override
    public String description() {
        return """
                 SELECT COUNT(*) FROM таблица;
                 COUNT(*) возвращает число строк, включая null и дубликаты.
                
                 Задача: вернуть количество всех строк в accounts.
                 Используйте alias: COUNT(*) AS count
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE accounts(
                    id int not null primary key,
                    name varchar(255),
                    login varchar(255),
                    password varchar(255)
                )
                """;
    }

    @Override
    public String data() {
        return  """
                INSERT INTO accounts VALUES(1, 'Ivan Petrov', 'ipetrov', 'qwerty');
                INSERT INTO accounts VALUES(2, 'Maria Sokolova', 'msokol', 'pass01');
                INSERT INTO accounts VALUES(3, 'Dmitry Orlov', 'dorlov', 'secret');
                INSERT INTO accounts VALUES(4, 'Anna Volkova', 'avolk', 'admin1');
                INSERT INTO accounts VALUES(5, 'Sergey Kozlov', 'skozlov', 'sql123')
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                count;
                5;
                """;
    }

    @Override
    public String solution() {
        return "SELECT COUNT(*) AS count FROM accounts";
    }
}
