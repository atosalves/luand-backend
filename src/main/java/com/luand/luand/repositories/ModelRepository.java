package com.luand.luand.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
