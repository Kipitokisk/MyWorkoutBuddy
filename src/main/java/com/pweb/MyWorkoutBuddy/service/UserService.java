package com.pweb.MyWorkoutBuddy.service;

import com.pweb.MyWorkoutBuddy.dto.UserDTO;
import com.pweb.MyWorkoutBuddy.exception.ResourceAlreadyExistsException;
import com.pweb.MyWorkoutBuddy.model.Response;
import com.pweb.MyWorkoutBuddy.model.Role;
import com.pweb.MyWorkoutBuddy.model.User;
import com.pweb.MyWorkoutBuddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.pweb.MyWorkoutBuddy.util.ResponseUtil.buildSuccessResponse;

@Service
@RequiredArgsConstructor
@Log
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<Response> save(UserDTO userDTO) {
        log.info("Registration called with: " + userDTO);

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(Role.USER);

        User existingUser = userRepository.findUserByUsername(user.getUsername());
        if (existingUser != null) {
            throw new ResourceAlreadyExistsException("Username '" + user.getUsername() + "' is already taken");
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse("User registered successfully", Collections.singletonMap("username", user.getUsername())));
    }
}
