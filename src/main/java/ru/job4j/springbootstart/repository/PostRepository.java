package ru.job4j.springbootstart.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.springbootstart.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
}
