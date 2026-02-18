package com.micnusz.ops.order.dto;


import com.micnusz.ops.item.dto.ItemEnvelope;
import com.micnusz.ops.order.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    OrderStatus status;

    @NotBlank
    private String title;

    @NotNull
    private Instant timestamp;

    private List<ItemEnvelope> items;
}
