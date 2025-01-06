package ru.job4j.springbootstart.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.springbootstart.model.Friendship;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
}
