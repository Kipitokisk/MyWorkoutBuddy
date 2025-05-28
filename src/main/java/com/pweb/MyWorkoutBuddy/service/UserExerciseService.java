package com.pweb.MyWorkoutBuddy.service;

import com.pweb.MyWorkoutBuddy.repository.UserExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserExerciseService {
    private final UserExerciseRepository userExerciseRepository;
}
