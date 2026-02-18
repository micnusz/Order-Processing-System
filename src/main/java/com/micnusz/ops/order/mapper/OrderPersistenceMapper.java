package com.micnusz.ops.order.mapper;

import com.micnusz.ops.order.dto.OrderEnvelope;
import com.micnusz.ops.order.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceMapper {


    public OrderEntity toEntity(OrderEnvelope orderEnvelope) {
        return OrderEntity.builder().orderId(orderEnvelope.getOrderId()).title(orderEnvelope.getTitle()).status(orderEnvelope.getStatus()).items(orderEnvelope.getItems()).build();

    }
}
