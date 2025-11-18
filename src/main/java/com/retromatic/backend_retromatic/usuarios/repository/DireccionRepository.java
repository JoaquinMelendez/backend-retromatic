package com.retromatic.backend_retromatic.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retromatic.backend_retromatic.usuarios.model.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Long>{

}
