package com.micnusz.ops.config.kafka;

public class KafkaTopics {

    // Order topics
    public static final String ORDER_CREATED = "order.created";
    public static final String ORDER_COMPLETED = "order.completed";
    public static final String ORDER_CANCELLED = "order.cancelled";

    // Inventory topics
    public static final String INVENTORY_RESERVED = "inventory.reserved";
    public static final String INVENTORY_FAILED = "inventory.failed";
    public static final String INVENTORY_UNRESERVED = "inventory.unreserved";

    // Payment topics
    public static final String PAYMENT_PROCESSED = "payment.processed";
    public static final String PAYMENT_FAILED = "payment.failed";
    public static final String PAYMENT_REFUNDED = "payment.refunded";

    // Shipping topics
    public static final String SHIPPING_CREATED = "shipping.created";
    public static final String SHIPPING_FAILED = "shipping.failed";

    // DLQ
    public static final String ORDER_DLT = "order-dlt";

    private KafkaTopics() {
    }
}
