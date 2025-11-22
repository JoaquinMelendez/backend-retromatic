package com.retromatic.backend_retromatic.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.model.JuegoModalidad;

public interface JuegoModalidadRepository extends JpaRepository<JuegoModalidad, Long>{

    void deleteByJuego(Juego juego);
}
