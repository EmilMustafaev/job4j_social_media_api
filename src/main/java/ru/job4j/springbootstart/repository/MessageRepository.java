package ru.job4j.springbootstart.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.springbootstart.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
