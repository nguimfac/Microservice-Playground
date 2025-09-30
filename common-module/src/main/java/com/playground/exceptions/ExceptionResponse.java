package com.playground.exceptions;

import lombok.Builder;

@Builder
public record ExceptionResponse(String message , int code) {
}
