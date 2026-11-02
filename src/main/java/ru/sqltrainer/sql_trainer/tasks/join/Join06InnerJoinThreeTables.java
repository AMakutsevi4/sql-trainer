package ru.sqltrainer.sql_trainer.tasks.join;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Join06InnerJoinThreeTables extends SqlTask {

    @Override
    public String id() {
        return "join-06";
    }

    @Override
    public String section() {
        return "3. JOIN";
    }

    @Override
    public int order() {
        return 6;
    }

    @Override
    public String title() {
        return "1.6 Объединение трех и более таблиц";
    }

    @Override
    public String description() {
        return """
                В SQL можно объединять более двух таблиц. Для этого предложения JOIN записываются последовательно друг за другом:
                SELECT ... FROM A a
                JOIN B b ON a.id = b.a_id
                JOIN C c ON b.id = c.b_id;

                Задача: Выполните запрос объединения трех таблиц. В результатах выборки должны быть отражены значения столбцов id из таблицы users, email, name, login, password, amount, payment_date.
                Объединение настройте по столбцам id из таблицы users и user_id из accounts, а также id из таблицы accounts и account_id из payment.
                В качестве псевдонимов используйте первые буквы названия таблиц (u, a, p).
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE users (
                    id int primary key,
                    email text,
                    name text
                );

                CREATE TABLE accounts (
                    id int primary key,
                    login text,
                    password text,
                    user_id int references users(id)
                );

                CREATE TABLE payment (
                    id int primary key,
                    amount decimal,
                    payment_date date,
                    account_id int references accounts(id)
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO users VALUES (1, 'email_1@mail.ru', 'first_name');
                INSERT INTO users VALUES (2, 'email_2@bk.ru', 'second_name');

                INSERT INTO accounts VALUES (1, 'login_1', '12345', 1);
                INSERT INTO accounts VALUES (2, 'login_2', '67890', 2);

                INSERT INTO payment VALUES (1, 100.0, '2022-09-09', 1);
                INSERT INTO payment VALUES (2, 200.0, '2022-08-08', 2);
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
                id;email;name;login;password;amount;payment_date;
                1;email_1@mail.ru;first_name;login_1;12345;100.0;2022-09-09;
                2;email_2@bk.ru;second_name;login_2;67890;200.0;2022-08-08;
                """;
    }

    @Override
    public String solution() {
        return  """
                SELECT u.id, u.email, u.name, a.login, a.password, CAST(p.amount AS numeric(10,1)) AS amount, p.payment_date
                FROM users u 
                JOIN accounts a ON u.id = a.user_id 
                JOIN payment p ON a.id = p.account_id
                ORDER BY u.id
                """;
    }
}
