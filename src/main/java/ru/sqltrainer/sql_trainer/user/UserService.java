package ru.sqltrainer.sql_trainer.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sqltrainer.sql_trainer.security.SecurityUtils;

import java.util.List;

@Service
public class UserService {

    private static final int MIN_PASSWORD_LENGTH = 6;

    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AppUser> findAll() {
        return users.findAll();
    }

    @Transactional(readOnly = true)
    public boolean mustChangePassword(String login) {
        if (login == null || login.isBlank()) {
            return false;
        }
        return users.findByLoginIgnoreCase(login)
                .map(AppUser::isMustChangePassword)
                .orElse(false);
    }

    @Transactional
    public AppUser createStudent(String login, String rawPassword) {
        return createUser(login, rawPassword, Role.STUDENT);
    }

    @Transactional
    public AppUser createUser(String login, String rawPassword, Role role) {
        String normalized = normalizeLogin(login);
        validatePassword(rawPassword);
        if (normalized.isBlank()) {
            throw new IllegalArgumentException("Логин пустой");
        }
        if (users.existsByLoginIgnoreCase(normalized)) {
            throw new IllegalArgumentException("Логин уже занят: " + normalized);
        }
        AppUser user = new AppUser(normalized, passwordEncoder.encode(rawPassword), role);
        user.setMustChangePassword(true);
        return users.save(user);
    }

    @Transactional
    public void resetPassword(Long userId, String rawPassword) {
        validatePassword(rawPassword);
        AppUser user = users.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setMustChangePassword(true);
    }

    @Transactional
    public void changeOwnPassword(String rawPassword, String confirmPassword) {
        String login = SecurityUtils.currentLogin();
        if (login == null) {
            throw new IllegalArgumentException("Нужно войти в систему");
        }
        if (rawPassword == null || confirmPassword == null || !rawPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }
        validatePassword(rawPassword);
        AppUser user = users.findByLoginIgnoreCase(login)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setMustChangePassword(false);
    }

    @Transactional
    public void ensureAdmin(String login, String rawPassword) {
        String normalized = normalizeLogin(login);
        if (users.existsByLoginIgnoreCase(normalized)) {
            return;
        }
        AppUser admin = new AppUser(normalized, passwordEncoder.encode(rawPassword), Role.ADMIN);
        admin.setMustChangePassword(false);
        users.save(admin);
    }

    private static void validatePassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Пароль пустой");
        }
        if (rawPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Пароль слишком короткий (минимум " + MIN_PASSWORD_LENGTH + " символов)");
        }
    }

    private static String normalizeLogin(String login) {
        return login == null ? "" : login.trim().toLowerCase();
    }
}
