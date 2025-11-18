package com.retromatic.backend_retromatic.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retromatic.backend_retromatic.juegos.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
