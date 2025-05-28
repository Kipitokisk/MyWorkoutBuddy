package com.pweb.MyWorkoutBuddy.repository;

import com.pweb.MyWorkoutBuddy.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise findExerciseByName(String name);

    void deleteExerciseByName(String name);
}
