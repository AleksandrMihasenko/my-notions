package com.aleksandrm.mynotions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation errors (from @Valid)
     * Example: email not valid, password too short
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("message", fieldError.getDefaultMessage());
            errors.add(error);
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    /**
     * Handle business logic errors
     * Later will be replaced with domain exceptions
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();

        if (message != null && message.contains("Email already")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);  // 409
        }

        if (message != null && message.contains("Invalid credentials")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);  // 401
        }

        if (message != null && message.contains("Workspace not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);  // 404
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error");
    }
}