package com.pweb.MyWorkoutBuddy.repository;

import com.pweb.MyWorkoutBuddy.model.UserExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserExerciseRepository extends JpaRepository<UserExercise, Long> {
    List<UserExercise> findByUserId(Long userId);
    Optional<UserExercise> findByUserIdAndExerciseId(Long userId, Long exerciseId);
}
