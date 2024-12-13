package com.luand.luand.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
