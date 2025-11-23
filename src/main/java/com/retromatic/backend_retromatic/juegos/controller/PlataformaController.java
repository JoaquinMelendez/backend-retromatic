package com.retromatic.backend_retromatic.juegos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retromatic.backend_retromatic.juegos.model.Plataforma;
import com.retromatic.backend_retromatic.juegos.service.PlataformaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/plataformas")
@Tag(
    name = "Plataformas",
    description = "Operaciones para gestionar las plataformas de juego (PC, PS5, Xbox, etc.)"
)
public class PlataformaController {

    @Autowired
    private PlataformaService plataformaService;

    @GetMapping
    @Operation(
        summary = "Obtener todas las plataformas",
        description = "Retorna una lista con todas las plataformas registradas en el sistema."
    )
    public List<Plataforma> getAllPlataformas(){
        return plataformaService.getAllPlataformas();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener plataforma por ID",
        description = "Retorna una plataforma específica según su identificador."
    )
    public Plataforma getPlataformaById(@PathVariable Long id){
        return plataformaService.getPlataformaById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva plataforma",
        description = "Permite registrar una nueva plataforma de juego en el sistema."
    )
    public Plataforma createPlataforma(@RequestBody Plataforma plataforma){
        return plataformaService.savePlataforma(plataforma);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar una plataforma existente",
        description = "Modifica los datos de una plataforma registrada mediante su ID."
    )
    public Plataforma updatePlataforma(@PathVariable Long id, @RequestBody Plataforma plataforma) {
        Plataforma plataformaExistente = plataformaService.getPlataformaById(id);
        if (plataformaExistente != null){
            plataformaExistente.setNombre(plataforma.getNombre() );
            return plataformaService.savePlataforma(plataformaExistente);
        }
        return null;
    }
}
