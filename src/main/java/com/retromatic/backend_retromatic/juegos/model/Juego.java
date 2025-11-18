package com.retromatic.backend_retromatic.juegos.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Juego {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column
    private Integer precio;

    //Relaciones con tablas intermedias
    @OneToMany(mappedBy="juego")
    private List<JuegoCategoria> categorias;

    @OneToMany(mappedBy="juego")
    private List<JuegoCompannia> compannias;

    @OneToMany(mappedBy="juego")
    private List<JuegoPlataforma> plataformas;

    @OneToMany(mappedBy="juego")
    private List<JuegoModalidad> modalidades;

    //Relaciones sin tablas intermedias
    @ManyToOne
    @JoinColumn(name = "clasificacion_id")
    private Clasificacion clasificacion;
}
