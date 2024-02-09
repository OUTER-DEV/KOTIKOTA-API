package com.hackathon.tohanoapimvn.service;



import com.hackathon.tohanoapimvn.model.User;
import com.hackathon.tohanoapimvn.model.exception.UserNotFoundException;
import com.hackathon.tohanoapimvn.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public User validateAndGetUserByUsername(String username) {
        return getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
