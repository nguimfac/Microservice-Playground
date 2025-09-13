package com.nguimfack.learnt.dto.request;

import java.math.BigDecimal;

public record ProductRequest(String id , String name , String description , BigDecimal price) {

}
