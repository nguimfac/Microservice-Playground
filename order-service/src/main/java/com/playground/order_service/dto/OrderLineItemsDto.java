package com.playground.order_service.dto;

import java.math.BigDecimal;


public record OrderLineItemsDto(String skuCode, BigDecimal price, Integer quantity)  {



}
