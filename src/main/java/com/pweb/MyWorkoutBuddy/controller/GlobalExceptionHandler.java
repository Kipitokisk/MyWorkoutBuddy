package com.pweb.MyWorkoutBuddy.controller;

import com.pweb.MyWorkoutBuddy.exception.ForbiddenException;
import com.pweb.MyWorkoutBuddy.exception.ResourceAlreadyExistsException;
import com.pweb.MyWorkoutBuddy.exception.ResourceNotFoundException;
import com.pweb.MyWorkoutBuddy.model.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pweb.MyWorkoutBuddy.util.ResponseUtil.getErrorResponse;

@RestControllerAdvice
@Log
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Response> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        log.warning("Resource already exists: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(getErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warning("Validation error: " + ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorResponse("Validation failed: " + errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warning("Constraint violation: " + ex.getMessage());

        String violations = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getErrorResponse("Validation failed: " + violations));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warning("Resource not found: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Response> handleForbiddenException(ForbiddenException ex) {
        log.warning("Forbidden access: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(getErrorResponse(ex.getMessage()));
    }
}
