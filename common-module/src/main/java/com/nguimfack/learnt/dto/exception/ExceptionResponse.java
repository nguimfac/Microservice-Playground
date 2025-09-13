package com.nguimfack.learnt.dto.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(String message , int code) {
}
