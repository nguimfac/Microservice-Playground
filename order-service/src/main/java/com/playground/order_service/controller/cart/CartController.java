package com.playground.order_service.controller.cart;


import com.playground.dto.request.AddProductRequest;
import com.playground.order_service.constant.OrderServiceConstant;
import com.playground.order_service.dto.response.CartItemResponse;
import com.playground.order_service.dto.response.CartResponse;
import com.playground.order_service.service.facade.cart.CartService;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/order/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(path = "/{cartId}/add",
            consumes = OrderServiceConstant.PRODUCT_REQUEST_VENDOR_TYPE,
            produces = OrderServiceConstant.CART_RESPONSE_VENDOR_TYPE
    )
    public ResponseEntity<EntityModel<CartResponse>> addProduct(
            @PathVariable long cartId,
            @Valid @RequestBody AddProductRequest addProductRequest) {

        CartResponse response = cartService.addProductToCart(cartId, addProductRequest);

        EntityModel<CartResponse> resource = EntityModel.of(response,
                linkTo(methodOn(CartController.class).addProduct(cartId, addProductRequest)).withSelfRel(),
                linkTo(methodOn(CartController.class).getCartItems(cartId)).withRel("items"),
                linkTo(methodOn(CartController.class).validateCart(cartId)).withRel("validate")
        );

        return ResponseEntity.ok(resource);
    }


    @PostMapping(path = "/{cartId}/add", produces = OrderServiceConstant.CART_RESPONSE_VENDOR_TYPE)
    @Retry(name = "inventory", fallbackMethod = "retryFallback")
    public ResponseEntity<EntityModel<CartResponse>> validateCart(@PathVariable Long cartId) {
        CartResponse response = cartService.validateCart(cartId);
        EntityModel<CartResponse> resource = EntityModel.of(response,
                linkTo(methodOn(CartController.class).validateCart(cartId)).withSelfRel(),
                linkTo(methodOn(CartController.class).getCartItems(cartId)).withRel("items")
        );
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/{cartId}/items", produces = OrderServiceConstant.CART_ITEM_RESPONSE)
    public ResponseEntity<CollectionModel<EntityModel<CartItemResponse>>> getCartItems(@PathVariable long cartId) {
        List<CartItemResponse> items = cartService.getCartItemsOfCart(cartId);
        List<EntityModel<CartItemResponse>> itemResources = items.stream()
                .map(item -> EntityModel.of(item,
                        linkTo(methodOn(CartController.class).getCartItems(cartId)).withSelfRel()))
                .toList();
        CollectionModel<EntityModel<CartItemResponse>> collection = CollectionModel.of(itemResources,
                linkTo(methodOn(CartController.class).getCartItems(cartId)).withSelfRel(),
                linkTo(methodOn(CartController.class).validateCart(cartId)).withRel("validate"),
                linkTo(methodOn(CartController.class).addProduct(cartId, null)).withRel("addProduct")
        );
        return ResponseEntity.ok(collection);
    }

    public ResponseEntity<?> retryFallback(RuntimeException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE,e.getMessage());
        detail.setInstance(URI.create("error/service-unavailable"));
        return ResponseEntity.status(detail.getStatus()).body(detail);
    }

}
