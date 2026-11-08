package ru.sqltrainer.sql_trainer.tasks.groupby;

import org.junit.jupiter.api.Test;
import ru.sqltrainer.sql_trainer.engine.SqlQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupBy37CompaniesAvgBudgetTest {
    @Test
    void solutionMatchesExpected() throws Exception {
        GroupBy37CompaniesAvgBudget task = new GroupBy37CompaniesAvgBudget();
        assertTrue(SqlQueryExecutor.matches(task.schema(), task.data(), task.solution(), task.expected()));
    }
}