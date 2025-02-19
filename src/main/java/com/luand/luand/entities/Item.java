package com.luand.luand.entities;

import java.io.Serializable;

import com.luand.luand.entities.dto.item.CreateItemDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Size size;

    private String color;

    private int availableQuantity;

    @ManyToOne
    private Print print;

    public Item(CreateItemDTO itemDTO, Print print) {
        this.size = itemDTO.size();
        this.color = itemDTO.color();
        this.availableQuantity = itemDTO.availableQuantity();
        this.print = print;
    }

}
