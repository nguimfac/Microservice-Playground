package com.playground.order_service.model.cart;

import com.playground.order_service.model.audi.Auditable;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
public class CartItem extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Cart cart;

    private long productId;

    private int quantity;

    public CartItem() {}

    public CartItem(long productId, int quantity) {
        this.productId = productId;
        this.quantity  = quantity;
    }

    public CartItem(long productId, int quantity,Cart cart){
        this(productId, quantity);
        this.cart = cart;
    }

    public void increaseQuantity(int quantity) {
        this.quantity  +=  quantity;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
