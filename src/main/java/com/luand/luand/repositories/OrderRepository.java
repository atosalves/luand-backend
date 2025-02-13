package com.luand.luand.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
