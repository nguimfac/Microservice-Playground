package com.playground.dto.response;

import lombok.Builder;


@Builder
public record InventoryResponse(String skuCode , boolean isInStock) {

}
