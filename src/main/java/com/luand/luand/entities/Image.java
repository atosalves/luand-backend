package com.luand.luand.entities;

import java.io.Serializable;

import com.luand.luand.entities.dto.image.CreateImageDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_image")
public class Image implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String nameKey, url;

        public Image(CreateImageDTO createImageDTO) {
                this.nameKey = createImageDTO.nameKey();
                this.url = createImageDTO.url();
        }

}
