package com.retromatic.backend_retromatic.juegos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.model.JuegoRequest;
import com.retromatic.backend_retromatic.juegos.service.JuegoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/juegos")
@RequiredArgsConstructor
public class JuegoController {

    private final JuegoService juegoService;

    @GetMapping
    public ResponseEntity<List<Juego>> getAllJuegos() {
        List<Juego> juegos = juegoService.getAllJuegos();
        return ResponseEntity.ok(juegos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Juego> getJuegoById(@PathVariable Long id) {
        Juego juego = juegoService.getJuegoById(id);
        if (juego == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(juego);
    }

    @PostMapping
    public ResponseEntity<Juego> crearJuego(@RequestBody JuegoRequest request) {
        Juego creado = juegoService.crearJuego(request);
        return ResponseEntity.status(201).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Juego> actualizarJuego(
            @PathVariable Long id,
            @RequestBody JuegoRequest request
    ) {
        Juego actualizado = juegoService.actualizarJuego(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJuego(@PathVariable Long id) {
        juegoService.deleteJuego(id);
        return ResponseEntity.noContent().build();
    }
}

