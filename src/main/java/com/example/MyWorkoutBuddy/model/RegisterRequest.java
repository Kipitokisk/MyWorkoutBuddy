package com.example.MyWorkoutBuddy.model;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role;
}