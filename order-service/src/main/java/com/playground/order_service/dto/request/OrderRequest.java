package com.playground.order_service.dto.request;

import java.util.List;


public record OrderRequest(List<OrderLineItemsDto> orderLineItemsDtos) {
}
