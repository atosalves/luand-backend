package com.luand.luand.entities;

import java.io.Serializable;

import com.luand.luand.entities.dto.user.CreateUserDTO;

import jakarta.persistence.Column;
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
@Table(name = "tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private boolean isActive;

    public User(CreateUserDTO userDTO) {
        this.name = userDTO.name();
        this.password = userDTO.password();
        this.email = userDTO.email();
        this.isActive = true;
    }

}
