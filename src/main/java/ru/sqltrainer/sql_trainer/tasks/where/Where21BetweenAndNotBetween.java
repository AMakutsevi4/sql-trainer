package ru.sqltrainer.sql_trainer.tasks.where;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Where21BetweenAndNotBetween extends SqlTask {

    @Override
    public String id() {
        return "where-21";
    }

    @Override
    public String section() {
        return "WHERE";
    }

    @Override
    public int order() {
        return 21;
    }

    @Override
    public String title() {
        return "17. Предложение WHERE с оператором BETWEEN и NOT BETWEEN";
    }

    @Override
    public String description() {
        return """
                Задача: Для представленной ниже схемы напишите запрос, который будет сочетать в себе 2 условия с помощью AND:
                
                 - в результаты выборки должны попасть только те строки, в которых в поле amount
                   находятся значения от 80 до 190
                 - в результаты должны попасть только те строки, в которых в поле payment_date
                 не находятся значения в пределах от '2022-02-01 12:00:00' до '2022-08-01 12:00:00'.
                 Вам понадобится оператор NOT BETWEEN.
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
                INSERT INTO transactions VALUES(1, 'Credit', 1, 125, '2021-05-10 12:00:00');
                INSERT INTO transactions VALUES(2, 'Current payment', 2, 88, '2021-11-20 12:00:00');
                INSERT INTO transactions VALUES(3, 'Service', 3, 194, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(4, 'Vacation', 4, 254, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(5, 'Credit', 1, 244, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(6, 'Current payment', 2, 114, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(7, 'Service', 3, 178, '2022-09-15 12:00:00');
                INSERT INTO transactions VALUES(8, 'Vacation', 4, 196, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(9, 'Credit', 1, 198, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(10, 'Current payment', 2, 78, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(11, 'Service', 3, 156, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(12, 'Vacation', 4, 150, '2022-01-05 12:00:00')
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
                1;Credit;1;125;2021-05-10 12:00:00.0;
                2;Current payment;2;88;2021-11-20 12:00:00.0;
                7;Service;3;178;2022-09-15 12:00:00.0;
                12;Vacation;4;150;2022-01-05 12:00:00.0;
                """;
    }

    @Override
    public String solution() {
        return "SELECT * FROM transactions WHERE amount BETWEEN 80 AND 190 AND payment_date NOT BETWEEN '2022-02-01 12:00:00' AND '2022-08-01 12:00:00'";
    }
}
