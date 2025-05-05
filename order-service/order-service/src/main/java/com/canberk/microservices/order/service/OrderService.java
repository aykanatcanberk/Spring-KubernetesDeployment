package com.canberk.microservices.order.service;

import client.InventoryClient;
import com.canberk.microservices.order.dto.OrderRequest.OrderRequest;
import com.canberk.microservices.order.model.Order;
import com.canberk.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInStock(orderRequest.code(), orderRequest.quantity());

        if(isProductInStock){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setCode(orderRequest.code());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);
        }
        else{
            throw new RuntimeException("Product with code " + orderRequest.code()+" is not in stock");
        }


    }
}
