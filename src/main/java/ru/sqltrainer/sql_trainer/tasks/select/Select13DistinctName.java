package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select13DistinctName extends SqlTask {

    @Override
    public String id() {
        return "select-13";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 13;
    }

    @Override
    public String title() {
        return "Удаление повторяющихся строк — DISTINCT (accounts)";
    }

    @Override
    public String description() {
        return """
                SELECT DISTINCT столбцы FROM таблица;
                Если указан один столбец — вернутся уникальные значения этого столбца.
                
                Задача: выбрать уникальные значения name.
                Добавьте ORDER BY name для стабильного порядка.
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
        return """
                INSERT INTO accounts VALUES(1, 'Ivan Petrov', 'ipetrov', 'qwerty');
                INSERT INTO accounts VALUES(2, 'Maria Sokolova', 'msokol', 'pass01');
                INSERT INTO accounts VALUES(3, 'Anna Volkova', 'avolk', 'admin1');
                INSERT INTO accounts VALUES(4, 'Ivan Petrov', 'ivan_p', 'qwerty');
                INSERT INTO accounts VALUES(5, 'Maria Sokolova', 'maria_s', 'pass01');
                INSERT INTO accounts VALUES(6, 'Anna Volkova', 'anna_v', 'admin1')
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                name;
                Anna Volkova;
                Ivan Petrov;
                Maria Sokolova;
                """;
    }

    @Override
    public String solution() {
        return "SELECT DISTINCT name FROM accounts";
    }
}
