package com.micnusz.ops.order.dto;


import com.micnusz.ops.item.dto.ItemEnvelope;
import com.micnusz.ops.order.enums.OrderStatus;


import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderEnvelope(
        UUID orderId,
        OrderStatus status,
        String title,
        Instant timestamp,
        int version,
        List<ItemEnvelope> items
) {}
