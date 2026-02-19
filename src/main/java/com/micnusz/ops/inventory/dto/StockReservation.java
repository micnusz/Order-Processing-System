package com.micnusz.ops.inventory.dto;

import com.micnusz.ops.inventory.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockReservation {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private Integer quantity;
    private ReservationStatus status;
    private Instant reservedAt;
    private Instant releasedAt;
}