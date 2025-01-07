package ru.job4j.springbootstart.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.springbootstart.model.Message;
import ru.job4j.springbootstart.model.User;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveMessageThenFindById() {
        var user1 = new User(null, "name1", "test1@mail.ru", "111", LocalDateTime.now());
        var user2 = new User(null, "name2", "test2@mail.ru", "222", LocalDateTime.now());
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        var message = new Message();
        message.setSender(user1);
        message.setReceiver(user2);
        message.setSentAt(LocalDateTime.now());
        message.setContent("TEST MESSAGE");
        messageRepository.save(message);
        var foundMessage = messageRepository.findById(message.getId());

        assertThat(foundMessage).isPresent();
        assertThat(foundMessage.get().getContent()).isEqualTo("TEST MESSAGE");
    }

    @Test
    public void whenFindAllThenReturnAllMessages() {
        var user1 = new User(null, "name1", "test1@mail.ru", "111", LocalDateTime.now());
        var user2 = new User(null, "name2", "test2@mail.ru", "222", LocalDateTime.now());

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        var message1 = new Message();
        message1.setSender(user1);
        message1.setReceiver(user2);
        message1.setSentAt(LocalDateTime.now());
        message1.setContent("MESSAGE1");


        var message2 = new Message();
        message2.setSender(user2);
        message2.setReceiver(user1);
        message2.setSentAt(LocalDateTime.now());
        message2.setContent("MESSAGE2");

        messageRepository.save(message1);
        messageRepository.save(message2);

        var messages = messageRepository.findAll();
        assertThat(messages).hasSize(2);
        assertThat(messages).extracting(Message::getContent).contains("MESSAGE1", "MESSAGE2");
    }

}