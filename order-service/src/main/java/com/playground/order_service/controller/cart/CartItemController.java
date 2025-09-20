package com.playground.order_service.controller.cart;


import com.playground.dto.request.AddProductRequest;
import com.playground.order_service.dto.response.ApiResponse;
import com.playground.order_service.dto.response.CartResponse;
import com.playground.order_service.service.impl.cart.CartServiceImpl;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order/cart")
public class CartItemController {

    private final CartServiceImpl cartServiceImpl;

    public CartItemController(CartServiceImpl cartServiceImpl) {
        this.cartServiceImpl = cartServiceImpl;
    }

    @PostMapping(path = "/{cartId}/add",
            produces =  "application/vnd.inventory-service.AddProductRequest+json")
    public ResponseEntity<CartResponse> addProduct(
            @PathVariable long cartId,
            @RequestBody AddProductRequest addProductRequest) {
        CartResponse response = cartServiceImpl.addProductToCart(cartId,addProductRequest);
        return ResponseEntity.ok(response);
    }


    @PostMapping(path = "/{cartId}/add" , produces =  "application/vnd.inventory-service.CartResponse+json")
    @Retry(name = "inventory", fallbackMethod = "retryFallback")
    public ResponseEntity<CartResponse> validateCard(@PathVariable Long cartId) {
        CartResponse response = cartServiceImpl.validateCart(cartId);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> retryFallback(RuntimeException e) {
        ApiResponse<?> response = new ApiResponse<>(e.getMessage(),
                "Inventory indisponible après  retries");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);

    }

}
