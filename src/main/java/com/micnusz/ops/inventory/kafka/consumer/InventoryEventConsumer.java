package com.micnusz.ops.inventory.kafka.consumer;


import com.micnusz.ops.exception.InsufficientStockException;
import com.micnusz.ops.inventory.kafka.producer.InventoryProducer;
import com.micnusz.ops.inventory.service.InventoryApplicationService;
import com.micnusz.ops.order.event.OrderCompletedEvent;
import com.micnusz.ops.order.event.OrderCreatedEvent;
import com.micnusz.ops.payment.event.PaymentFailedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.micnusz.ops.config.kafka.KafkaTopics.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private final InventoryApplicationService inventoryService;
    private final InventoryProducer inventoryProducer;

    @KafkaListener(topics = ORDER_CREATED, groupId = "inventory-service")
    @Transactional
    public void handleOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        log.info("Received OrderCreated: orderId={}", orderCreatedEvent.getOrderId());

        try {
            inventoryService.reserveStock(orderCreatedEvent.getOrderId(), orderCreatedEvent.getItems());

            inventoryProducer.publishInventoryReserved(orderCreatedEvent.getOrderId());
        } catch (InsufficientStockException exception) {
            log.error("Insufficient stock for order: {}", orderCreatedEvent.getOrderId(), exception);

            inventoryProducer.publishInventoryFailed(orderCreatedEvent.getOrderId(), exception.getMessage());
        }
    }


    @KafkaListener(topics = PAYMENT_FAILED, groupId = "inventory-service")
    @Transactional
    public void handlePaymentFailed(PaymentFailedEvent paymentFailedEvent) {
        log.warn("Payment failed, unreserving stock: orderId={}", paymentFailedEvent.getOrderId());

        inventoryService.unreserveStock(paymentFailedEvent.getOrderId());

        inventoryProducer.publishInventoryUnreserved(
                paymentFailedEvent.getOrderId(),
                "Payment failed: " + paymentFailedEvent.getReason()
        );
    }


    @KafkaListener(topics = ORDER_COMPLETED, groupId = "inventory-service")
    @Transactional
    public void handleOrderCompleted(OrderCompletedEvent orderCompletedEvent) {
        log.info("Order completed, consuming stock: orderId={}", orderCompletedEvent.getOrderId());

        inventoryService.consumeStock(orderCompletedEvent.getOrderId());
    }
}
