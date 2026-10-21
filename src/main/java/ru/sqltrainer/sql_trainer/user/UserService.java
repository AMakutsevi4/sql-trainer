package ru.sqltrainer.sql_trainer.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AppUser> findAll() {
        return users.findAll();
    }

    @Transactional
    public AppUser createStudent(String login, String rawPassword) {
        return createUser(login, rawPassword, Role.STUDENT);
    }

    @Transactional
    public AppUser createUser(String login, String rawPassword, Role role) {
        String normalized = normalizeLogin(login);
        if (normalized.isBlank()) {
            throw new IllegalArgumentException("Логин пустой");
        }
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Пароль пустой");
        }
        if (users.existsByLoginIgnoreCase(normalized)) {
            throw new IllegalArgumentException("Логин уже занят: " + normalized);
        }
        AppUser user = new AppUser(normalized, passwordEncoder.encode(rawPassword), role);
        return users.save(user);
    }

    @Transactional
    public void resetPassword(Long userId, String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Пароль пустой");
        }
        AppUser user = users.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
    }

    @Transactional
    public void ensureAdmin(String login, String rawPassword) {
        String normalized = normalizeLogin(login);
        if (users.existsByLoginIgnoreCase(normalized)) {
            return;
        }
        users.save(new AppUser(normalized, passwordEncoder.encode(rawPassword), Role.ADMIN));
    }

    private static String normalizeLogin(String login) {
        return login == null ? "" : login.trim().toLowerCase();
    }
}
