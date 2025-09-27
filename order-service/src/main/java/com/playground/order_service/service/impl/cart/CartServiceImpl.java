package com.playground.order_service.service.impl.cart;


import com.playground.constant.CartStatusEnum;
import com.playground.constant.CartStrategyEnum;
import com.playground.dto.request.AddProductRequest;
import com.playground.dto.response.InventoryResponse;
import com.playground.exceptions.InvalidDataException;
import com.playground.exceptions.NoSuchElementFoundException;
import com.playground.order_service.dto.request.CartRequest;
import com.playground.order_service.dto.response.CartItemResponse;
import com.playground.order_service.dto.response.CartResponse;
import com.playground.order_service.mapper.CartMapper;
import com.playground.order_service.entities.cart.Cart;
import com.playground.order_service.dao.CartRepository;

import com.playground.order_service.service.facade.cart.CartService;
import com.playground.order_service.service.facade.cart.CartStrategy;
import com.playground.order_service.service.facade.feignClient.InventoryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl  implements CartService {

    private final Map<CartStrategyEnum, CartStrategy> strategies;

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    private final InventoryClient inventoryClient;

    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper,  InventoryClient inventoryClient){
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.strategies =  new HashMap<>();
        this.inventoryClient =  inventoryClient;
        strategies.put(CartStrategyEnum.DEFAULT, new DefaultCartStrategyImpl(cartRepository));
        strategies.put(CartStrategyEnum.PROMOTION, new PromotionCartStrategyImpl());
        strategies.put(CartStrategyEnum.BUNDLE,  new BundleCartStrategyImpl());
    }

    @Transactional
    public CartResponse addProductToCart(long cartId,AddProductRequest addProductRequest) {
        log.info("Try to add product to cart id {}", cartId);
        CartStrategy strategy = strategies.getOrDefault(addProductRequest.strategy(), new DefaultCartStrategyImpl(cartRepository));
        Cart cart  = strategy.addProduct(cartId, addProductRequest.productId(), addProductRequest.quantity());
        return cartMapper.toDto(cart);
    }

    @Override
    public CartResponse createCart(CartRequest cartRequest) {        log.info("Try to add product to cart");
        log.info("Try to create cart ");
        Cart cart = new Cart(cartRequest.ownerId() , CartStatusEnum.INIT);
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    @Override
    public CartResponse findCartById(long cartId) {
        log.info("Try getting cart with id {}", cartId);
        Cart cart =  cartRepository.findById(cartId).orElseThrow(()->new NoSuchElementFoundException(" No cart found with id "+ cartId));
        return cartMapper.toDto(cart);
    }

    @Override
    public List<CartItemResponse> getCartItemsOfCart(long cartId) {
        log.info("Try getting cart items  {}", cartId);
        CartResponse cart =  findCartById(cartId);
        return cart.cartItems();
    }

    @Transactional
    public CartResponse validateCart(long cartId){
        log.info("Try to validate cart with id  {}", cartId);
        Cart cart = cartRepository.findById(cartId).orElseThrow(()->new NoSuchElementFoundException("No cart found with id " + cartId));
        List<String> productIds = cart.getCartItems()
                .stream().map(c->String.valueOf(c.getProductId()))
                .toList();
        // check if product are available in stock
        List<InventoryResponse> inventoryClients =  inventoryClient.checkStock(productIds);
        if(inventoryClients.stream().anyMatch(iv->!iv.isInStock())){
           throw new InvalidDataException("one of the products is out of stock");
        }
        cart.setCartStatusEnum(CartStatusEnum.SOLD);
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }





}
