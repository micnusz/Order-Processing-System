package com.micnusz.ops.order.entity;


import com.micnusz.ops.item.dto.ItemEnvelope;
import com.micnusz.ops.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    private UUID orderId;
    private String title;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private List<ItemEnvelope> items;
}
