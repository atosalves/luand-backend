package com.luand.luand.entities;

import java.io.Serializable;
import java.util.Set;

import com.luand.luand.entities.dto.print.CreatePrintDTO;

import jakarta.persistence.Column;
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
@Table(name = "tb_print")
public class Print implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String image;

    @ManyToOne
    private Model model;

    @ManyToOne
    private Store store;

    @OneToMany(mappedBy = "print")
    @EqualsAndHashCode.Exclude
    private Set<Item> itens;

    public Print(CreatePrintDTO print, Model model) {
        this.name = print.name();
        this.image = print.image();
        this.model = model;
    }

}
