package ru.job4j.springbootstart.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.springbootstart.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("""
            SELECT u FROM User u
            WHERE u.username = :username AND u.passwordHash = :password
            """)
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT s.subscriber FROM Subscription s WHERE s.post.user.id = :userId")
    List<User> findSubscribersByUserId(@Param("userId") Long userId);

    @Query("""
       SELECT f.requester 
       FROM Friendship f 
       WHERE f.receiver.id = :userId AND f.status = 'ACCEPTED'
       UNION
       SELECT f.receiver 
       FROM Friendship f 
       WHERE f.requester.id = :userId AND f.status = 'ACCEPTED'
       """)
    List<User> findFriendsByUserId(@Param("userId") Long userId);
}
