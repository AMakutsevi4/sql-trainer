package ru.sqltrainer.sql_trainer.progress;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sqltrainer.sql_trainer.security.SecurityUtils;
import ru.sqltrainer.sql_trainer.user.AppUserRepository;

@Component
public class DbAttemptRecorder implements AttemptRecorder {

    private final AppUserRepository users;
    private final SubmissionRepository submissions;

    public DbAttemptRecorder(AppUserRepository users, SubmissionRepository submissions) {
        this.users = users;
        this.submissions = submissions;
    }

    @Override
    @Transactional
    public void record(String taskId, String studentSql, boolean passed) {
        String login = SecurityUtils.currentLogin();
        if (login == null) {
            return;
        }
        users.findByLoginIgnoreCase(login).ifPresent(user ->
                submissions.save(new Submission(user, taskId, studentSql, passed))
        );
    }
}
