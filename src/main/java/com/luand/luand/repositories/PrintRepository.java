package com.luand.luand.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.Print;

public interface PrintRepository extends JpaRepository<Print, Long> {
}
