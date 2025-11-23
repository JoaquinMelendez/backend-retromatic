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

import com.retromatic.backend_retromatic.usuarios.model.Direccion;
import com.retromatic.backend_retromatic.usuarios.service.DireccionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/direcciones")
@Tag(
    name = "Direcciones",
    description = "Operaciones para gestionar direcciones asociadas a los usuarios"
)
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping
    @Operation(
        summary = "Obtener todas las direcciones",
        description = "Retorna una lista con todas las direcciones registradas en el sistema."
    )
    public List<Direccion> getAllDirecciones(){
        return direccionService.getAllDirecciones();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener dirección por ID",
        description = "Retorna una dirección específica según su identificador."
    )
    public Direccion getDireccionById(@PathVariable Long id){
        return direccionService.getDireccionById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva dirección",
        description = "Registra una nueva dirección que puede ser asociada a un usuario."
    )
    public Direccion createDireccion(@RequestBody Direccion direccion){
        return direccionService.saveDireccion(direccion);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar dirección existente",
        description = "Modifica los datos de una dirección ya registrada mediante su ID."
    )
    public Direccion updateDireccion(@PathVariable Long id, @RequestBody Direccion direccion) {
        Direccion direccionExistente = direccionService.getDireccionById(id);
        if (direccionExistente != null){
            direccionExistente.setDireccion(direccion.getDireccion() );
            direccionExistente.setEnumeracion(direccion.getEnumeracion());
            return direccionService.saveDireccion(direccionExistente);
        }
        return null;
    }
}
