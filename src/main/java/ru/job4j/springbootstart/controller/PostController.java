package ru.job4j.springbootstart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.springbootstart.dto.UserDTO;
import ru.job4j.springbootstart.model.Post;
import ru.job4j.springbootstart.service.PostService;
import ru.job4j.springbootstart.service.UserService;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Validated
@Tag(name = "PostController", description = "API для управления публикациями")
public class PostController {

    private final PostService postService;

    private final UserService userService;


    @Operation(summary = "Создать новую публикацию", description = "Создает новую публикацию и возвращает её данные")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Публикация успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        postService.createPost(post);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(post);
    }

    @Operation(summary = "Получить публикацию по ID", description = "Возвращает данные публикации по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Публикация найдена"),
            @ApiResponse(responseCode = "404", description = "Публикация не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable @NotNull
                                            @Min(value = 1, message = "ID ресурса должен быть 1 и более") Long id) {
        return postService.findPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Обновить публикацию", description = "Обновляет данные публикации по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные публикации успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Публикация не найдена")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable @NotNull
                                           @Min(value = 1, message = "ID ресурса должен быть 1 и более") Long id,
                                           @Valid @RequestBody Post post) {
        if (postService.updatePost(id, post)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Удалить публикацию", description = "Удаляет публикацию по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Публикация успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Публикация не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable
                                           @NotNull
                                           @Min(value = 1, message = "ID ресурса должен быть 1 и более") Long id) {
        if (postService.deletePost(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Получить все публикации", description = "Возвращает список всех публикаций")
    @ApiResponse(responseCode = "200", description = "Список публикаций успешно получен")
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }


    @Operation(summary = "Получить публикации по ID пользователей", description = "Возвращает список публикаций для заданных ID пользователей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список публикаций успешно получен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
    })
    @PostMapping("/users/posts")
    public ResponseEntity<List<UserDTO>> getUserPosts(@RequestBody List<Long> userIds) {
        List<UserDTO> userDTOs = userIds.stream()
                .map(userId -> {
                    var user = userService.findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                    var posts = postService.getPostsByUserId(userId).stream()
                            .map(Post::getTitle)
                            .collect(Collectors.toList());

                    return new UserDTO(user.getId(), user.getUsername(), posts);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }
}

