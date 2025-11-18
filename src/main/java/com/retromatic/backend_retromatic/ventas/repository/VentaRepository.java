package com.retromatic.backend_retromatic.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retromatic.backend_retromatic.ventas.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long>{

}
