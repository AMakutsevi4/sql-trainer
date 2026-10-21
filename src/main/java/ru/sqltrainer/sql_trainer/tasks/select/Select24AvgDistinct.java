package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select24AvgDistinct extends SqlTask {

    @Override
    public String id() {
        return "select-24";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 24;
    }

    @Override
    public String title() {
        return "7.6 Агрегатные функции. AVG(DISTINCT)";
    }

    @Override
    public String description() {
        return """
                 SELECT AVG(DISTINCT имя_столбца) FROM таблица;
                 Считает среднее только среди уникальных значений.
                
                 Задача: среднее по уникальным значениям amount.
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
                INSERT INTO transactions VALUES(6, 'Current payment', 4, 80, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(7, 'Service', 1, 150, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(8, 'Credit', 2, 250, '2022-06-01 12:00:00');
                INSERT INTO transactions VALUES(9, 'Vacation', 1, 200, '2022-06-01 12:00:00')
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
                160.0;
                """;
    }

    @Override
    public String solution() {
        return "SELECT AVG(DISTINCT amount) AS avg FROM transactions";
    }
}
