package com.playground.order_service.exception;

import com.playground.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionInterceptor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ExceptionResponse exceptionResponse = new ExceptionResponse(errors.toString(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
