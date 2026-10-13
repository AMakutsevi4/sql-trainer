package ru.sqltrainer.sql_trainer.ui;

public record SqlTask(
        String id,
        String section,
        String title,
        String description,
        String schemaSql,
        String dataSql,
        String hintQuery,
        String expectedCsv
) {
}
