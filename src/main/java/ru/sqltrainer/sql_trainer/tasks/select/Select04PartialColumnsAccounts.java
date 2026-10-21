package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select04PartialColumnsAccounts extends SqlTask {

    @Override
    public String id() {
        return "select-04";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 4;
    }

    @Override
    public String title() {
        return "2.1 Выбор части полей с их названиями (accounts)";
    }

    @Override
    public String description() {
        return """
                 SELECT названия_полей_через_запятую FROM название_таблицы;
                 Можно указать не все поля, если они в выборке не нужны.
                
                 Задача: выбрать все записи из accounts,
                 только поля name и login.
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
                name;login;
                Ivan Petrov;ipetrov;
                Maria Sokolova;msokol;
                Dmitry Orlov;dorlov;
                Anna Volkova;avolk;
                Sergey Kozlov;skozlov;
                """;
    }

    @Override
    public String solution() {
        return "SELECT name, login FROM accounts";
    }
}
