package com.micnusz.ops.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static com.micnusz.ops.config.kafka.KafkaTopics.*;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private static final int DEFAULT_PARTITIONS = 3;
    private static final int DEFAULT_REPLICAS = 1;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic orderCreatedTopic() {
        return createTopic(ORDER_CREATED);
    }

    @Bean
    public NewTopic orderCompletedTopic() {
        return createTopic(ORDER_COMPLETED);
    }

    @Bean
    public NewTopic orderCancelledTopic() {
        return createTopic(ORDER_CANCELLED);
    }


    @Bean
    public NewTopic inventoryReservedTopic() {
        return createTopic(INVENTORY_RESERVED);
    }

    @Bean
    public NewTopic inventoryFailedTopic() {
        return createTopic(INVENTORY_FAILED);
    }

    @Bean
    public NewTopic inventoryUnreservedTopic() {
        return createTopic(INVENTORY_UNRESERVED);
    }


    @Bean
    public NewTopic paymentProcessedTopic() {
        return createTopic(PAYMENT_PROCESSED);
    }

    @Bean
    public NewTopic paymentFailedTopic() {
        return createTopic(PAYMENT_FAILED);
    }

    @Bean
    public NewTopic paymentRefundedTopic() {
        return createTopic(PAYMENT_REFUNDED);
    }


    @Bean
    public NewTopic shippingCreatedTopic() {
        return createTopic(SHIPPING_CREATED);
    }

    @Bean
    public NewTopic shippingFailedTopic() {
        return createTopic(SHIPPING_FAILED);
    }


    @Bean
    public NewTopic orderDltTopic() {
        return TopicBuilder.name(ORDER_DLT)
                .partitions(1)
                .replicas(DEFAULT_REPLICAS)
                .build();
    }



    private NewTopic createTopic(String name) {
        return TopicBuilder.name(name)
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .build();
    }


}
