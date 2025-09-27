package com.playground.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        @NotNull(message = "productCode can't be null")
        @NotEmpty(message = "productCode can't be empty")
        String productCode ,

        @Min(value = 1 , message = "quantity must be atlist 1")
        int quantity ,

        @NotNull(message = "price can't be null ")
        @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
        BigDecimal price ,

        @NotNull(message = "productName can't be null")
        @NotEmpty(message = "productName can't be empty")
        String productName,

        @Min(value = 1, message = "categoryId must be atlist 1")
        long categoryId) {

}
