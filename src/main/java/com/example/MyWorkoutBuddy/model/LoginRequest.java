package com.example.MyWorkoutBuddy.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}