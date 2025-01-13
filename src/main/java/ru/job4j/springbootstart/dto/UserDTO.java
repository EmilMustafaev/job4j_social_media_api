package ru.job4j.springbootstart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object для пользователя, содержащий информацию о пользователе и его публикациях")
public class UserDTO {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long userId;

    @NotBlank(message = "Username не может быть пустым")
    @Length(min = 3, max = 20, message = "Username должен быть от 3 до 20 символов")
    @Schema(description = "Имя пользователя", example = "John Doe")
    private String username;

    @Schema(description = "Список публикаций пользователя", example = "[\"My first post\", \"Another day in paradise\"]")
    private List<String> posts;
}
