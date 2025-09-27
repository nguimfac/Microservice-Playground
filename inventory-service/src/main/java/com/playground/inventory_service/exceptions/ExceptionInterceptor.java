package com.playground.inventory_service.exceptions;

import com.playground.exceptions.NoSuchElementFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(NoSuchElementFoundException.class)
    public final ResponseEntity<ProblemDetail> handleNoSuchElementFoundException(NoSuchElementFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND , ex.getMessage());
        problemDetail.setInstance(URI.create("/errors/no-such-element"));
        return new ResponseEntity<>(problemDetail , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Optional.ofNullable(fieldError.getDefaultMessage())
                                .orElse("Validation error"),
                        // ici on garde le premier si doublon
                        (msg1, msg2) -> msg1
                ));
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail("Validation failed for request body");
        problemDetail.setProperty("errors", errors);
        // uri va permettre de indiquer au client le lien vers la documentation pour avoir une explication a cette erreur
        problemDetail.setInstance(URI.create("/errors/validation"));
        return ResponseEntity.badRequest().body(problemDetail);
    }




}
