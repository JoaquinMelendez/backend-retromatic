package com.retromatic.backend_retromatic.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retromatic.backend_retromatic.usuarios.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{

}
