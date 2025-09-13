package com.nguimfacklearnt.order_service.service;

import com.nguimfack.learnt.dto.exception.NoSuchElementFoundException;
import com.nguimfack.learnt.dto.response.InventoryResponse;
import com.nguimfacklearnt.order_service.event.OrderPlaceEvent;
import lombok.RequiredArgsConstructor;
import com.nguimfacklearnt.order_service.dto.OrderLineItemsDto;
import com.nguimfacklearnt.order_service.dto.OrderRequest;
import com.nguimfacklearnt.order_service.model.Order;
import com.nguimfacklearnt.order_service.model.OrderLineItems;
import com.nguimfacklearnt.order_service.repository.OrderRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;


    @Transactional
    public String  placeOrder(OrderRequest orderRequest){
        List<OrderLineItems>  orderLineItems = orderRequest.getOrderLineItemsDtos()
                .stream().map(this::mapToDto)
                .toList();

        Order order = new Order(UUID.randomUUID().toString(), orderLineItems);

        List<String> skuCodes = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] responses = webClient.get() .uri("http://localhost:8082/api/inventory",
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

        orderRepository.save(order);
        return "Order Placed Successfully";
    }

    private OrderLineItems mapToDto(OrderLineItemsDto ol) {
        return new OrderLineItems(ol.getSkuCode(), ol.getPrice() , ol.getQuantity());
    }
}
