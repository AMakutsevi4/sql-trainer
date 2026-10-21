package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where01ById extends SqlTask {

    @Override
    public String id() {
        return "where-01";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public String title() {
        return "Теория. Выборка по where id.";
    }

    @Override
    public String description() {
        return """
                Нужно получить запись с ID = 1;
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE account(
                    id int not null primary key,
                    name varchar(255)
                )
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO account VALUES (1, 'Ivan Petrov');
                INSERT INTO account VALUES (2, 'Maria Sokolova');
                INSERT INTO account VALUES (3, 'Dmitry Orlov')
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                id;name;
                1;Ivan Petrov;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM account WHERE id = 1";
    }
}
