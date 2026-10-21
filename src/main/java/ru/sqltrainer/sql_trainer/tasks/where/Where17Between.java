package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where17Between extends SqlTask {

    @Override
    public String id() {
        return "where-17";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 17;
    }

    @Override
    public String title() {
        return "16. Предложение WHERE с оператором BETWEEN";
    }

    @Override
    public String description() {
        return """
                 Для того, чтобы выбрать строки, которые удовлетворяют заданному условию,
                 в SQL используется WHERE предложение.
                
                 При этом иногда необходимо сделать выборку строк чтобы при проверке условия
                 значение в столбце находилось в каком-то диапазоне значений.
                 В этом случае нам поможет оператор BETWEEN. Синтаксис будет иметь следующий вид:
                 SELECT имена_столбцов_через_запятую FROM название_таблицы
                 WHERE столбец_для_проверки BETWEEN значение_1 AND значение_2;
                
                 Задача: Для представленной ниже схемы напишите запрос, который вернет только те транзакции,
                 значение поле в amount находится в пределах от 100 до 200.
                 В выборке должны быть отражены значения всех столбцов.
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
        return  """
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
                3;Service;3;185;2022-06-01 12:00:00.0;
                6;Current payment;2;110;2022-06-01 12:00:00.0;
                7;Service;3;165;2022-06-01 12:00:00.0;
                8;Vacation;4;190;2022-06-01 12:00:00.0;
                9;Credit;1;195;2022-06-01 12:00:00.0;
                11;Service;3;145;2022-06-01 12:00:00.0;
                12;Vacation;4;140;2022-06-01 12:00:00.0;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM transactions WHERE amount BETWEEN 100 AND 200";
    }
}
