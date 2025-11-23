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

import com.retromatic.backend_retromatic.juegos.model.Modalidad;
import com.retromatic.backend_retromatic.juegos.service.ModalidadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/modalidades")
@Tag(
    name = "Modalidades",
    description = "Operaciones para gestionar las modalidades de juego (Singleplayer, Multiplayer, etc.)"
)
public class ModalidadController {

    @Autowired
    private ModalidadService modalidadService;

    @GetMapping
    @Operation(
        summary = "Obtener todas las modalidades",
        description = "Retorna una lista con todas las modalidades de juego registradas."
    )
    public List<Modalidad> getAllModalidads(){
        return modalidadService.getAllModalidades();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener modalidad por ID",
        description = "Retorna una modalidad específica según su identificador."
    )
    public Modalidad getModalidadById(@PathVariable Long id){
        return modalidadService.getModalidadById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva modalidad",
        description = "Permite agregar una nueva modalidad de juego al sistema."
    )
    public Modalidad createModalidad(@RequestBody Modalidad modalidad){
        return modalidadService.saveModalidad(modalidad);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar una modalidad existente",
        description = "Modifica una modalidad registrada mediante su ID."
    )
    public Modalidad updateModalidad(@PathVariable Long id, @RequestBody Modalidad modalidad) {
        Modalidad modalidadExistente = modalidadService.getModalidadById(id);
        if (modalidadExistente != null){
            modalidadExistente.setNombre(modalidad.getNombre() );
            return modalidadService.saveModalidad(modalidadExistente);
        }
        return null;
    }
}
