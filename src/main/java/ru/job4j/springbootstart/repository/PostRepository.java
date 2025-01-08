package ru.job4j.springbootstart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.springbootstart.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByUserId(Long userId);

    List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Post p
            SET p.title = :title, p.content = :content
            WHERE p.id = :id
            """)
    int updatePostTitleAndContent(@Param("title") String title, @Param("content") String content, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Post p
            SET p.imageUrl = NULL
            WHERE p.id = :id
            """)
    int removePostImage(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("""
            DELETE FROM Post p
            WHERE p.id = :id
            """)
    int deletePostById(@Param("id") Long id);

    @Query("""
            SELECT p 
            FROM Post p 
            JOIN Subscription s ON s.post.id = p.id 
            WHERE s.subscriber.id = :userId
            ORDER BY p.createdAt DESC
            """)
    Page<Post> findPostsBySubscribers(@Param("userId") Long userId, Pageable pageable);

}
