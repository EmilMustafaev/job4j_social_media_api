package ru.job4j.springbootstart.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.job4j.springbootstart.model.Friendship;
import ru.job4j.springbootstart.model.User;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FriendshipRepositoryTest {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        friendshipRepository.deleteAll();
        userRepository.deleteAll();
        jdbcTemplate.execute("ALTER TABLE friendships ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE users ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    public void whenSaveFriendshipThenFindById() {
        var user1 = new User(null, "name1", "test1@mail.ru", "111", LocalDateTime.now());
        var user2 = new User(null, "name2", "test2@mail.ru", "222", LocalDateTime.now());
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        var friendship = new Friendship();
        friendship.setRequester(user1);
        friendship.setReceiver(user2);
        friendship.setCreatedAt(LocalDateTime.now());
        friendship.setStatus("ACCEPTED");
        friendshipRepository.save(friendship);
        var foundFriendship = friendshipRepository.findById(friendship.getId());

        assertThat(foundFriendship).isPresent();
        assertThat(foundFriendship.get().getId()).isEqualTo(1L);
    }

    @Test
    public void whenFindAllThenReturnAllFriendships() {
        var user1 = new User(null, "name1", "test1@mail.ru", "111", LocalDateTime.now());
        var user2 = new User(null, "name2", "test2@mail.ru", "222", LocalDateTime.now());

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        var friendship1 = new Friendship();
        friendship1.setRequester(user1);
        friendship1.setReceiver(user2);
        friendship1.setCreatedAt(LocalDateTime.now());
        friendship1.setStatus("ACCEPTED");


        var friendship2 = new Friendship();
        friendship2.setRequester(user2);
        friendship2.setReceiver(user1);
        friendship2.setCreatedAt(LocalDateTime.now());
        friendship2.setStatus("DECLINED");

        friendshipRepository.save(friendship1);
        friendshipRepository.save(friendship2);

        var friendships = friendshipRepository.findAll();
        assertThat(friendships).hasSize(2);
        assertThat(friendships).extracting(Friendship::getStatus).contains("ACCEPTED", "DECLINED");
    }

}