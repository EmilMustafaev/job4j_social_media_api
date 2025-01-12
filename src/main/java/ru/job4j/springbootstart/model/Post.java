package ru.job4j.springbootstart.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Заголовок поста не может быть пустым")
    @Size(max = 100, message = "Заголовок не может быть длиннее 100 символов")
    private String title;

    @NotBlank(message = "Содержимое поста не может быть пустым")
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull(message = "Пользователь обязателен")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
