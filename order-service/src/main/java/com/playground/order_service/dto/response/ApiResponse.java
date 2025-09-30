package com.playground.order_service.dto.response;

public record ApiResponse<T>(T data , String message){

}
