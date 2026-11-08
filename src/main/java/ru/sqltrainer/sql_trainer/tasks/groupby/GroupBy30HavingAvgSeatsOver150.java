package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class GroupBy30HavingAvgSeatsOver150 extends SqlTask {

    @Override
    public String id() {
        return "groupby-30";
    }

    @Override
    public String section() {
        return "4. GROUP BY";
    }

    @Override
    public int order() {
        return 30;
    }

    @Override
    public String title() {
        return "GROUP BY и HAVING 10";
    }

    @Override
    public String description() {
        return """
                GROUP BY позволяет разделить данные, которые возвращены с помощью SELECT на группы.

                При этом для каждой из групп можно будет использовать агрегатные функции, например COUNT() для подсчета предметов в каждой из групп.

                Вместе с GROUP BY можно использовать HAVING. Этот оператор позволяет указать какие группы будут включены в выходной результат. Т.е. просто выполняют фильтрацию групп. Но очень важно усвоить разницу между WHERE и HAVING - в отличие от WHERE оператор HAVING будет выполняться после группировки данных. Т.е. WHERE фильтрует строки, а HAVING - группы после их группировки.

                Синтаксис имеет следующий вид:

                SELECT
                    столбец_1,
                    столбец_2,
                    ...,
                    aggregate_function(столбец_3)
                FROM
                    название_таблицы
                GROUP BY
                    столбец_1,
                    столбец_2,
                    ...
                HAVING
                    условие_фильтрации_групп;
                Необходимо учесть - любой столбец, который указан в SELECT (столбец, который хранит результат вычисления агрегатных функций, не считается), должен быть указан после GROUP BY.

                Вывести среднее количество мест для каждого производителя самолетов. Необходимо использовать оператор GROUP BY для группировки данных по производителю. Необходимо включить условие HAVING, чтобы выводить только те производителей, у которых среднее количество мест больше определенного значения (например, больше 150 мест).
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE airplanes
                (
                    id           SERIAL PRIMARY KEY,
                    manufacturer VARCHAR(50),
                    model        VARCHAR(50),
                    seats        INT,
                    range        INT
                );
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO airplanes(manufacturer, model, seats, range)
                VALUES ('Airbus', 'A320', 150, 6000),
                       ('Airbus', 'A330', 250, 8000),
                       ('Boeing', '737', 120, 5500),
                       ('Boeing', '747', 400, 10000),
                       ('Embraer', 'E190', 100, 4500),
                       ('Embraer', 'E195', 120, 5000),
                       ('Bombardier', 'CRJ900', 90, 2500),
                       ('Bombardier', 'Global 6000', 19, 11000),
                       ('Cessna', 'Citation X', 8, 3700),
                       ('Cessna', '172 Skyhawk', 4, 900);
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                manufacturer;avg;
                Airbus;200.0000000000000000;
                Boeing;260.0000000000000000;
                """;
    }

    @Override
    public String solution() {
        return """
                SELECT manufacturer, AVG(seats) AS avg
                FROM airplanes
                GROUP BY manufacturer
                HAVING AVG(seats) > 150
                ORDER BY manufacturer
                """;
    }
}
