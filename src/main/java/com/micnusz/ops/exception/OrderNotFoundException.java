package com.micnusz.ops.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(UUID orderId) {
        super("Order with id: " + orderId + " doesn't exists.");
    }
}
