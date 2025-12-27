package com.backenddemo.order_service.services;

import com.backenddemo.order_service.client.InventoryClient;
import com.backenddemo.order_service.dto.InventoryResponse;
import com.backenddemo.order_service.dto.OrderLineItemsDto;
import com.backenddemo.order_service.dto.OrderRequest;
import com.backenddemo.order_service.model.Order;
import com.backenddemo.order_service.model.OrderLineItems;
import com.backenddemo.order_service.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.rmi.server.UID;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
// Services create data inside a database

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    final OrderRepository orderRepository;
//    final WebClient webClient;
final WebClient.Builder webClientbuilder;

    @Autowired
    InventoryClient inventoryClient;

//    public OrderService(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }

    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> items = orderRequest.getOrderLineListDto()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineList(items);

        List<String> skuCodes = order.getOrderLineList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // Call inventory service, and place order if product is available in stock
        // retrieve() -> means what reposonce we are expecting
        // and then we need to provide response type below it's "bodyToMono"
        // By default, webclient makes asynchrounus request
        // So in order to make synchronus request we use block

//        InventoryResponse[] inventoryResponsearray = webClientbuilder.build().get()
//                .uri(uriBuilder -> {
//                    UriBuilder ub = uriBuilder.path("/api/inventory");
//                    for (String sku : skuCodes) {
//                        ub = ub.queryParam("skuCode", sku);
//                    }
//                    return ub.build();
//                })
//                .retrieve()
//                .bodyToMono(InventoryResponse[].class)
//                .block();
//
//        if (inventoryResponsearray == null) {
//            throw new IllegalStateException("Inventory service returned no body");
//        }

        InventoryResponse[] inventoryResponsearray = inventoryClient.getInventory(skuCodes).toArray(new InventoryResponse[0]);

//                webClientbuilder.build().get()
//                .uri("http://localhost:64318/api/inventory",
//                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
//                .retrieve()
//                .bodyToMono(InventoryResponse[].class)// THis line has issue
//                .block();
        boolean allProductIsInStock = Arrays.stream(inventoryResponsearray).allMatch(InventoryResponse::isInStock);
        if (allProductIsInStock) {
            // save data in database
            orderRepository.save(order);
        } else {
            throw new IllegalAccessException("Product is not in stock. Try again Later.");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItems) {
        OrderLineItems items = new OrderLineItems();
        items.setQuantity(orderLineItems.getQuantity());
        items.setPrice(orderLineItems.getPrice());
        items.setSkuCode(orderLineItems.getSkuCode());
        return items;
    }
}
