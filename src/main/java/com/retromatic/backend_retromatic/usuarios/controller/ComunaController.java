package com.retromatic.backend_retromatic.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retromatic.backend_retromatic.usuarios.model.Comuna;
import com.retromatic.backend_retromatic.usuarios.service.ComunaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/comunas")
@Tag(
    name = "Comunas",
    description = "Operaciones para gestionar comunas asociadas a direcciones de usuarios"
)
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    @Operation(
        summary = "Obtener todas las comunas",
        description = "Retorna una lista con todas las comunas registradas en el sistema."
    )
    public List<Comuna> getAllComunas(){
        return comunaService.getAllComunas();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener comuna por ID",
        description = "Retorna una comuna específica según su identificador."
    )
    public Comuna getComunaById(@PathVariable Long id){
        return comunaService.getComunaById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva comuna",
        description = "Permite registrar una nueva comuna en el sistema."
    )
    public Comuna createComuna(@RequestBody Comuna comuna){
        return comunaService.saveComuna(comuna);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar una comuna existente",
        description = "Modifica los datos de una comuna ya registrada mediante su ID."
    )
    public Comuna updateComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna comunaExistente = comunaService.getComunaById(id);
        if (comunaExistente != null){
            comunaExistente.setNombre(comuna.getNombre() );
            return comunaService.saveComuna(comunaExistente);
        }
        return null;
    }
}
