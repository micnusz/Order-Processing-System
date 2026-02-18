package com.micnusz.ops.order.service;


import com.micnusz.ops.order.dto.OrderEnvelope;
import com.micnusz.ops.order.dto.OrderRequest;
import com.micnusz.ops.order.dto.OrderResponse;
import com.micnusz.ops.order.enums.OrderStatus;
import com.micnusz.ops.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderApplicationService {


    public OrderResponse createOrder(OrderRequest orderRequest) {

        UUID orderId = UUID.randomUUID();

        OrderEnvelope envelope = new OrderEnvelope(
                orderId, orderRequest.getStatus(), orderRequest.getTitle(), orderRequest.getTimestamp(),1, orderRequest.getItems()
        );
        log.info("Order created. orderId={}",
                orderId);

        return new OrderResponse(orderId, OrderStatus.PENDING, Instant.now());
    }
}
