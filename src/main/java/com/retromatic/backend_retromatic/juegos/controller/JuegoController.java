package com.retromatic.backend_retromatic.juegos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.model.JuegoRequest;
import com.retromatic.backend_retromatic.juegos.service.JuegoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/juegos")
@RequiredArgsConstructor
@Tag(
    name = "Juegos",
    description = "Operaciones para gestionar el catálogo de juegos de Retromatic"
)
public class JuegoController {

    private final JuegoService juegoService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los juegos",
        description = "Retorna una lista con todos los juegos disponibles en el catálogo."
    )
    public ResponseEntity<List<Juego>> getAllJuegos() {
        List<Juego> juegos = juegoService.getAllJuegos();
        return ResponseEntity.ok(juegos);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener juego por ID",
        description = "Retorna la información de un juego específico según su identificador."
    )
    public ResponseEntity<Juego> getJuegoById(@PathVariable Long id) {
        Juego juego = juegoService.getJuegoById(id);
        if (juego == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(juego);
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo juego",
        description = "Permite registrar un nuevo juego en el catálogo con sus datos asociados."
    )
    public ResponseEntity<Juego> crearJuego(@RequestBody JuegoRequest request) {
        Juego creado = juegoService.crearJuego(request);
        return ResponseEntity.status(201).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un juego existente",
        description = "Actualiza los datos de un juego ya registrado mediante su ID."
    )
    public ResponseEntity<Juego> actualizarJuego(
            @PathVariable Long id,
            @RequestBody JuegoRequest request
    ) {
        Juego actualizado = juegoService.actualizarJuego(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar un juego",
        description = "Elimina un juego del catálogo según su identificador."
    )
    public ResponseEntity<Void> deleteJuego(@PathVariable Long id) {
        juegoService.deleteJuego(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar")
    @Operation(
        summary = "Filtrar juegos por distintos criterios",
        description = "Permite filtrar juegos por compañía, plataforma, modalidad, categoría y clasificación."
    )
    public List<Juego> filtrarJuegos(
            @RequestParam(required = false) Long companniaId,
            @RequestParam(required = false) Long plataformaId,
            @RequestParam(required = false) Long modalidadId,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Long clasificacionId
    ) {
        return juegoService.filtrarJuegos(
                companniaId,
                plataformaId,
                modalidadId,
                categoriaId,
                clasificacionId
        );
    }
}

