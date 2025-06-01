package com.example.MyWorkoutBuddy.service;

import com.example.MyWorkoutBuddy.model.Exercise;
import com.example.MyWorkoutBuddy.model.User;
import com.example.MyWorkoutBuddy.repository.ExerciseRepository;
import com.example.MyWorkoutBuddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public Page<Exercise> getAllExercises(Pageable pageable, String role) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if ("ADMIN".equalsIgnoreCase(role) && user.getRole().equals("ROLE_ADMIN")) {
            return exerciseRepository.findAll(pageable);
        }
        return exerciseRepository.findExerciseByUser_Id(user.getId(), pageable);
    }

    public Optional<Exercise> getExerciseById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        if (exercise.isPresent() && (exercise.get().getUser().getId().equals(user.getId()) || user.getRole().equals("ROLE_ADMIN"))) {
            return exercise;
        }
        return Optional.empty();
    }

    public Exercise createExercise(Exercise exercise) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        exercise.setUser(user);
        return exerciseRepository.save(exercise);
    }

    public Exercise updateExercise(Long id, Exercise exerciseDetails) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found with id " + id));
        if (!exercise.getUser().getId().equals(user.getId()) && !user.getRole().equals("ROLE_ADMIN")) {
            throw new RuntimeException("Unauthorized to update this exercise");
        }
        exercise.setName(exerciseDetails.getName());
        exercise.setDifficulty(exerciseDetails.getDifficulty());
        exercise.setSets(exerciseDetails.getSets());
        exercise.setReps(exerciseDetails.getReps());
        exercise.setTargets(exerciseDetails.getTargets());
        exercise.setImage(exerciseDetails.getImage());
        exercise.setFavorite(exerciseDetails.isFavorite());
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found with id " + id));
        if (!exercise.getUser().getId().equals(user.getId()) && !user.getRole().equals("ROLE_ADMIN")) {
            throw new RuntimeException("Unauthorized to delete this exercise");
        }
        exerciseRepository.deleteById(id);
    }

    public Page<Exercise> getFavoriteExercises(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return exerciseRepository.findExercisesByFavoriteTrueAndUser_Id(user.getId(), pageable);
    }
}
