package ru.job4j.springbootstart.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.springbootstart.model.User;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveUserThenFindById() {
        var user = new User();
        user.setUsername("Emil");
        user.setEmail("emil@example.com");
        user.setPasswordHash("111");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        var foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("Emil");
    }

    @Test
    public void whenFindAllThenReturnAllUsers() {
        var user1 = new User();
        user1.setUsername("Emil");
        user1.setEmail("emil@example.com");
        user1.setPasswordHash("111");
        user1.setCreatedAt(LocalDateTime.now());
        var user2 = new User();
        user2.setUsername("Petr");
        user2.setEmail("petr@example.com");
        user2.setPasswordHash("222");
        user2.setCreatedAt(LocalDateTime.now());
        userRepository.save(user1);
        userRepository.save(user2);
        var users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getUsername).contains("Emil", "Petr");
    }
}