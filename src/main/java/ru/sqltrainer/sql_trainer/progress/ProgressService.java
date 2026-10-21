package ru.sqltrainer.sql_trainer.progress;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sqltrainer.sql_trainer.security.SecurityUtils;
import ru.sqltrainer.sql_trainer.user.AppUser;
import ru.sqltrainer.sql_trainer.user.AppUserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class ProgressService {

    private final AppUserRepository users;
    private final SubmissionRepository submissions;

    public ProgressService(AppUserRepository users, SubmissionRepository submissions) {
        this.users = users;
        this.submissions = submissions;
    }

    @Transactional(readOnly = true)
    public boolean isSolved(String taskId) {
        return currentUser()
                .map(user -> submissions.existsByUserIdAndTaskIdAndPassedTrue(user.getId(), taskId))
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public int attempts(String taskId) {
        return currentUser()
                .map(user -> (int) submissions.countByUserIdAndTaskId(user.getId(), taskId))
                .orElse(0);
    }

    @Transactional(readOnly = true)
    public Optional<String> lastSql(String taskId) {
        return currentUser().flatMap(user ->
                submissions.findFirstByUserIdAndTaskIdOrderByCreatedAtDesc(user.getId(), taskId)
                        .map(Submission::getSqlText)
        );
    }

    @Transactional(readOnly = true)
    public long solvedCount(Collection<String> taskIds) {
        if (taskIds.isEmpty()) {
            return 0;
        }
        return currentUser()
                .map(user -> (long) submissions.findSolvedTaskIds(user.getId(), taskIds).size())
                .orElse(0L);
    }

    @Transactional(readOnly = true)
    public Set<String> solvedTaskIds(Collection<String> taskIds) {
        if (taskIds.isEmpty()) {
            return Set.of();
        }
        return currentUser()
                .map(user -> submissions.findSolvedTaskIds(user.getId(), taskIds))
                .orElse(Set.of());
    }

    private Optional<AppUser> currentUser() {
        String login = SecurityUtils.currentLogin();
        if (login == null) {
            return Optional.empty();
        }
        return users.findByLoginIgnoreCase(login);
    }
}
