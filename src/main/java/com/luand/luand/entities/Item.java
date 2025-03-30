package com.luand.luand.entities;

import java.io.Serializable;

import com.luand.luand.entities.dto.item.CreateItemDTO;

import jakarta.persistence.Column;
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

    @Column(unique = true)
    private String ref;

    @ManyToOne
    private Print print;

    @ManyToOne
    private Color color;

    private int availableQuantity;

    public Item(CreateItemDTO itemDTO, Print print) {
        this.size = itemDTO.size();
        this.color = itemDTO.color();
        this.availableQuantity = itemDTO.availableQuantity();
        this.print = print;

        this.ref = new StringBuilder()
                .append(print.getModel().getRef())
                .append("-")
                .append(color.getRef())
                .append("-")
                .append(size.toString())
                .append("-")
                .append(print.getRef())
                .toString();
    }

}
