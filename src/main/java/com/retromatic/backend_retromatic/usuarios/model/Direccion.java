package com.retromatic.backend_retromatic.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String direccion;

    @Column
    private Integer enumeracion;

    @Column
    private String depto;

    @ManyToOne
    @JoinColumn(name = "comuna_id")
    private Comuna comuna;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
