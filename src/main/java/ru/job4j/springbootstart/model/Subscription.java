package ru.job4j.springbootstart.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;

@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    private User subscriber;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Subscription(Long subscriberId, Long postId, LocalDateTime createdAt) {
        this.subscriber = new User();
        this.subscriber.setId(subscriberId);
        this.post = new Post();
        this.post.setId(postId);
        this.createdAt = createdAt;
    }

}
