package com.diac.oligos.authentication.repository;

import com.diac.oligos.authentication.config.DataConfig;
import com.diac.oligos.authentication.model.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {
        DataConfig.class,
        UserRepository.class
})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindAll() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = userRepository.save(
                User.builder()
                        .username(value)
                        .password(value.toCharArray())
                        .build()
        );
        assertThat(userRepository.findAll()).contains(user);
    }

    @Test
    public void whenFindById() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = userRepository.save(
                User.builder()
                        .username(value)
                        .password(value.toCharArray())
                        .build()
        );
        User userInDb = userRepository.findById(user.getId()).orElse(new User());
        assertThat(userInDb).isEqualTo(user);
    }

    @Test
    public void whenFindByUsernameAndPassword() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = userRepository.save(
                User.builder()
                        .username(value)
                        .password(value.toCharArray())
                        .build()
        );
        User userInDb = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())
                .orElse(new User());
        assertThat(userInDb).isEqualTo(user);
    }

    @Test
    public void whenAdd() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = userRepository.save(
                User.builder()
                        .username(value)
                        .password(value.toCharArray())
                        .build()
        );
        assertThat(value).isEqualTo(user.getUsername());
        assertThat(value.toCharArray()).isEqualTo(user.getPassword());
    }

    @Test
    public void whenAddWithNullFieldsThenThrowException() {
        assertThatThrownBy(
                () -> userRepository.save(User.builder()
                        .username(null)
                        .build()
                )
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenAddDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = User.builder()
                .username(value)
                .password(value.toCharArray())
                .build();
        User duplicateUser = User.builder()
                .username(value)
                .password(value.toCharArray())
                .build();
        userRepository.save(user);
        assertThatThrownBy(
                () -> userRepository.save(duplicateUser)
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenUpdate() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = userRepository.save(
                User.builder()
                        .username(value)
                        .password(value.toCharArray())
                        .build()
        );
        user.setUsername(user.getUsername() + "_updated");
        user.setPassword((String.valueOf(user.getPassword()) + "_updated").toCharArray());
        User updatedUser = userRepository.save(user);
        assertThat(updatedUser).isEqualTo(user);
        assertThat(updatedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void whenUpdateWithNullValuesThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = userRepository.save(
                User.builder()
                        .username(value)
                        .password(value.toCharArray())
                        .build()
        );
        user.setUsername(null);
        assertThatThrownBy(
                () -> {
                    userRepository.save(user);
                    userRepository.findAll();
                }
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void whenUpdateDuplicateThenThrowException() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = userRepository.save(
                User.builder()
                        .username(value)
                        .password(value.toCharArray())
                        .build()
        );
        User duplicateUser = userRepository.save(
                User.builder()
                        .username(value + "_another")
                        .password(
                                (String.valueOf(user.getPassword()) + "another").toCharArray()
                        )
                        .build()
        );
        duplicateUser.setUsername(user.getUsername());
        duplicateUser.setPassword(user.getPassword());
        assertThatThrownBy(
                () -> {
                    userRepository.save(duplicateUser);
                    userRepository.findAll();
                }
        ).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenDelete() {
        String value = String.valueOf(System.currentTimeMillis());
        User user = userRepository.save(
                User.builder()
                        .username(value)
                        .password(value.toCharArray())
                        .build()
        );
        userRepository.delete(user);
        assertThat(userRepository.findAll()).doesNotContain(user);
    }
}