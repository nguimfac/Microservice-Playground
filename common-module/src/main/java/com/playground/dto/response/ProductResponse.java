package com.playground.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(long id , String productCode , int quantity, BigDecimal price, String productName) {
}
