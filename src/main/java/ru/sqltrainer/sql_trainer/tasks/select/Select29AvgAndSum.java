package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select29AvgAndSum extends SqlTask {

    @Override
    public String id() {
        return "select-29";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 29;
    }

    @Override
    public String title() {
        return "7.11 Агрегатные функции. AVG и SUM вместе";
    }

    @Override
    public String description() {
        return """
                 7.11 Агрегатные функции. AVG и SUM вместе
                
                 SELECT AVG(столбец), SUM(столбец) FROM таблица;
                
                 Задача: среднее и сумма по amount.
                 Используйте alias: AS avg, AS sum
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
                INSERT INTO transactions VALUES(3, 'Service', 3, 200, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(4, 'Vacation', 4, 250, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(5, 'Delivery', 3, 150, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(6, 'Current payment', 4, 70, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(7, 'Service', 1, 130, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(8, 'Credit', 2, 600, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(9, 'Vacation', 1, 210, '2022-06-01 12:00:00')
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                avg;sum;
                201.11111111111111;1810;
                """;
    }

    @Override
    public String solution() {
        return "SELECT AVG(amount) AS avg, SUM(amount) AS sum FROM transactions";
    }
}
