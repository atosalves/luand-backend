package com.luand.luand.entities;

import java.io.Serializable;
import java.util.Set;

import com.luand.luand.entities.dto.store.CreateStoreDTO;

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
<<<<<<< HEAD
@Table(name = "tb_store")
=======
@Table(name = "TB_STORE")
>>>>>>> develop
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "store")
    private Set<User> users;

    @OneToMany(mappedBy = "store")
    private Set<FashionLine> fashionLines;

<<<<<<< HEAD
    @OneToMany(mappedBy = "store")
    private Set<Order> orders;

=======
>>>>>>> develop
    public Store(CreateStoreDTO storeDTO) {
        this.name = storeDTO.name();
        this.description = storeDTO.description();
    }

}
