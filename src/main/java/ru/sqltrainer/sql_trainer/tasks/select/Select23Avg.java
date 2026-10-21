package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select23Avg extends SqlTask {

    @Override
    public String id() {
        return "select-23";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 23;
    }

    @Override
    public String title() {
        return "7.5 Агрегатные функции. AVG";
    }

    @Override
    public String description() {
        return """
                 SELECT AVG(имя_столбца) FROM таблица;
                
                 Задача: среднее значение по столбцу amount.
                 Используйте alias: AS avg
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
                avg;
                201.11111111111111;
                """;
    }

    @Override
    public String solution() {
        return "SELECT AVG(amount) AS avg FROM transactions;";
    }
}
