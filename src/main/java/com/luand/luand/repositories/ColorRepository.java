package com.luand.luand.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {

        Optional<Color> findByHexColor(String hexColor);

        Optional<Color> findByName(String name);

        Optional<Color> findByRef(String ref);
}
