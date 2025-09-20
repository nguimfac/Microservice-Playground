package com.playground.order_service.exception;

import com.playground.exceptions.ExceptionResponse;
import com.playground.exceptions.InvalidDataException;
import com.playground.exceptions.NoSuchElementFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
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

    @ExceptionHandler(InvalidDataException.class)
    public final ResponseEntity<ProblemDetail> handleNoSuchElementFoundException(NoSuchElementFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND , ex.getMessage());
        problemDetail.setInstance(URI.create("/errors/invalid-data"));
        return new ResponseEntity<>(problemDetail , HttpStatus.NOT_FOUND);
    }


}
