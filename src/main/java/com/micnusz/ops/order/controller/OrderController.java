package com.micnusz.ops.order.controller;


import com.micnusz.ops.order.dto.OrderRequest;
import com.micnusz.ops.order.dto.OrderResponse;
import com.micnusz.ops.order.service.OrderApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderApplicationService orderApplicationService;


    @PostMapping()
    public ResponseEntity<OrderResponse> createEvent(@Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderApplicationService.createOrder(orderRequest);
        return ResponseEntity.created(URI.create("/api/orders/" + orderResponse.getOrderId())).body(orderResponse);
    }
}
