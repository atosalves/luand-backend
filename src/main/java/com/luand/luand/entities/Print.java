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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String ref;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image coverImage;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;

    @ManyToMany
    @JoinTable(name = "tb_print_colors", joinColumns = @JoinColumn(name = "print_id"), inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> colors;

    @OneToMany(mappedBy = "print")
    private Set<Item> itens;

    public Print(CreatePrintDTO print, Model model) {
        this.model = model;
        this.name = print.name();
        this.ref = print.ref();
        this.coverImage = print.coverImage();
        this.images = print.images();
        this.colors = print.colors();
    }

}
