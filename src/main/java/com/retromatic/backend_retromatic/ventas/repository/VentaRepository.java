package com.retromatic.backend_retromatic.ventas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retromatic.backend_retromatic.ventas.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{

    Optional<Venta> findByUsuarioIdAndEstadoNombre(Long usuarioId, String nombreEstado);
    List<Venta> findByEstadoNombre(String estadoNombre);


}
