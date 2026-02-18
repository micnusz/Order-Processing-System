package com.micnusz.ops.order.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    UUID orderId;
    Instant timestamp;
}
