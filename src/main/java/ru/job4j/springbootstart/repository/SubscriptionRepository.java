package ru.job4j.springbootstart.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.springbootstart.model.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    boolean existsBySubscriberIdAndPostId(Long subscriberId, Long postId);

    @Modifying
    @Query("DELETE FROM Subscription s WHERE s.subscriber.id = :subscriberId AND s.post.id = :postId")
    void deleteBySubscriberIdAndPostId(@Param("subscriberId") Long subscriberId, @Param("postId") Long postId);
}
