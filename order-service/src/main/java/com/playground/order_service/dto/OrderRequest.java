package com.playground.order_service.dto;

import java.util.List;


public record OrderRequest(List<OrderLineItemsDto> orderLineItemsDtos) {
}
