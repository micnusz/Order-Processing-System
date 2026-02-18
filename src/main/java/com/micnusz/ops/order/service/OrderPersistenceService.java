package com.micnusz.ops.order.service;


import com.micnusz.ops.order.dto.OrderEnvelope;
import com.micnusz.ops.order.entity.OrderEntity;
import com.micnusz.ops.order.mapper.OrderPersistenceMapper;
import com.micnusz.ops.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderPersistenceService {

    private final OrderRepository orderRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    public void saveOrder(OrderEnvelope orderEnvelope) {
        OrderEntity orderEntity = orderPersistenceMapper.toEntity(orderEnvelope);
        orderRepository.save(orderEntity);
    }
}
