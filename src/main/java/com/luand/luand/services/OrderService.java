package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.Order;
import com.luand.luand.entities.dto.order.CreateOrderDTO;
import com.luand.luand.exception.order.OrderNotFoundException;
import com.luand.luand.repositories.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreService storeService;

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        var result = orderRepository.findAll();
        return result;
    }

    @Transactional
    public Order createOrder(CreateOrderDTO data) {
        var store = storeService.getStoreById(data.storeId());
        var order = new Order(data, store);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        getOrderById(id);
        orderRepository.deleteById(id);
    }

}
