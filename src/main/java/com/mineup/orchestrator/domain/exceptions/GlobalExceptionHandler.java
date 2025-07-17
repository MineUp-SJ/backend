package com.mineup.orchestrator.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(body));
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleAlreadyExists(ResourceAlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Already Exists");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleGeneric(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Internal Server Error");
        body.put("message", "An unexpected error occurred");
        body.put("timestamp", LocalDateTime.now());
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
    }
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleBindException(WebExchangeBindException ex) {
    Map<String, Object> body = new HashMap<>();
        body.put("error", "Bad Request");
        body.put("message", "Invalid input data");
        body.put("timestamp", LocalDateTime.now());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }
}