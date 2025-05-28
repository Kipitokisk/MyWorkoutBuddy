package com.pweb.MyWorkoutBuddy.controller;

import com.pweb.MyWorkoutBuddy.dto.UserDTO;
import com.pweb.MyWorkoutBuddy.model.Response;
import com.pweb.MyWorkoutBuddy.model.User;
import com.pweb.MyWorkoutBuddy.service.UserService;
import com.pweb.MyWorkoutBuddy.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pweb.MyWorkoutBuddy.util.ResponseUtil.buildSuccessResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> createAuthenticationToken(@RequestBody UserDTO userDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        User user = (User) userService.findUserByUsername(userDTO.getUsername()).getBody().getData();
        String username = user.getUsername();
        String role = user.getRole().name();
        String token = jwtUtil.generateToken(username, role);
        return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse("Login successfull", token));
    }
}
