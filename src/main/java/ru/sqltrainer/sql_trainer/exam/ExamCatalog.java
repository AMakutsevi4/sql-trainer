package ru.sqltrainer.sql_trainer.exam;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ExamCatalog {

    public static final String SECTION = "5. Экзамен";
    public static final int DEFAULT_TIME_LIMIT_MINUTES = 20;
    public static final int DEFAULT_RETAKE_COOLDOWN_HOURS = 24;

    private final List<ExamDefinition> exams;

    public ExamCatalog() {
        this.exams = List.of(examCreateUpdateInsert(), examSelectData());
    }

    public List<ExamDefinition> exams() {
        return exams;
    }

    public Optional<ExamDefinition> findExam(String examId) {
        return exams.stream().filter(e -> e.id().equals(examId)).findFirst();
    }

    public List<ExamQuestion> allQuestions() {
        return exams.stream().flatMap(e -> e.questions().stream()).toList();
    }

    public Optional<ExamQuestion> findQuestionById(String id) {
        return allQuestions().stream().filter(q -> q.id().equals(id)).findFirst();
    }

    private static ExamDefinition examCreateUpdateInsert() {
        String section = SECTION + " · CREATE / UPDATE / INSERT";
        List<ExamQuestion> questions = List.of(
                q("exam1", section, 1, "CREATE TABLE — какое множество?",
                        "К какому множеству операторов относится операция CREATE TABLE?\nВыберите 1 правильный вариант.",
                        false,
                        opts("DML", "DCL", "DDL", "TCL"),
                        Set.of("c")),
                q("exam1", section, 2, "Операторы DML",
                        "Какие операторы содержит группа операторов DML?\nВыберите несколько верных ответов.",
                        true,
                        opts("CREATE TABLE", "INSERT", "ALTER TABLE", "UPDATE", "DELETE"),
                        Set.of("b", "d", "e")),
                q("exam1", section, 3, "Автоинкремент идентификатора",
                        "Какой тип данных указать для столбца-идентификатора, чтобы значения были числовыми и автоматически увеличивались на 1?\nВыберите 1 верный вариант.",
                        false,
                        opts("PRIMARY KEY", "SERIAL", "INTEGER", "NUMERIC"),
                        Set.of("b")),
                q("exam1", section, 4, "UPDATE несуществующей колонки",
                        """
                        Дана таблица t1:

                        ID | NAME
                        1  | Лев
                        3  | Геннадий
                        10 | Василий

                        Что будет в столбце name у строки с ID = 3 после запроса?

                        UPDATE t1 SET col2 = 'Пётр';

                        Выберите 1 верный вариант.""",
                        false,
                        opts("Лев", "Геннадий", "Василий", "Пётр"),
                        Set.of("b")),
                q("exam1", section, 5, "Какие операторы — DML?",
                        "Какие из перечисленных операторов относятся к множеству DML?\nВыберите один или несколько верных ответов.",
                        true,
                        opts("SELECT", "INSERT", "UPDATE", "DELETE"),
                        Set.of("a", "b", "c", "d")),
                q("exam1", section, 6, "Счёт и несколько карт",
                        "К какому виду отношений относится утверждение: к банковскому счёту привязано более одной карты?\nВыберите верный ответ.",
                        false,
                        opts("many-to-one", "one-to-many", "many-to-many", "one-to-one"),
                        Set.of("b")),
                q("exam1", section, 7, "Ограничения для one-to-one",
                        "С помощью каких ограничений можно организовать связь one-to-one?\nВыберите несколько верных ответов.",
                        true,
                        opts("PRIMARY KEY", "REFERENCES", "FOREIGN KEY", "CHECK", "UNIQUE"),
                        Set.of("b", "c", "e")),
                q("exam1", section, 8, "Когда нужна связь-таблица",
                        "Для каких отношений между двумя сущностями необходимо создавать вспомогательную сущность (всегда либо при определённых условиях)?\nВыберите верный ответ.",
                        false,
                        opts("many-to-many", "many-to-one", "one-to-many", "one-to-one"),
                        Set.of("a")),
                q("exam1", section, 9, "PRIMARY KEY и UNIQUE",
                        "В чём отличие ограничения PRIMARY KEY от UNIQUE?\nВыберите верный ответ.",
                        false,
                        opts(
                                "Отличий нет",
                                "PRIMARY KEY позволяет NULL, а UNIQUE — нет",
                                "PRIMARY KEY не позволяет NULL, а UNIQUE позволяет"
                        ),
                        Set.of("c"))
        );
        return new ExamDefinition(
                "exam1",
                "SQL. CREATE, UPDATE, INSERT",
                "В этом экзамене вы можете проверить свои знания SQL по CREATE, UPDATE, INSERT.",
                List.of("Типы данных", "Связи между таблицами", "Ограничения", "DDL / DML"),
                DEFAULT_TIME_LIMIT_MINUTES,
                DEFAULT_RETAKE_COOLDOWN_HOURS,
                "exam-attempt", // старый id попыток первого экзамена
                questions
        );
    }

    private static ExamDefinition examSelectData() {
        String section = SECTION + " · Выборка данных";
        List<ExamQuestion> questions = List.of(
                q("exam2", section, 1, "NULL = NULL",
                        "Какой результат выдаст следующий запрос?\n\nSELECT NULL = NULL;\n\nВыберите верный ответ.",
                        false,
                        opts("NULL", "true", "false"),
                        Set.of("a")),
                q("exam2", section, 2, "NULL != NULL",
                        "Какой результат выдаст следующий запрос?\n\nSELECT NULL != NULL;\n\nВыберите верный ответ.",
                        false,
                        opts("NULL", "true", "false"),
                        Set.of("a")),
                q("exam2", section, 3, "Фильтр monitor/tablet и цены",
                        """
                        Дана таблица PRODUCT:

                        MAKER   | TYPE     | PRICE
                        HP      | monitor  | 126
                        HP      | PC       | 202
                        Huawei  | tablet   | 159
                        Samsung | notebook | 199
                        Samsung | tablet   | 130
                        Samsung | monitor  | 99

                        Нужно выбрать компании, производящие мониторы и планшеты, с ценами в диапазонах 125–135 или 300–400.

                        Какой запрос даст верный результат?""",
                        false,
                        opts(
                                """
                                SELECT maker, type, price FROM product
                                WHERE ("type" = 'monitor' OR "type" = 'tablet' AND price BETWEEN 125 AND 135)
                                   OR (price > 300 AND price < 400);""",
                                """
                                SELECT maker, type, price FROM product
                                WHERE ("type" = 'monitor' OR "type" = 'tablet')
                                  AND (price BETWEEN 125 AND 135 OR (price > 300 AND price < 400));""",
                                """
                                SELECT maker, type, price FROM product
                                WHERE "type" = 'monitor' OR "type" = 'tablet' AND price BETWEEN 125 AND 135
                                   OR price > 300 AND price < 400;"""
                        ),
                        Set.of("b")),
                q("exam2", section, 4, "Шаблоны LIKE",
                        "Выберите символы, которые может содержать шаблон сравнения для оператора LIKE:\nВыберите верные ответы.",
                        true,
                        opts("%", "*", "_", "$"),
                        Set.of("a", "c")),
                q("exam2", section, 5, "Убрать дубликаты",
                        """
                        Дана таблица:

                        TYPE    | PRICE
                        tablet  | 70
                        monitor | 369
                        monitor | 124
                        tablet  | 70
                        monitor | 124
                        monitor | 124
                        monitor | 369

                        С помощью каких операторов можно убрать дубликаты в запросе?
                        Выберите верные ответы.""",
                        true,
                        opts("LIMIT", "DISTINCT", "UNION ALL", "GROUP BY"),
                        Set.of("b", "d")),
                q("exam2", section, 6, "COUNT(*) и COUNT(field)",
                        "В чём отличие агрегатных функций COUNT(*) и COUNT(field_of_table)?\nВыберите верный ответ.",
                        false,
                        opts(
                                "Обе функции работают абсолютно одинаково",
                                "COUNT(*) не учитывает NULL, а COUNT(field_of_table) учитывает",
                                "COUNT(*) учитывает NULL, а COUNT(field_of_table) не учитывает"
                        ),
                        Set.of("c")),
                q("exam2", section, 7, "Псевдонимы таблиц",
                        "В каких случаях необходимо использовать псевдонимы для таблиц?\nВыберите верные ответы.",
                        true,
                        opts(
                                "Для переименования таблиц",
                                "Для устранения неоднозначности при выборке одноимённых полей из разных таблиц"
                        ),
                        Set.of("a", "b")),
                q("exam2", section, 8, "Что писать в GROUP BY",
                        "Какие столбцы необходимо записывать в предложении GROUP BY?\nВыберите верный ответ.",
                        false,
                        opts(
                                "Все",
                                "К которым применены агрегатные функции",
                                "К которым не применены агрегатные функции"
                        ),
                        Set.of("c")),
                q("exam2", section, 9, "Максимум среднего",
                        "Необходимо выбрать максимальное среднее значение. Какой запрос будет верным?\nВыберите верный ответ.",
                        false,
                        opts(
                                "SELECT max(avg(price)) FROM product GROUP BY \"type\";",
                                "SELECT max(avg_price) FROM (SELECT avg(price) AS avg_price FROM product GROUP BY \"type\") AS t;",
                                "Оба запроса неверные",
                                "Оба запроса верные"
                        ),
                        Set.of("b")),
                q("exam2", section, 10, "INNER JOIN — диаграмма",
                        "Какая диаграмма Эйлера–Венна подходит для соединения INNER JOIN?\nВыберите верный ответ.",
                        false,
                        imageOpts(
                                "/images/exam/join-left.png",
                                "/images/exam/join-full.png",
                                "/images/exam/join-inner.png",
                                "/images/exam/join-left-anti.png",
                                "/images/exam/join-full-anti.png"
                        ),
                        Set.of("c")),
                q("exam2", section, 11, "INNER JOIN и WHERE",
                        """
                        Отличаются ли результаты следующих выборок?

                        Запрос 1:
                        SELECT t1.col1, t2.col2 FROM t1 INNER JOIN t2 ON t1.id = t2.t1_id;

                        Запрос 2:
                        SELECT t1.col1, t2.col2 FROM t1, t2 WHERE t1.id = t2.t1_id;

                        Выберите верный вариант.""",
                        false,
                        opts("Да", "Нет"),
                        Set.of("b"))
        );
        return new ExamDefinition(
                "exam2",
                "SQL. Выборка данных",
                "В этом экзамене вы можете проверить свои знания SQL по выборке данных.",
                List.of("Выборка данных из БД", "Операторы группировки", "Операторы выборки"),
                DEFAULT_TIME_LIMIT_MINUTES,
                DEFAULT_RETAKE_COOLDOWN_HOURS,
                "exam2-attempt",
                questions
        );
    }

    private static ExamQuestion q(
            String examId,
            String section,
            int order,
            String title,
            String prompt,
            boolean multiSelect,
            List<ExamOption> options,
            Set<String> correct
    ) {
        // exam1 сохраняет старые id exam-01.. для совместимости с уже записанными попытками
        String id = "exam1".equals(examId)
                ? "exam-" + String.format("%02d", order)
                : examId + "-" + String.format("%02d", order);
        return new ExamQuestion(id, section, order, title, prompt, multiSelect, options, correct);
    }

    private static List<ExamOption> opts(String... texts) {
        List<ExamOption> list = new ArrayList<>(texts.length);
        for (int i = 0; i < texts.length; i++) {
            list.add(new ExamOption(String.valueOf((char) ('a' + i)), texts[i]));
        }
        return List.copyOf(list);
    }

    private static List<ExamOption> imageOpts(String... imageUrls) {
        List<ExamOption> list = new ArrayList<>(imageUrls.length);
        for (int i = 0; i < imageUrls.length; i++) {
            char letter = (char) ('a' + i);
            list.add(new ExamOption(
                    String.valueOf(letter),
                    "Вариант " + Character.toUpperCase(letter),
                    imageUrls[i]
            ));
        }
        return List.copyOf(list);
    }
}
