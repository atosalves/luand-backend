package com.luand.luand.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
}
