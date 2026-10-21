package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select10OrderByNameAsc extends SqlTask {

    @Override
    public String id() {
        return "select-10";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 10;
    }

    @Override
    public String title() {
        return "4.1 Сортировка результатов выборки (accounts)";
    }

    @Override
    public String description() {
        return """
                SELECT столбцы FROM таблица ORDER BY столбец ASC | DESC;
                ASC — по возрастанию (по умолчанию), DESC — по убыванию.
                
                Задача: выбрать все столбцы из accounts,
                отсортировать по name по возрастанию.
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
                id;name;login;password;
                4;Anna Volkova;avolk;admin1;
                3;Dmitry Orlov;dorlov;secret;
                1;Ivan Petrov;ipetrov;qwerty;
                2;Maria Sokolova;msokol;pass01;
                5;Sergey Kozlov;skozlov;sql123;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM accounts ORDER BY name ASC";
    }
}
