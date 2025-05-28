package com.pweb.MyWorkoutBuddy.service;

import com.pweb.MyWorkoutBuddy.exception.ResourceAlreadyExistsException;
import com.pweb.MyWorkoutBuddy.exception.ResourceNotFoundException;
import com.pweb.MyWorkoutBuddy.model.Exercise;
import com.pweb.MyWorkoutBuddy.model.Response;
import com.pweb.MyWorkoutBuddy.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pweb.MyWorkoutBuddy.util.ResponseUtil.buildSuccessResponse;


@Service
@RequiredArgsConstructor
@Log
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public ResponseEntity<Response> save(Exercise exercise) {
        log.info("Saving exercise");
        Exercise existingExercise = exerciseRepository.findExerciseByName(exercise.getName());
        if (existingExercise != null) {
            log.warning("Exercise already exists");
            throw new ResourceAlreadyExistsException("Exercise " + exercise.getName() + " already exists");
        }
        Exercise newExercise = exerciseRepository.save(exercise);
        return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse("Exercise created with success", newExercise));
    }

    @Transactional
    public ResponseEntity<Response> getByName(String name) {
        log.info("Searching for exercise with name: " + name);
        Exercise exercise = exerciseRepository.findExerciseByName(name);
        if (exercise == null) {
            log.warning("Could not find exercise");
            throw new ResourceNotFoundException("Exercise " + name + " couldn't be found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse("Exercise found with success", exercise));
    }

    @Transactional
    public ResponseEntity<Response> delete(String name) {
        log.info("Deleting exercise with name: " + name);
        Exercise exercise = exerciseRepository.findExerciseByName(name);
        if (exercise == null) {
            log.warning("Could not find exercise");
            throw new ResourceNotFoundException("Exercise " + name + " couldn't be found");
        }
        exerciseRepository.deleteExerciseByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse("Exercise deleted with success", null));
    }

    @Transactional
    public ResponseEntity<Response> getAllPaged(int page, int size) {
        log.info("Getting exercises page " + page + " with size " + size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercisePage = exerciseRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildSuccessResponse("Exercises page fetched successfully", exercisePage));
    }
}
