package com.example.MyWorkoutBuddy.service;

import com.example.MyWorkoutBuddy.model.User;
import com.example.MyWorkoutBuddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        String userRole = "ROLE_USER";
        User user = new User(username, passwordEncoder.encode(password), userRole);
        return userRepository.save(user);
    }
}