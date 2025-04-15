package com.canberk.microservices.order.controller;

import com.canberk.microservices.order.dto.OrderRequest.OrderRequest;
import com.canberk.microservices.order.repository.OrderRepository;
import com.canberk.microservices.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderRequest placeOrder( @Valid @RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return orderRequest;
    }

}
