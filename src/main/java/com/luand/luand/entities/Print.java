package com.luand.luand.entities;

import java.io.Serializable;
import java.util.Set;

import com.luand.luand.entities.dto.print.CreatePrintDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
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

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String ref;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image coverImage;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Color> colors;

    @OneToMany(mappedBy = "print")
    private Set<Item> itens;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    public Print(CreatePrintDTO print, Model model) {
        this.name = print.name();
        this.coverImage = print.coverImage();
        this.images = print.images();
        this.model = model;
    }

}
