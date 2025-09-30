package com.playground.payment_service.application.web;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playground.payment_service.application.dto.BasePaymentRequest;
import com.playground.payment_service.application.dto.PaymentResponse;
import com.playground.payment_service.domain.ports.inbound.PaymentService;
import com.playground.payment_service.infrastructure.factories.PaymentFactory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@Validated
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentFactory paymentFactory;

	@PostMapping
	public ResponseEntity<PaymentResponse> pay(@Valid @RequestBody BasePaymentRequest request) {
		PaymentService service = paymentFactory.getStrategy(request.provider());
		return ResponseEntity.ok(service.handlePayment(request));
	}

	@GetMapping("/{provider}/{id}")
	public ResponseEntity<PaymentResponse> check(@PathVariable String provider, @PathVariable Long id) {
		PaymentService service = paymentFactory.getStrategy(provider);
		return ResponseEntity.ok(service.checkPayment(id));
	}
}
