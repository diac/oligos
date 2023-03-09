package com.diac.oligos.authentication.repository;

import com.diac.oligos.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения объектов User (пользователь)
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Найти пользователя по имени пользователя и паролю
     *
     * @param username Имя пользователя
     * @return Optional с найденным пользователем. Пустой Optional, если ничего не найдено
     */
    Optional<User> findByUsername(String username);

    /**
     * Найти пользователя по имени пользователя и паролю
     *
     * @param username Имя пользователя
     * @param password Пароль
     * @return Optional с найденным пользователем. Пустой Optional, если ничего не найдено
     */
    Optional<User> findByUsernameAndPassword(String username, char[] password);
}