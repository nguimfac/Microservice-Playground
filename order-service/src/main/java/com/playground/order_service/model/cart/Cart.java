package com.playground.order_service.model.cart;

import com.playground.constant.CartStatusEnum;
import com.playground.order_service.model.audi.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Cart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long ownerId;

    @Enumerated(EnumType.STRING)
    CartStatusEnum cartStatusEnum;

    @OneToMany(mappedBy = "cart" , fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();


    public Cart(){
       this.cartStatusEnum = CartStatusEnum.INIT;
    }

    public Cart(List<CartItem> cartItems, CartStatusEnum cartStatusEnum) {
        this.cartItems = cartItems;
        this.cartStatusEnum = cartStatusEnum;
    }

    public Cart(long ownerId, CartStatusEnum cartStatusEnum) {
        this.ownerId = ownerId;
        this.cartStatusEnum  =  cartStatusEnum;
    }

    public void addProductToCart(long  productId, int quantity) {
        cartItems.stream()
                .filter(item -> item.getProductId() == productId)
                .findFirst()
                .ifPresentOrElse(
                        item -> item.increaseQuantity(quantity),
                        () -> cartItems.add(new CartItem(productId, quantity ,this))
                );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public CartStatusEnum getCartStatusEnum() {
        return cartStatusEnum;
    }

    public void setCartStatusEnum(CartStatusEnum cartStatusEnum) {
        this.cartStatusEnum = cartStatusEnum;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
