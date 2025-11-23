package com.retromatic.backend_retromatic.juegos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.retromatic.backend_retromatic.juegos.model.Juego;

public interface JuegoRepository extends JpaRepository<Juego, Long>{
    @Query("SELECT DISTINCT jc.juego FROM JuegoCompannia jc WHERE jc.compannia.id = :companniaId")
    List<Juego> findByCompanniaId(@Param("companniaId") Long companniaId);

    @Query("SELECT DISTINCT jp.juego FROM JuegoPlataforma jp WHERE jp.plataforma.id = :plataformaId")
    List<Juego> findByPlataformaId(@Param("plataformaId") Long plataformaId);

    @Query("SELECT DISTINCT jm.juego FROM JuegoModalidad jm WHERE jm.modalidad.id = :modalidadId")
    List<Juego> findByModalidadId(@Param("modalidadId") Long modalidadId);

    @Query("SELECT DISTINCT jcat.juego FROM JuegoCategoria jcat WHERE jcat.categoria.id = :categoriaId")
    List<Juego> findByCategoriaId(@Param("categoriaId") Long categoriaId);

    @Query("SELECT j FROM Juego j WHERE j.clasificacion.id = :clasificacionId")
    List<Juego> findByClasificacionId(@Param("clasificacionId") Long clasificacionId);
}
