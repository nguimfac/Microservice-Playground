package com.playground.order_service.dao;

import com.playground.order_service.entities.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem , Long> {

}
