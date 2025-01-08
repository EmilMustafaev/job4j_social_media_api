package ru.job4j.springbootstart.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.springbootstart.model.Post;
import ru.job4j.springbootstart.model.User;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSavePostThenFindById() {
        var user = new User(null, "name", "test@mail.ru", "111", LocalDateTime.now());
        user = userRepository.save(user);

        var post = new Post();
        post.setTitle("Test Post");
        post.setContent("Content Post");
        post.setImageUrl("imagetest.url");
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        var foundPost = postRepository.findById(post.getId());

        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getTitle()).isEqualTo("Test Post");
        assertThat(foundPost.get().getContent()).isEqualTo("Content Post");
    }

    @Test
    public void whenFindAllThenReturnAllPosts() {
        var user1 = new User(null, "name1", "test1@mail.ru", "111", LocalDateTime.now());
        var user2 = new User(null, "name2", "test2@mail.ru", "222", LocalDateTime.now());

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        var post1 = new Post();
        post1.setTitle("Test Post1");
        post1.setContent("Content Post1");
        post1.setImageUrl("imagetest.url1");
        post1.setUser(user1);
        post1.setCreatedAt(LocalDateTime.now());


        var post2 = new Post();
        post2.setTitle("Test Post2");
        post2.setContent("Content Post2");
        post2.setImageUrl("imagetest.url2");
        post2.setUser(user2);
        post2.setCreatedAt(LocalDateTime.now());

        postRepository.save(post1);
        postRepository.save(post2);

        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getContent).contains("Content Post1", "Content Post2");
    }
}