package com.retromatic.backend_retromatic.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retromatic.backend_retromatic.ventas.model.VentaJuego;

@Repository
public interface VentaJuegoRepository extends JpaRepository<VentaJuego, Long>{

}
