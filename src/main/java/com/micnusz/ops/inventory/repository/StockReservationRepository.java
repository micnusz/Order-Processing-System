package com.micnusz.ops.inventory.repository;

import com.micnusz.ops.inventory.entity.StockReservationEntity;
import com.micnusz.ops.inventory.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StockReservationRepository extends JpaRepository<StockReservationEntity, UUID> {

    List<StockReservationEntity> findByOrderId(UUID orderId);
    List<StockReservationEntity> findByOrderIdAndStatus(UUID orderId, ReservationStatus status);
}
