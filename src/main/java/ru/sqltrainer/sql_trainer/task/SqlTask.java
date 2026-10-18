package ru.sqltrainer.sql_trainer.task;

/** Шаблон задачи. */
public abstract class SqlTask {

    public abstract String id();

    public abstract String section();

    public abstract int order();

    public abstract String title();

    public abstract String description();

    public abstract String schema();

    public abstract String data();

    /** Что ученик видит в редакторе при открытии. */
    public abstract String starter();

    /** Правильный результат в CSV. */
    public abstract String expected();

    /** Правильный SQL. Для тестов и «показать ответ», не для проверки ученика. */
    public abstract String solution();
}
