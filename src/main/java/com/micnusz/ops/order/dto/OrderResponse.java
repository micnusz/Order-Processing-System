package com.micnusz.ops.order.dto;

import com.micnusz.ops.order.enums.OrderStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    UUID orderId;
    OrderStatus status;
    Instant timestamp;
}
