package com.micnusz.ops.order.kafka.producer;

import com.micnusz.ops.order.event.OrderCancelledEvent;
import com.micnusz.ops.order.event.OrderCompletedEvent;
import com.micnusz.ops.order.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.micnusz.ops.config.kafka.KafkaTopics.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderCreated(OrderCreatedEvent event) {
        kafkaTemplate.send(ORDER_CREATED, event.getOrderId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Published OrderCreated: orderId={}", event.getOrderId());
                    } else {
                        log.error("Failed to publish OrderCreated: orderId={}",
                                event.getOrderId(), ex);
                    }
                });
    }

    public void publishOrderCompleted(OrderCompletedEvent event) {
        kafkaTemplate.send(ORDER_COMPLETED, event.getOrderId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Published OrderCompleted: orderId={}", event.getOrderId());
                    } else {
                        log.error("Failed to publish OrderCompleted: orderId={}",
                                event.getOrderId(), ex);
                    }
                });
    }

    public void publishOrderCancelled(OrderCancelledEvent event) {
        kafkaTemplate.send(ORDER_CANCELLED, event.getOrderId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Published OrderCancelled: orderId={}", event.getOrderId());
                    } else {
                        log.error("Failed to publish OrderCancelled: orderId={}",
                                event.getOrderId(), ex);
                    }
                });
    }
}
