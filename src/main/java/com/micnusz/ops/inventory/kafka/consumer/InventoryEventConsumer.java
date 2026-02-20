package com.micnusz.ops.inventory.kafka.consumer;


import com.micnusz.ops.inventory.kafka.producer.InventoryProducer;
import com.micnusz.ops.inventory.service.InventoryApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private final InventoryApplicationService inventoryService;
    private final InventoryProducer inventoryProducer;
}
