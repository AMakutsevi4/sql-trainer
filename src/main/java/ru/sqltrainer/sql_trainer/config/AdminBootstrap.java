package ru.sqltrainer.sql_trainer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.sqltrainer.sql_trainer.user.UserService;

@Component
public class AdminBootstrap implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);

    private final UserService users;
    private final AdminProperties admin;

    public AdminBootstrap(UserService users, AdminProperties admin) {
        this.users = users;
        this.admin = admin;
    }

    @Override
    public void run(ApplicationArguments args) {
        users.ensureAdmin(admin.getLogin(), admin.getPassword());
        log.info("Админ готов: login={}", admin.getLogin());
    }
}
