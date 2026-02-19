package com.micnusz.ops.order.kafka.consumer;

import com.micnusz.ops.order.entity.OrderEntity;
import com.micnusz.ops.order.enums.OrderStatus;
import com.micnusz.ops.order.event.OrderCreatedEvent;
import com.micnusz.ops.order.mapper.OrderMapper;
import com.micnusz.ops.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.micnusz.ops.config.kafka.KafkaTopics.ORDER_CREATED;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @KafkaListener(
            topics = ORDER_CREATED,
            groupId = "order-persistence-service"
    )
    @Transactional
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreated: orderId={}", event.getOrderId());

        OrderEntity entity = orderMapper.toEntity(event, OrderStatus.PENDING);
        orderRepository.save(entity);

        log.info("Order persisted: orderId={}", event.getOrderId());
    }
}
