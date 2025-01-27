package com.luand.luand.entities;

import java.io.Serializable;
import java.util.Set;

import com.luand.luand.entities.dto.fashionLine.CreateFashionLineDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_FASHION_LINE")
public class FashionLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String print;

    @ManyToOne
    private Model model;

    @ManyToOne
    private Store store;

    @OneToMany(mappedBy = "fashionLine")
    private Set<Item> itens;

    public FashionLine(CreateFashionLineDTO fashionLineDTO, Model model) {
        this.name = fashionLineDTO.name();
        this.print = fashionLineDTO.print();
        this.model = model;
    }

}
