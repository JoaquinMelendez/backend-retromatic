package com.retromatic.backend_retromatic.juegos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class JuegoPlataforma {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "juego_id")
    private Juego juego;

    @ManyToOne
    @JoinColumn(name = "plataforma_id")
    private Plataforma plataforma;
}
