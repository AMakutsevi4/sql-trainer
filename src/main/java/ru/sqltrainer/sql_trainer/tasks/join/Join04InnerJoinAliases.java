package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join04InnerJoinAliases extends SqlTask {

    @Override
    public String id() {
        return "join-04";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 4;
    }

    @Override
    public String title() {
        return "1.4 Использование псевдонимов таблиц (Aliases)";
    }

    @Override
    public String description() {
        return """
                При объединении таблиц часто встречаются столбцы с одинаковыми именами. Если сослаться на них без указания таблицы, СУБД выдаст ошибку двусмысленности.
                
                Чтобы избежать ошибок и сделать запрос короче, таблицам присваивают псевдонимы (aliases):
                SELECT a.id, b.id FROM table_A a INNER JOIN table_B b ON a.id = b.a_id;
                Ключевое слово AS при указании псевдонима таблицы не является обязательным.

                Задача: Напишите запрос, который вернет все строки из двух таблиц.
                В результатах должны быть отражены значения столбцов id из accounts, email из accounts, password из accounts, email из users, name из users.
                Объединение должно происходить по столбцам id из accounts и account_id из users. В качестве псевдонимов таблиц используйте их первые буквы (a и u).
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE accounts (
                    id int primary key,
                    email text,
                    password text
                );

                CREATE TABLE users (
                    id int primary key,
                    email text,
                    name text,
                    account_id int references accounts(id)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO accounts VALUES (1, 'for_auth_1@mail.ru', '12345');
                INSERT INTO accounts VALUES (2, 'for_auth_1@bk.ru', '67890');

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
                id;email;password;email;name;
                1;for_auth_1@mail.ru;12345;email_1@mail.ru;first_name;
                2;for_auth_1@bk.ru;67890;email_2@bk.ru;second_name;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT a.id, a.email, a.password, u.email, u.name
                FROM accounts a
                INNER JOIN users u ON a.id = u.account_id
                """;
    }
}
