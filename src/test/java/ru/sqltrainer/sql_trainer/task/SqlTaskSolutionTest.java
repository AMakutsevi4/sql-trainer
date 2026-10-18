package ru.sqltrainer.sql_trainer.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class SqlTaskSolutionTest {

    @Autowired
    private List<SqlTask> tasks;

    @Test
    void everyTaskSolutionMatchesExpected() {
        assertTrue(!tasks.isEmpty(), "Нет задач — добавь SqlTask с @Component");

        for (SqlTask task : tasks) {
            try {
                boolean ok = SqlQueryExecutor.matches(
                        task.schema(),
                        task.data(),
                        task.solution(),
                        task.expected()
                );
                assertTrue(ok, () -> "solution не совпал с expected: " + task.id());
            } catch (Exception e) {
                fail("Упал solution у " + task.id() + ": " + e.getMessage());
            }
        }
    }
}
