package com.luand.luand.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luand.luand.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
