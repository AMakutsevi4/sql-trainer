package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join03InnerJoinAccounts extends SqlTask {

    @Override
    public String id() {
        return "join-03";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 3;
    }

    @Override
    public String title() {
        return "1.3 Принцип работы объединения таблиц (INNER JOIN)";
    }

    @Override
    public String description() {
        return """
                Для того чтобы получить данные из нескольких таблиц, используется INNER JOIN.
                
                Как это работает:
                Для каждой строки в таблице А внутреннее соединение сравнивает значение связующего столбца со значением столбца в таблице В.
                - Если эти значения равны, создается новая строка, которая содержит столбцы обеих таблиц, и добавляется в результаты выборки.
                - Если значения не равны, они просто игнорируются.

                Задача: Напишите запрос, который вернет все строки. В результатах должны быть отражены данные столбцов id, login, password, email, name. 
                Объединение должно происходить по столбцам id и account_id.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE accounts (
                    id int primary key,
                    login text,
                    password text
                );

                CREATE TABLE users (
                    u_id int primary key,
                    email text,
                    name text,
                    account_id int references accounts(id)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO accounts VALUES (1, 'login_1', '12345');
                INSERT INTO accounts VALUES (2, 'login_2', '67890');

                INSERT INTO users VALUES (1, 'email_1@mail.ru', 'first_name', 1);
                INSERT INTO users VALUES (2, 'email_2@bk.ru', 'second_name', 2);
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
                id;login;password;email;name;
                1;login_1;12345;email_1@mail.ru;first_name;
                2;login_2;67890;email_2@bk.ru;second_name;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT id, login, password, email, name 
                FROM accounts 
                INNER JOIN users ON accounts.id = users.account_id
                """;
    }
}
