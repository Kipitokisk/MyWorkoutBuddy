package com.pweb.MyWorkoutBuddy.service;

import com.pweb.MyWorkoutBuddy.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
}
