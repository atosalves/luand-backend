package com.luand.luand.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.FashionLine;

public interface FashionLineRepository extends JpaRepository<FashionLine, Long> {
    Optional<FashionLine> findByPrint(String print);
}
