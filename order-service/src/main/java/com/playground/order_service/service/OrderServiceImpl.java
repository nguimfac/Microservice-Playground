package com.playground.order_service.service;

import com.playground.dto.response.InventoryResponse;
import com.playground.exceptions.NoSuchElementFoundException;
import com.playground.order_service.config.properties.PropertiesConfig;
import com.playground.order_service.dto.request.OrderRequest;
import com.playground.order_service.model.Order;
import com.playground.order_service.model.OrderLineItems;
import com.playground.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl  implements OrderService{

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    private final PropertiesConfig inventoryProps;


    public OrderServiceImpl(OrderRepository orderRepository, WebClient webClient, PropertiesConfig inventoryProps) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
        this.inventoryProps = inventoryProps;
    }

    @Transactional
    public Order placeOrder(OrderRequest orderRequest){
        List<OrderLineItems>  orderLineItems = orderRequest.orderLineItemsDtos()
                .stream().map(ol->new OrderLineItems(ol.skuCode(), ol.price() , ol.quantity()))
                .toList();

        Order order = new Order(UUID.randomUUID().toString(), orderLineItems);

        List<String> skuCodes = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode)
                .toList();
        InventoryResponse[] responses = webClient.get()
                .uri(inventoryProps.getInventoryUrl(),
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (responses == null || responses.length == 0) {
            throw new NoSuchElementFoundException("No product found in stock");
        }

        boolean allProductsInStock = Arrays.stream(responses).allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) {
            throw new NoSuchElementFoundException("One of the products is not in stock, please try later");
        }
        return  orderRepository.save(order);

    }

}
