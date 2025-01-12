package ru.job4j.springbootstart.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class PostController {

    private final PostService postService;

    private final UserService userService;


    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
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

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.findPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody Post post) {
        if (postService.updatePost(id, post)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (postService.deletePost(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

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

