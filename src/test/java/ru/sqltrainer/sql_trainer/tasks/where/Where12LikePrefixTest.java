package ru.sqltrainer.sql_trainer.tasks.where;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.support.SqlTaskTester;

class Where12LikePrefixTest {

    @Test
    void shouldSelectByLikePrefix() throws Exception {
        String schema = """
                CREATE TABLE airplanes(
                    id int not null primary key,
                    model varchar(255),
                    range int,
                    capacity int
                )
                """;

        String data = """
                INSERT INTO airplanes VALUES(1, 'Airbus-A320-200', 6300, 190);
                INSERT INTO airplanes VALUES(2, 'Airbus-A321-200', 7400, 230);
                INSERT INTO airplanes VALUES(3, 'Airbus-A220-300', 6200, 160);
                INSERT INTO airplanes VALUES(4, 'Embraer E190', 4500, 110);
                INSERT INTO airplanes VALUES(5, 'Boeing 787-9', 14100, 290);
                INSERT INTO airplanes VALUES(6, 'Boeing 777-200', 9700, 310);
                INSERT INTO airplanes VALUES(7, 'Boeing 737-800', 5400, 180);
                INSERT INTO airplanes VALUES(8, 'Tupolev Tu-214', 6500, 210);
                INSERT INTO airplanes VALUES(9, 'Bombardier CRJ-200', 2700, 50)
                """;

        String expected = """
                id;model;range;capacity;
                1;Airbus-A320-200;6300;190;
                2;Airbus-A321-200;7400;230;
                3;Airbus-A220-300;6200;160;
                """;

        SqlTaskTester.assertQuery(schema, data, new Where12LikePrefix().query(), expected);
    }
}
