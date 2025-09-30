package com.playground.payment.core.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private String message;
    private String error;
    private int status;
    private LocalDateTime timestamp;
    private String path;
}