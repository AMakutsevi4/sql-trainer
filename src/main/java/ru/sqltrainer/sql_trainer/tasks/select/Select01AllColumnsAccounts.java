package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

/** Пример задачи. Остальные делай так же: extends SqlTask + @Component. */
@Component
public class Select01AllColumnsAccounts extends SqlTask {

    @Override
    public String id() {
        return "select-01";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public String title() {
        return "1.1 Выбор всех полей с их названиями (accounts)";
    }

    @Override
    public String description() {
        return """
                Чтобы выбрать записи из таблицы используется синтаксис:
                SELECT названия_полей_через_запятую FROM название_таблицы;
                После последнего поля запятая не ставится.

                Задача: выбрать все записи из таблицы accounts,
                в выборке должны быть данные всех полей таблицы.
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
        return """
                SELECT
                """;
    }

    @Override
    public String expected() {
        return """
                id;name;login;password;
                1;Ivan Petrov;ipetrov;qwerty;
                2;Maria Sokolova;msokol;pass01;
                3;Dmitry Orlov;dorlov;secret;
                4;Anna Volkova;avolk;admin1;
                5;Sergey Kozlov;skozlov;sql123;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT * FROM accounts
                """;
    }
}
