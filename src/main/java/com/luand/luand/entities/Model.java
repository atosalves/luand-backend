package com.luand.luand.entities;

import com.luand.luand.converter.SizeSetConverter;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import java.math.BigDecimal;
import java.util.Set;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "tb_model")
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String ref;

    @Convert(converter = SizeSetConverter.class)
    private Set<Size> supportedSizes;

    private BigDecimal price;

    private String description;

    public Model(CreateModelDTO modelDTO) {
        this.name = modelDTO.name();
        this.ref = modelDTO.ref();
        this.description = modelDTO.description();
        this.price = modelDTO.price();
        this.supportedSizes = modelDTO.supportedSizes();
    }

}
