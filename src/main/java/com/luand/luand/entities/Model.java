package com.luand.luand.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.luand.luand.entities.dto.model.CreateModelDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_MODEL")
public class Model implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;
    private BigDecimal price;

    @OneToMany(mappedBy = "model")
    private List<FashionLine> fashionLines;

    public Model(CreateModelDTO modelDTO) {
        this.name = modelDTO.name();
        this.description = modelDTO.description();
        this.price = modelDTO.price();
    }

}
