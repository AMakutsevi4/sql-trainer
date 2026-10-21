package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select32MaxGroupBy extends SqlTask {

    @Override
    public String id() {
        return "select-32";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 32;
    }

    @Override
    public String title() {
        return "7.14 Агрегатные функции. MAX + GROUP BY";
    }

    @Override
    public String description() {
        return """
                 SELECT столбец_группировки, MAX(столбец) FROM таблица
                 GROUP BY столбец_группировки;
                
                 Задача: максимум amount для каждой purpose,
                 отсортировать по purpose.
                 Используйте alias: AS max
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
                INSERT INTO transactions VALUES(2, 'Current payment', 2, 80, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(3, 'Service', 3, 190, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(4, 'Vacation', 4, 250, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(5, 'Credit', 1, 240, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(6, 'Current payment', 2, 100, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(7, 'Service', 3, 170, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(8, 'Vacation', 4, 190, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(9, 'Credit', 1, 180, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(10, 'Current payment', 2, 60, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(11, 'Service', 3, 150, '2022-06-01 12:00:00');
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
                purpose;max;
                Credit;240;
                Current payment;100;
                Service;190;
                Vacation;250;
                """;
    }

    @Override
    public String solution() {
        return "SELECT purpose, MAX(amount) AS max FROM transactions GROUP BY purpose";
    }
}
