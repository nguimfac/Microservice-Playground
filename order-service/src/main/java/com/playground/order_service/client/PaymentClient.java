package com.playground.order_service.client;

import com.playground.order_service.dto.request.PaymentRequests.AbsaPaymentRequest;
import com.playground.order_service.dto.request.PaymentRequests.OrangeMoneyPaymentRequest;
import com.playground.order_service.dto.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gateway-payment", url = "http://localhost:8080", path = "/api/payments")
public interface PaymentClient {

    @PostMapping
    PaymentResponse payAbsa(@RequestBody AbsaPaymentRequest request);

    @PostMapping
    PaymentResponse payOrange(@RequestBody OrangeMoneyPaymentRequest request);
}
