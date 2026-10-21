package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where07AndPurposeAmount extends SqlTask {

    @Override
    public String id() {
        return "where-07";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 7;
    }

    @Override
    public String title() {
        return "1.6. Предложение WHERE с несколькими условиями с помощью AND 2";
    }

    @Override
    public String description() {
        return """
                 Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
                 в SQL используется WHERE предложение.
                
                 При этом условия можно комбинировать и проверять несколько условий.
                 Для этого используется логическая операция AND (И). Синтаксис имеет следующий вид:
                 SELECT имена_столбцов_через_запятую FROM название_таблицы
                 WHERE условие1 AND условие2 AND условие3;
                 В итоге запрос вернет только те строки, которые удовлетворяют всем условиям,
                 которые объединены с помощью AND.
                
                Задача: Для представленной ниже схемы transactions напишите запрос, который выполнит выборку всех записей,
                 удовлетворяющих условиям - столбец purpose не равно 'Vacation' и amount меньше 150 усл. ед.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE transactions(
                    id int not null primary key,
                    purpose varchar(255),
                    client_id int,
                    amount int,
                    payment_date timestamp
                )
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO transactions VALUES(1, 'Credit', 1, 120, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(2, 'Current payment', 2, 90, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(3, 'Service', 3, 185, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(4, 'Vacation', 4, 260, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(5, 'Credit', 1, 230, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(6, 'Current payment', 2, 110, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(7, 'Service', 3, 165, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(8, 'Vacation', 4, 190, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(9, 'Credit', 1, 195, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(10, 'Current payment', 2, 75, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(11, 'Service', 3, 145, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(12, 'Vacation', 4, 140, '2022-06-01 12:00:00')
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                id;purpose;client_id;amount;payment_date;
                1;Credit;1;120;2022-06-01 12:00:00.0;
                2;Current payment;2;90;2022-06-01 12:00:00.0;
                6;Current payment;2;110;2022-06-01 12:00:00.0;
                10;Current payment;2;75;2022-06-01 12:00:00.0;
                11;Service;3;145;2022-06-01 12:00:00.0;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM transactions WHERE purpose != 'Vacation' AND amount < 150";
    }
}
