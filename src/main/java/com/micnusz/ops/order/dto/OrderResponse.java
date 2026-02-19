package com.micnusz.ops.order.dto;

import com.micnusz.ops.order.enums.OrderStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private UUID orderId;
    private OrderStatus status;
    private Instant createdAt;
}
