package ru.job4j.springbootstart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.springbootstart.model.User;
import ru.job4j.springbootstart.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Optional<User> createUser(User user) {
        return Optional.ofNullable(userRepository.save(user));
    }

    @Transactional
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    public boolean updateUser(Long userId, User updatedUser) {
        boolean result = false;
        var optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPasswordHash(updatedUser.getPasswordHash());
            userRepository.save(user);
            result = true;
        }
        return result;
    }

    @Transactional
    public boolean deleteUserById(Long userId) {
        boolean result = false;
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            result = true;
        }
        return result;
    }
}
