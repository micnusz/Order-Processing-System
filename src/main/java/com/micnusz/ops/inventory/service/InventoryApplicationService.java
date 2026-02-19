package com.micnusz.ops.inventory.service;


import com.micnusz.ops.exception.InsufficientStockException;
import com.micnusz.ops.exception.ProductNotFoundException;
import com.micnusz.ops.inventory.entity.ProductEntity;
import com.micnusz.ops.inventory.entity.StockReservationEntity;
import com.micnusz.ops.inventory.enums.ReservationStatus;
import com.micnusz.ops.inventory.repository.ProductRepository;
import com.micnusz.ops.inventory.repository.StockReservationRepository;
import com.micnusz.ops.order.dto.OrderItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryApplicationService {

    private final ProductRepository productRepository;
    private final StockReservationRepository reservationRepository;


    @Transactional
    public void reserveStock (UUID orderId, List<OrderItem> orderItems){
        log.info("Reserving stock for order: {}", orderId);

        for (OrderItem item : orderItems) {
            ProductEntity product = productRepository.findByIdForUpdate(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(item.getProductId()));

            if (product.getAvailableQuantity() < item.getQuantity()) {
                throw new InsufficientStockException(
                        String.format("Product %s has only %d available (requested: %d)",
                                product.getName(),
                                product.getAvailableQuantity(),
                                item.getQuantity())
                );
            }

            product.setAvailableQuantity(product.getAvailableQuantity() - item.getQuantity());
            product.setReservedQuantity(product.getReservedQuantity() + item.getQuantity());
            productRepository.save(product);

            StockReservationEntity reservation = StockReservationEntity.builder()
                    .orderId(orderId)
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .status(ReservationStatus.RESERVED)
                    .reservedAt(Instant.now())
                    .build();
            reservationRepository.save(reservation);

            log.info("Reserved {} units of product {} for order {}",
                    item.getQuantity(), product.getName(), orderId);
        }
    }
        @Transactional
        public void unreserveStock(UUID orderId) {
            log.info("Unreserving stock for order: {} (compensation)", orderId);

            List<StockReservationEntity> reservations = reservationRepository
                    .findByOrderIdAndStatus(orderId, ReservationStatus.RESERVED);

            for (StockReservationEntity reservation : reservations) {
                ProductEntity product = productRepository.findByIdForUpdate(reservation.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException(reservation.getProductId()));

                product.setAvailableQuantity(product.getAvailableQuantity() + reservation.getQuantity());
                product.setReservedQuantity(product.getReservedQuantity() - reservation.getQuantity());
                productRepository.save(product);

                reservation.setStatus(ReservationStatus.RELEASED);
                reservation.setReleasedAt(Instant.now());
                reservationRepository.save(reservation);

                log.info("Unreserved {} units of product {} for order {}",
                        reservation.getQuantity(), product.getName(), orderId);
            }
    }

    @Transactional
    public void consumeStock(UUID orderId) {
        log.info("Consuming stock for completed order: {}", orderId);

        List<StockReservationEntity> reservations = reservationRepository
                .findByOrderIdAndStatus(orderId, ReservationStatus.RESERVED);

        for (StockReservationEntity reservation : reservations) {
            ProductEntity product = productRepository.findById(reservation.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(reservation.getProductId()));

            product.setReservedQuantity(product.getReservedQuantity() - reservation.getQuantity());
            productRepository.save(product);

            reservation.setStatus(ReservationStatus.CONSUMED);
            reservationRepository.save(reservation);

            log.info("Consumed {} units of product {} for order {}",
                    reservation.getQuantity(), product.getName(), orderId);
        }
    }
}
