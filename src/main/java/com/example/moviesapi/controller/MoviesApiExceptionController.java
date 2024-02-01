package com.example.moviesapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

// The class handles validation errors in a centralized manner and respond to clients with meaningful error messages. 
@RestControllerAdvice
public class MoviesApiExceptionController {

    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    // Handle illegal arguments in the HTTP request
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error",
                "Error occurred in the request. Try to post a new request with approved filters: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    // Handle HTTP client exceptions
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, String>> handleClientErrors(HttpClientErrorException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", "Error occurred on the client side. Try to post a new request: " + ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(errorBody);
    }

    // Handle HTTP server exceptions
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Map<String, String>> handleServerErrors(HttpServerErrorException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", "Internal Server Error. Try again once the server is back up: " + ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(errorBody);
    }

    // Handle REST client exceptions
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, String>> handleRestClientException(RestClientException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", "General REST exception. Try to post a new request: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }

    // Handle general exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", "An unexpected error occurred: " + ex.getMessage());
        // Log the exception for debugging
        // ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }
}
