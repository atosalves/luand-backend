package com.luand.luand.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
        Optional<Image> findByNameKey(String nameKey);

        Optional<Image> findByUrl(String url);
}
