package com.micnusz.ops.inventory.kafka.producer;


import com.micnusz.ops.inventory.event.InventoryFailedEvent;
import com.micnusz.ops.inventory.event.InventoryReservedEvent;
import com.micnusz.ops.inventory.event.InventoryUnreservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

import static com.micnusz.ops.config.kafka.KafkaTopics.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void publishInventoryReserved(UUID orderId) {
        InventoryReservedEvent inventoryReservedEvent = InventoryReservedEvent.builder()
                .orderId(orderId)
                .reservedAt(Instant.now())
                .build();

        kafkaTemplate.send(INVENTORY_RESERVED, orderId.toString(), inventoryReservedEvent)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Published InventoryReserved: orderId={}", orderId);
                    } else {
                        log.error("Failed to publish InventoryReserved: orderId={}", orderId, ex);
                    }
                });
    }

    public void publishInventoryFailed(UUID orderId, String reason ) {
        InventoryFailedEvent inventoryFailedEvent = InventoryFailedEvent.builder()
                .orderId(orderId).reason(reason).failedAt(Instant.now()).build();

        kafkaTemplate.send(INVENTORY_FAILED, orderId.toString(), inventoryFailedEvent)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.warn("Published InventoryFailed: orderId={}, reason={}", orderId, reason);
                    } else {
                        log.error("Failed to publish InventoryFailed: orderId={}", orderId, ex);
                    }
                });
    }

    public void publishInventoryUnreserved(UUID orderId, String reason) {
        InventoryUnreservedEvent inventoryUnreservedEvent = InventoryUnreservedEvent.builder()
                .orderId(orderId).reason(reason).unreservedAt(Instant.now()).build();

        kafkaTemplate.send(INVENTORY_UNRESERVED, orderId.toString(), inventoryUnreservedEvent)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Published InventoryUnreserved: orderId={}", orderId);
                    } else {
                        log.error("Failed to publish InventoryUnreserved: orderId={}", orderId, ex);
                    }
                });
    }
}


