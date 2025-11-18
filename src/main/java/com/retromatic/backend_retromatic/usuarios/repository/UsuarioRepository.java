package com.retromatic.backend_retromatic.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retromatic.backend_retromatic.usuarios.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
