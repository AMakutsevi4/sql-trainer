package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select07AliasAccounts extends SqlTask {

    @Override
    public String id() {
        return "select-07";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 7;
    }

    @Override
    public String title() {
        return "3.1 Использование псевдонимов для столбцов (accounts)";
    }

    @Override
    public String description() {
        return """
                 SELECT имя_столбца AS имя_псевдонима FROM название_таблицы;
                 Псевдонимы пишутся в стиле snake_case.
                
                 Задача: выбрать столбцы name и password.
                 Для name используйте псевдоним person_name,
                 для password — account_password.
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
                person_name;account_password;
                Ivan Petrov;qwerty;
                Maria Sokolova;pass01;
                Dmitry Orlov;secret;
                Anna Volkova;admin1;
                Sergey Kozlov;sql123;
                """;
    }

    @Override
    public String solution() {
        return "SELECT name AS person_name, password AS account_password FROM accounts";
    }
}
