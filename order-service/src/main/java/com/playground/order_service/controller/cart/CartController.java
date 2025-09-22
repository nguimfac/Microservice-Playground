package com.playground.order_service.controller.cart;


import com.playground.dto.request.AddProductRequest;
import com.playground.order_service.dto.response.CartResponse;
import com.playground.order_service.service.facade.cart.CartService;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/order/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(path = "/{cartId}/add",
            consumes =  "application/vnd.inventory-service.AddProductRequest+json",
            produces = "application/vnd.inventory-service.CartResponse+json"
    )
    public ResponseEntity<CartResponse> addProduct(
            @PathVariable long cartId,
            @RequestBody AddProductRequest addProductRequest) {
        CartResponse response = cartService.addProductToCart(cartId,addProductRequest);
        return ResponseEntity.ok(response);
    }


    @PostMapping(path = "/{cartId}/add" , produces =  "application/vnd.inventory-service.CartResponse+json")
    @Retry(name = "inventory", fallbackMethod = "retryFallback")
    public ResponseEntity<CartResponse> validateCard(@PathVariable Long cartId) {
        CartResponse response = cartService.validateCart(cartId);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> retryFallback(RuntimeException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE,e.getMessage());
        detail.setInstance(URI.create("error/service-unavailable"));
        return ResponseEntity.status(detail.getStatus()).body(detail);
    }

}
