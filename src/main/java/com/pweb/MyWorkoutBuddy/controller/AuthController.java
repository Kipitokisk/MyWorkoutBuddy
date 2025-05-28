package com.pweb.MyWorkoutBuddy.controller;

import com.pweb.MyWorkoutBuddy.dto.UserDTO;
import com.pweb.MyWorkoutBuddy.model.Response;
import com.pweb.MyWorkoutBuddy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }
}
