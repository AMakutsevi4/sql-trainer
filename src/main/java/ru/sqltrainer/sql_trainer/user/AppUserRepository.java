package ru.sqltrainer.sql_trainer.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByLoginIgnoreCase(String login);

    boolean existsByLoginIgnoreCase(String login);
}
