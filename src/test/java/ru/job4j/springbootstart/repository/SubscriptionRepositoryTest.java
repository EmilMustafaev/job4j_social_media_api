package ru.job4j.springbootstart.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.springbootstart.model.Post;
import ru.job4j.springbootstart.model.Subscription;
import ru.job4j.springbootstart.model.User;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class SubscriptionRepositoryTest {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        subscriptionRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
        jdbcTemplate.execute("ALTER TABLE subscriptions ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    public void whenSaveSubscriptionThenFindById() {
        var user = new User(null, "name", "test@mail.ru", "111", LocalDateTime.now());
        user = userRepository.save(user);
        var post = new Post(null, "title", "content", "imagetest.url", user, LocalDateTime.now());
        post = postRepository.save(post);

        var subscription = new Subscription();
        subscription.setSubscriber(user);
        subscription.setPost(post);
        subscription.setCreatedAt(LocalDateTime.now());
        subscriptionRepository.save(subscription);
        var foundSubscription = subscriptionRepository.findById(subscription.getId());

        assertThat(foundSubscription).isPresent();
        assertThat(foundSubscription.get().getId()).isEqualTo(1L);
    }

    @Test
    public void whenFindAllThenReturnAllPosts() {
        var user1 = new User(null, "name1", "test1@mail.ru", "111", LocalDateTime.now());
        var user2 = new User(null, "name2", "test2@mail.ru", "222", LocalDateTime.now());
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        var post1 = new Post(null, "title1", "content1", "imagetest.url1", user1, LocalDateTime.now());
        var post2 = new Post(null, "title2", "content2", "imagetest.url2", user2, LocalDateTime.now());
        post1 = postRepository.save(post1);
        post2 = postRepository.save(post2);

        var subscription1 = new Subscription();
        subscription1.setSubscriber(user1);
        subscription1.setPost(post1);
        subscription1.setCreatedAt(LocalDateTime.now());


        var subscription2 = new Subscription();
        subscription2.setSubscriber(user2);
        subscription2.setPost(post2);
        subscription2.setCreatedAt(LocalDateTime.now());

        subscriptionRepository.save(subscription1);
        subscriptionRepository.save(subscription2);

        var subscriptions = subscriptionRepository.findAll();
        assertThat(subscriptions).hasSize(2);
        assertThat(subscriptions).extracting(Subscription::getId).contains(1L, 2L);
    }

}