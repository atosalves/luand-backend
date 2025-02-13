package com.luand.luand.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByColor(String color);
}
