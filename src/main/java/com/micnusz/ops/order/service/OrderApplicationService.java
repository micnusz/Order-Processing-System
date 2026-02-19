package com.micnusz.ops.order.service;



import com.micnusz.ops.order.dto.OrderItem;
import com.micnusz.ops.order.dto.OrderRequest;
import com.micnusz.ops.order.dto.OrderResponse;
import com.micnusz.ops.order.enums.OrderStatus;
import com.micnusz.ops.order.event.OrderCreatedEvent;
import com.micnusz.ops.order.kafka.producer.OrderProducer;
import com.micnusz.ops.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderProducer orderProducer;
    private final OrderMapper orderMapper;


    public OrderResponse createOrder(OrderRequest orderRequest) {

        UUID orderId = UUID.randomUUID();
        Instant createdAt = Instant.now();

        List<OrderItem> items = orderMapper.toDomainItems(orderRequest.getItems());

        BigDecimal totalAmount = items.stream()
                .map(item -> item.getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(orderId)
                .userId(orderRequest.getUserId())
                .items(items)
                .totalAmount(totalAmount)
                .createdAt(createdAt)
                .build();

        orderProducer.publishOrderCreated(event);

        log.info("Order created: orderId={}, userId={}, totalAmount={}",
                orderId, orderRequest.getUserId(), totalAmount);

        // 6. Return response (instant)
        return OrderResponse.builder()
                .orderId(orderId)
                .status(OrderStatus.PENDING)
                .createdAt(createdAt)
                .build();
    }
}

