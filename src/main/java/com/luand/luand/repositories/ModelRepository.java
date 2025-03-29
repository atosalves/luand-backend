package com.luand.luand.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {
        Optional<Model> findByName(String name);
}
