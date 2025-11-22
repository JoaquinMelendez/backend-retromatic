package com.retromatic.backend_retromatic.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.model.JuegoCategoria;

public interface JuegoCategoriaRepository extends JpaRepository<JuegoCategoria, Long>{

    void deleteByJuego(Juego juego);

}
