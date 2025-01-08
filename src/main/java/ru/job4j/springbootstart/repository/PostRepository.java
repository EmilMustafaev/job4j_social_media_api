package ru.job4j.springbootstart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.springbootstart.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByUserId(Long userId);

    List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
