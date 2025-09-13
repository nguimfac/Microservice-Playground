package com.playground.inventory_service.exception;


import com.playground.exceptions.ExceptionResponse;
import com.playground.exceptions.NoSuchElementFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionInterceptor {
    @ExceptionHandler(NoSuchElementFoundException.class)
    public final ResponseEntity<?> handleNoSuchElementFoundException(NoSuchElementFoundException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
