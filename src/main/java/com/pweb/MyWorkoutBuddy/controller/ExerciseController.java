package com.pweb.MyWorkoutBuddy.controller;

import com.pweb.MyWorkoutBuddy.model.Exercise;
import com.pweb.MyWorkoutBuddy.model.Response;
import com.pweb.MyWorkoutBuddy.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<Response> save(@Valid @RequestBody Exercise exercise) {
        return exerciseService.save(exercise);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Response> findByName(@PathVariable String name) {
        return exerciseService.getByName(name);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Response> deleteByName(@PathVariable String name) {
        return exerciseService.delete(name);
    }

    @GetMapping("/paged")
    public ResponseEntity<Response> getExercisesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return exerciseService.getAllPaged(page, size);
    }
}
