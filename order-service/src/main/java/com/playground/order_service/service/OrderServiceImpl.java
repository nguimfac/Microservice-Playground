package com.playground.order_service.service;

import com.playground.dto.response.InventoryResponse;
import com.playground.exceptions.NoSuchElementFoundException;
import com.playground.order_service.dto.request.OrderLineItemsDto;
import com.playground.order_service.dto.request.OrderRequest;
import com.playground.order_service.model.Order;
import com.playground.order_service.model.OrderLineItems;
import com.playground.order_service.repository.OrderRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl  implements OrderService{

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public OrderServiceImpl(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }


    @Transactional
    public Order placeOrder(OrderRequest orderRequest){
        List<OrderLineItems>  orderLineItems = orderRequest.orderLineItemsDtos()
                .stream().map(this::mapToDto)
                .toList();

        Order order = new Order(UUID.randomUUID().toString(), orderLineItems);

        List<String> skuCodes = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] responses = webClient.get() .uri("http://localhost:8080/api/inventory",
                        uriBuilder->uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        Mono.error(new NoSuchElementFoundException("Inventory service unavailable")))
                .bodyToMono(InventoryResponse[].class)
                .blockOptional()
                .orElseThrow(() -> new NoSuchElementFoundException("No product found in stock"));

        boolean allProductsInStock = Arrays.stream(responses).allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) {
            throw new NoSuchElementFoundException("One of the products is not in stock, please try later");
        }
        return  orderRepository.save(order);

    }

    private OrderLineItems mapToDto(OrderLineItemsDto ol) {
        return new OrderLineItems(ol.skuCode(), ol.price() , ol.quantity());
    }
}
