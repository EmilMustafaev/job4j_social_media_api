package ru.job4j.springbootstart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.springbootstart.model.Post;
import ru.job4j.springbootstart.model.User;
import ru.job4j.springbootstart.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Optional<Post> createPost(Post post) {
        return Optional.ofNullable(postRepository.save(post));
    }

    @Transactional
    public boolean updatePost(Long postId, Post updatedPost) {
        boolean result = false;
        var optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            var post = optionalPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setImageUrl(updatedPost.getImageUrl());
            postRepository.save(post);
            result = true;
        }
        return result;
    }

    @Transactional
    public boolean deletePost(Long postId) {
        boolean result = false;
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            result = true;
        }
        return result;
    }

    @Transactional
    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    @Transactional
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }
}

