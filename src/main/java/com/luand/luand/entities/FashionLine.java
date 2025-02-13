package com.luand.luand.entities;

import java.io.Serializable;
import java.util.Set;

import com.luand.luand.entities.dto.fashionLine.CreateFashionLineDTO;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_fashion_line")
public class FashionLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String print;

    @ManyToOne
    private Model model;

    @ElementCollection
    private Set<String> colorsDistinct;
    @ElementCollection
    private Set<Size> sizesDistinct;

    @OneToMany(mappedBy = "fashionLine")
    @EqualsAndHashCode.Exclude
    private Set<Item> itens;

    public FashionLine(CreateFashionLineDTO fashionLineDTO, Model model) {
        this.name = fashionLineDTO.name();
        this.print = fashionLineDTO.print();
        this.model = model;
    }

}
