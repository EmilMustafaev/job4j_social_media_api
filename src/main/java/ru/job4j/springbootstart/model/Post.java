package ru.job4j.springbootstart.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Информация о публикации")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор публикации", example = "101")
    private Long id;

    @NotBlank(message = "Заголовок поста не может быть пустым")
    @Size(max = 100, message = "Заголовок не может быть длиннее 100 символов")
    @Schema(description = "Заголовок публикации", example = "Мой первый пост")
    private String title;

    @NotBlank(message = "Содержимое поста не может быть пустым")
    @Schema(description = "Содержимое публикации", example = "Это мой первый пост в социальной сети.")
    private String content;

    @Column(name = "image_url")
    @Schema(description = "URL изображения", example = "https://example.com/image.jpg")
    private String imageUrl;

    @NotNull(message = "Пользователь обязателен")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "Пользователь, который создал пост")
    private User user;

    @Column(name = "created_at")
    @Schema(description = "Дата создания публикации", example = "2025-01-01T10:00:00")
    private LocalDateTime createdAt;

}
