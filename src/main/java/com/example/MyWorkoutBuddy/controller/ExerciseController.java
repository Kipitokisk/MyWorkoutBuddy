package com.example.MyWorkoutBuddy.controller;

import com.example.MyWorkoutBuddy.model.Exercise;
import com.example.MyWorkoutBuddy.service.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Exercises", description = "API for managing exercises")
@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "http://localhost:5173")
@SecurityRequirement(name = "bearerAuth")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @Operation(summary = "Get all exercises", description = "Retrieve a paginated list of exercises. Admins see all exercises; users see only their own.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved exercises"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public ResponseEntity<Page<Exercise>> getAllExercises(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercises = exerciseService.getAllExercises(pageable);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @Operation(summary = "Get exercise by ID", description = "Retrieve a specific exercise by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved exercise"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseService.getExerciseById(id)
                .map(exercise -> new ResponseEntity<>(exercise, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new exercise", description = "Create a new exercise for the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Exercise created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseService.createExercise(exercise);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an exercise", description = "Update an existing exercise by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exercise updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise exerciseDetails) {
        try {
            Exercise updatedExercise = exerciseService.updateExercise(id, exerciseDetails);
            return new ResponseEntity<>(updatedExercise, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Delete an exercise", description = "Delete an exercise by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Exercise deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        try {
            exerciseService.deleteExercise(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Get favorite exercises", description = "Retrieve a paginated list of favorite exercises for the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved favorite exercises"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/favorites")
    public ResponseEntity<Page<Exercise>> getFavoriteExercises(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> favorites = exerciseService.getFavoriteExercises(pageable);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    @Operation(summary = "Toggle favorite status", description = "Toggle the favorite status of an exercise by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Favorite status updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    @PutMapping("/{id}/favorite")
    public ResponseEntity<Exercise> toggleFavorite(@PathVariable Long id, @RequestBody boolean favorite) {
        try {
            Exercise exercise = exerciseService.getExerciseById(id)
                    .orElseThrow(() -> new RuntimeException("Exercise not found with id " + id));
            exercise.setFavorite(favorite);
            Exercise updatedExercise = exerciseService.updateExercise(id, exercise);
            return new ResponseEntity<>(updatedExercise, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace(); // log the real reason
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
