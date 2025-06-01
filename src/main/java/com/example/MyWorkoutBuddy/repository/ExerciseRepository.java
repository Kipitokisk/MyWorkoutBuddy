package com.example.MyWorkoutBuddy.repository;

import com.example.MyWorkoutBuddy.model.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Page<Exercise> findExercisesByFavoriteTrueAndUser_Id(Long userId, Pageable pageable);
    Page<Exercise> findExerciseByUser_Id(Long userId, Pageable pageable);
}
