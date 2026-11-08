package ru.sqltrainer.sql_trainer.exam;

public record ExamOption(String id, String text, String imageUrl) {

    public ExamOption(String id, String text) {
        this(id, text, null);
    }

    public boolean hasImage() {
        return imageUrl != null && !imageUrl.isBlank();
    }
}
