package com.micnusz.ops.order.mapper;


import com.micnusz.ops.order.dto.OrderItem;
import com.micnusz.ops.order.dto.OrderItemRequest;
import com.micnusz.ops.order.entity.OrderEntity;
import com.micnusz.ops.order.entity.OrderItemEntity;
import com.micnusz.ops.order.enums.OrderStatus;
import com.micnusz.ops.order.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderEntity toEntity(OrderCreatedEvent event, OrderStatus status) {
        OrderEntity entity = OrderEntity.builder()
                .id(event.getOrderId())
                .userId(event.getUserId())
                .status(status)
                .totalAmount(event.getTotalAmount())
                .createdAt(event.getCreatedAt())
                .build();

        List<OrderItemEntity> itemEntities = event.getItems().stream()
                .map(this::toItemEntity)
                .collect(Collectors.toList());

        entity.setItems(itemEntities);
        return entity;
    }

    private OrderItemEntity toItemEntity(OrderItem item) {
        return OrderItemEntity.builder()
                .productId(item.getProductId())
                .productName(item.getProductName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .build();
    }

    public List<OrderItem> toDomainItems(List<OrderItemRequest> requests) {
        return requests.stream()
                .map(req -> OrderItem.builder()
                        .productId(req.getProductId())
                        .productName(req.getProductName())
                        .quantity(req.getQuantity())
                        .unitPrice(req.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }

}
