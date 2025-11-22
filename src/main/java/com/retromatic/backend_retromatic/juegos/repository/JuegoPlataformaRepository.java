package com.retromatic.backend_retromatic.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.model.JuegoPlataforma;

public interface JuegoPlataformaRepository extends JpaRepository<JuegoPlataforma, Long>{

    void deleteByJuego(Juego juego);
}
