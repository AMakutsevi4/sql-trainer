package ru.sqltrainer.sql_trainer.tasks.select;

import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.task.SqlTask;

@Component
public class Select16DistinctOnNamePerson extends SqlTask {

    @Override
    public String id() {
        return "select-16";
    }

    @Override
    public String section() {
        return "SELECT";
    }

    @Override
    public int order() {
        return 16;
    }

    @Override
    public String title() {
        return "6.1 Предложение DISTINCT ON (accounts)";
    }

    @Override
    public String description() {
        return """
                SELECT DISTINCT ON (столбцы_уникальности) столбцы_выборки FROM таблица;
                Уникальность учитывается только по столбцам в скобках,
                а в результат попадают и другие указанные столбцы.
                
                Задача: уникальные записи по name_person,
                в выборке: name_person, login, password.
                Используйте ORDER BY name_person, id.
                
                Внимание: DISTINCT ON есть только в PostgreSQL.
                """;
    }

    @Override
    public String schema() {
        return """
                CREATE TABLE accounts(
                    id int not null primary key,
                    name_person varchar(255),
                    login varchar(255),
                    password varchar(255)
                )
                """;
    }

    @Override
    public String data() {
        return """
                INSERT INTO accounts VALUES(1, 'Ivan Petrov', 'ipetrov', 'qwerty');
                INSERT INTO accounts VALUES(2, 'Maria Sokolova', 'msokol', 'pass01');
                INSERT INTO accounts VALUES(3, 'Anna Volkova', 'avolk', 'admin1');
                INSERT INTO accounts VALUES(4, 'Ivan Petrov', 'ivan_p', 'qwerty');
                INSERT INTO accounts VALUES(5, 'Maria Sokolova', 'maria_s', 'pass01');
                INSERT INTO accounts VALUES(6, 'Anna Volkova', 'anna_v', 'admin1')
                """;
    }

    @Override
    public String starter() {
        return "SELECT";
    }

    @Override
    public String expected() {
        return """
                id;name_person;login;password;
                3;Anna Volkova;avolk;admin1;
                1;Ivan Petrov;ipetrov;qwerty;
                2;Maria Sokolova;msokol;pass01;
                """;
    }

    @Override
    public String solution() {
        return "SELECT DISTINCT ON (name_person) id, name_person, login, password FROM accounts ORDER BY name_person, id";
    }
}
