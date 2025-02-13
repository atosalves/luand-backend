package com.luand.luand.entities.dto.order;

import java.util.Set;

import com.luand.luand.entities.Order;
import com.luand.luand.entities.OrderItem;

public record OrderDetailsDTO(String client, Set<OrderItem> orderItems, Long storeId, String storeName) {
        public OrderDetailsDTO(Order order) {
                this(order.getClient(), order.getOrderItems(), order.getStore().getId(), order.getStore().getName());
        }
}
