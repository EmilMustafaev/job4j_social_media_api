package ru.job4j.springbootstart.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.springbootstart.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
