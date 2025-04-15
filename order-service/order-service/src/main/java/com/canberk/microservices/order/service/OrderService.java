package com.canberk.microservices.order.service;

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
    public void placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setCode(orderRequest.code());
        order.setQuantity(orderRequest.quantity());

        orderRepository.save(order);

    }
}
