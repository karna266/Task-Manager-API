package com.example.Task.Management.API.exceptions;


import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(ValidationException ex) {
        log.error("Validation error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));

    }
        @ExceptionHandler(TaskNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleTaskNotFound(TaskNotFoundException ex) {
            log.error("Task not found: {}", ex.getMessage());
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
            log.error("User not found: {}", ex.getMessage());
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }
}
