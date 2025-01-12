package ru.job4j.springbootstart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;

    @NotBlank(message = "Username не может быть пустым")
    @Length(min = 3, max = 20, message = "Username должен быть от 3 до 20 символов")
    private String username;

    private List<String> posts;
}
