package com.playground.order_service.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;


public record OrderRequest(
        @NotNull(message = "orderLineItemsDtos can't be null")
        List<OrderLineItemsDto> orderLineItemsDtos
) {
}
