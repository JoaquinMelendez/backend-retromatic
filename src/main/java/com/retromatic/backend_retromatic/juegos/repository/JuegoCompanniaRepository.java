package com.retromatic.backend_retromatic.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.model.JuegoCompannia;

public interface JuegoCompanniaRepository extends JpaRepository<JuegoCompannia, Long>{

    void deleteByJuego(Juego juego);
}
