package ru.sqltrainer.sql_trainer.ui;

import org.springframework.stereotype.Service;
import ru.sqltrainer.sql_trainer.support.SqlQueryExecutor;

@Service
public class SqlRunner {

    public record QueryResult(String csv, String error) {
        public static QueryResult ok(String csv) {
            return new QueryResult(csv, null);
        }

        public static QueryResult fail(String error) {
            return new QueryResult("", error);
        }

        public boolean isOk() {
            return error == null;
        }
    }

    public QueryResult execute(SqlTask task, String studentSql) {
        try {
            return QueryResult.ok(SqlQueryExecutor.execute(
                    task.schemaSql(),
                    task.dataSql(),
                    studentSql
            ));
        } catch (Exception e) {
            return QueryResult.fail(e.getMessage());
        }
    }

    public boolean matchesExpected(SqlTask task, String studentSql) {
        try {
            return SqlQueryExecutor.matches(
                    task.schemaSql(),
                    task.dataSql(),
                    studentSql,
                    task.expectedCsv()
            );
        } catch (Exception e) {
            return false;
        }
    }
}
