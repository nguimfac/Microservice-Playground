package com.playground.inventory_service.exceptions;


import com.playground.exceptions.ExceptionResponse;
import com.playground.exceptions.NoSuchElementFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ExceptionInterceptor {
    @ExceptionHandler(NoSuchElementFoundException.class)
    public final ResponseEntity<?> handleNoSuchElementFoundException(NoSuchElementFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND , ex.getMessage());
        problemDetail.setInstance(URI.create(""));
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
