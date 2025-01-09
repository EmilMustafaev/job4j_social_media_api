package ru.job4j.springbootstart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.springbootstart.model.Friendship;
import ru.job4j.springbootstart.model.Subscription;
import ru.job4j.springbootstart.repository.FriendshipRepository;
import ru.job4j.springbootstart.repository.SubscriptionRepository;

import java.time.LocalDateTime;

@Service
public class SubscribeService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        friendshipRepository.deleteByUsers(userId, friendId);

        subscriptionRepository.deleteBySubscriberIdAndPostId(userId, friendId);
        subscriptionRepository.deleteBySubscriberIdAndPostId(friendId, userId);
    }

    @Transactional
    public void handleFriendRequest(Long senderId, Long receiverId, boolean accepted) {
        Friendship friendship = friendshipRepository.findByRequesterIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new RuntimeException("Friendship request not found"));

        if (accepted) {
            friendship.setStatus("ACCEPTED");
            friendshipRepository.save(friendship);

            if (!subscriptionRepository.existsBySubscriberIdAndPostId(senderId, receiverId)) {
                subscriptionRepository.save(new Subscription(senderId, receiverId, LocalDateTime.now()));
            }
            if (!subscriptionRepository.existsBySubscriberIdAndPostId(receiverId, senderId)) {
                subscriptionRepository.save(new Subscription(receiverId, senderId, LocalDateTime.now()));
            }
        } else {
            friendshipRepository.delete(friendship);

            if (!subscriptionRepository.existsBySubscriberIdAndPostId(senderId, receiverId)) {
                subscriptionRepository.save(new Subscription(senderId, receiverId, LocalDateTime.now()));
            }
        }
    }

    @Transactional
    public void ensureFriendshipSubscribers(Long userId, Long friendId) {
        if (!subscriptionRepository.existsBySubscriberIdAndPostId(userId, friendId)) {
            subscriptionRepository.save(new Subscription(userId, friendId, LocalDateTime.now()));
        }
        if (!subscriptionRepository.existsBySubscriberIdAndPostId(friendId, userId)) {
            subscriptionRepository.save(new Subscription(friendId, userId, LocalDateTime.now()));
        }
    }
}

