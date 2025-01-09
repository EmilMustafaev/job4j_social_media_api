package ru.job4j.springbootstart.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.springbootstart.model.Friendship;
import java.util.Optional;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

    Optional<Friendship> findByRequesterIdAndReceiverId(Long requesterId, Long receiverId);

    @Modifying
    @Query("DELETE FROM Friendship f WHERE (f.requester.id = :userId AND f.receiver.id = :friendId) OR (f.requester.id = :friendId AND f.receiver.id = :userId)")
    void deleteByUsers(@Param("userId") Long userId, @Param("friendId") Long friendId);

}
