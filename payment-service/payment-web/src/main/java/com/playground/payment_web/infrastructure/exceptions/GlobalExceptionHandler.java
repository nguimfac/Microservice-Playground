package com.playground.payment_web.infrastructure.exceptions;

import java.net.URI;
import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.playground.payment.core.exceptions.BusinessException;
import com.playground.payment.core.exceptions.UnknownProviderException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final String CODE_UNKNOWN_PROVIDER = "UNKNOWN_PROVIDER";
    private static final String CODE_VALIDATION_ERROR = "VALIDATION_ERROR";
    private static final String TIMESTAMP = "timestamp";
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusiness(BusinessException ex, HttpServletRequest req) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(ex.getStatusCode()), ex.getMessage());
        problemDetail.setType(URI.create("https://api.payment-service.com/problems/business-error"));
        problemDetail.setTitle("Business Error");
        problemDetail.setProperty(TIMESTAMP, OffsetDateTime.now());
        problemDetail.setProperty("path", req.getRequestURI());
        return ResponseEntity.status(ex.getStatusCode()).body(problemDetail);
    }

    @ExceptionHandler(UnknownProviderException.class)
    public ResponseEntity<ProblemDetail> handleUnknownProvider(UnknownProviderException ex, HttpServletRequest req) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setType(URI.create("https://api.payment-service.com/problems/unknown-provider"));
        problemDetail.setTitle("Unknown Provider");
        problemDetail.setProperty(TIMESTAMP, OffsetDateTime.now());
        problemDetail.setProperty("path", req.getRequestURI());
        problemDetail.setProperty("error", CODE_UNKNOWN_PROVIDER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String message = "Validation failed";
        if (!ex.getBindingResult().getAllErrors().isEmpty()) {
            message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
        problemDetail.setType(URI.create("https://api.payment-service.com/problems/validation-error"));
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty(TIMESTAMP, OffsetDateTime.now());
        problemDetail.setProperty("path", req.getRequestURI());
        problemDetail.setProperty("error", CODE_VALIDATION_ERROR);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
