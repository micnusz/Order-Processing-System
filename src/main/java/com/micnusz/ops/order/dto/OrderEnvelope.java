package com.micnusz.ops.order.dto;


import com.micnusz.ops.item.dto.ItemEnvelope;
import com.micnusz.ops.order.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEnvelope {

    @Id
    UUID orderId;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @NotBlank
    String title;

    @Timestamp
    Instant timestamp;

    private int version;

    List<ItemEnvelope> items;
}
