package com.micnusz.ops.order.dto;


import com.micnusz.ops.order.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Timestamp;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @NotBlank
    String title;

    @Timestamp
    Instant timestamp;
}
