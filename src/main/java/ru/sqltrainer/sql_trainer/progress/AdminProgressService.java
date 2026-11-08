package ru.sqltrainer.sql_trainer.progress;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sqltrainer.sql_trainer.exam.ExamCatalog;
import ru.sqltrainer.sql_trainer.exam.ExamQuestion;
import ru.sqltrainer.sql_trainer.task.SqlTask;
import ru.sqltrainer.sql_trainer.task.TaskCatalog;
import ru.sqltrainer.sql_trainer.user.AppUser;
import ru.sqltrainer.sql_trainer.user.AppUserRepository;
import ru.sqltrainer.sql_trainer.user.Role;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminProgressService {

    private final AppUserRepository users;
    private final SubmissionRepository submissions;
    private final TaskCatalog taskCatalog;
    private final ExamCatalog examCatalog;

    public AdminProgressService(
            AppUserRepository users,
            SubmissionRepository submissions,
            TaskCatalog taskCatalog,
            ExamCatalog examCatalog
    ) {
        this.users = users;
        this.submissions = submissions;
        this.taskCatalog = taskCatalog;
        this.examCatalog = examCatalog;
    }

    public int totalTasks() {
        return trackables().size();
    }

    @Transactional(readOnly = true)
    public List<StudentSummary> studentSummaries() {
        Map<Long, Long> attempts = new HashMap<>();
        Map<Long, Instant> lastActivity = new HashMap<>();
        for (Object[] row : submissions.attemptAggregatesByUser()) {
            Long userId = (Long) row[0];
            attempts.put(userId, (Long) row[1]);
            lastActivity.put(userId, (Instant) row[2]);
        }

        Map<Long, Long> solved = new HashMap<>();
        for (Object[] row : submissions.solvedAggregatesByUser()) {
            solved.put((Long) row[0], (Long) row[1]);
        }

        List<StudentSummary> result = new ArrayList<>();
        for (AppUser user : users.findByRoleOrderByLoginAsc(Role.STUDENT)) {
            result.add(new StudentSummary(
                    user.getId(),
                    user.getLogin(),
                    solved.getOrDefault(user.getId(), 0L),
                    attempts.getOrDefault(user.getId(), 0L),
                    lastActivity.get(user.getId())
            ));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Optional<StudentDetail> studentDetail(Long userId) {
        return users.findById(userId)
                .filter(user -> user.getRole() == Role.STUDENT)
                .map(this::buildDetail);
    }

    private StudentDetail buildDetail(AppUser user) {
        List<Submission> all = submissions.findByUserIdOrderByCreatedAtDesc(user.getId());

        Map<String, Integer> attemptsByTask = new HashMap<>();
        Map<String, Instant> lastAttemptByTask = new HashMap<>();
        Map<String, String> lastSqlByTask = new HashMap<>();
        Set<String> solvedIds = new HashSet<>();

        for (Submission s : all) {
            attemptsByTask.merge(s.getTaskId(), 1, Integer::sum);
            lastAttemptByTask.putIfAbsent(s.getTaskId(), s.getCreatedAt());
            lastSqlByTask.putIfAbsent(s.getTaskId(), s.getSqlText());
            if (s.isPassed()) {
                solvedIds.add(s.getTaskId());
            }
        }

        List<Trackable> items = trackables();
        List<TaskProgress> tasks = new ArrayList<>();
        Map<String, int[]> sectionCounts = new HashMap<>();

        for (Trackable item : items) {
            int[] counts = sectionCounts.computeIfAbsent(item.section(), key -> new int[2]);
            counts[1]++;
            boolean solved = solvedIds.contains(item.id());
            if (solved) {
                counts[0]++;
            }
            tasks.add(new TaskProgress(
                    item.id(),
                    item.section(),
                    item.order(),
                    item.title(),
                    solved,
                    attemptsByTask.getOrDefault(item.id(), 0),
                    lastAttemptByTask.get(item.id()),
                    lastSqlByTask.get(item.id())
            ));
        }

        List<String> sectionOrder = items.stream().map(Trackable::section).distinct().toList();
        List<SectionProgress> sectionList = sectionOrder.stream()
                .map(section -> {
                    int[] counts = sectionCounts.getOrDefault(section, new int[2]);
                    return new SectionProgress(section, counts[0], counts[1]);
                })
                .toList();

        Instant lastActivity = all.isEmpty() ? null : all.getFirst().getCreatedAt();
        long solvedKnown = items.stream().map(Trackable::id).filter(solvedIds::contains).count();

        return new StudentDetail(
                user.getId(),
                user.getLogin(),
                (int) solvedKnown,
                all.size(),
                lastActivity,
                sectionList,
                tasks
        );
    }

    private List<Trackable> trackables() {
        List<Trackable> items = new ArrayList<>();
        for (SqlTask task : taskCatalog.all()) {
            items.add(new Trackable(task.id(), task.section(), task.order(), task.title()));
        }
        for (ExamQuestion question : examCatalog.allQuestions()) {
            items.add(new Trackable(question.id(), question.section(), question.order(), question.title()));
        }
        items.sort(Comparator.comparing(Trackable::section).thenComparingInt(Trackable::order));
        return items;
    }

    private record Trackable(String id, String section, int order, String title) {
    }

    public record StudentSummary(
            Long userId,
            String login,
            long solved,
            long attempts,
            Instant lastActivity
    ) {
    }

    public record SectionProgress(String section, int solved, int total) {
    }

    public record StudentDetail(
            Long userId,
            String login,
            int solved,
            int attempts,
            Instant lastActivity,
            List<SectionProgress> sections,
            List<TaskProgress> tasks
    ) {
    }

    public record TaskProgress(
            String taskId,
            String section,
            int order,
            String title,
            boolean solved,
            int attempts,
            Instant lastAttempt,
            String lastSql
    ) {
    }
}
