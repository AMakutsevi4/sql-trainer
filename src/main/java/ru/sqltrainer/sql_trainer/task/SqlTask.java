package ru.sqltrainer.sql_trainer.task;

public abstract class SqlTask {

    public abstract String id();

    public abstract String section();

    public abstract int order();

    public abstract String title();

    public abstract String description();

    public abstract String schema();

    public abstract String data();

    public abstract String starter();

    public abstract String expected();

    public abstract String solution();
}
