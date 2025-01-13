package ru.job4j.springbootstart.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
@Schema(description = "Информация о пользователе")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    @NotBlank(message = "Username не может быть пустым")
    @Size(min = 3, max = 20, message = "Username должен быть длиной от 3 до 20 символов")
    @Schema(description = "Имя пользователя", example = "Jon Doe")
    private String username;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный формат email")
    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    @NotBlank(message = "Пароль обязателен")
    @Column(name = "password_hash")
    @Schema(description = "Хэш пароля пользователя", example = "hashedpassword123")
    private String passwordHash;

    @Column(name = "created_at")
    @Schema(description = "Дата создания пользователя", example = "2025-01-01T10:00:00")
    private LocalDateTime createdAt;
}
