package com.retromatic.backend_retromatic.ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retromatic.backend_retromatic.ventas.model.Estado;
import com.retromatic.backend_retromatic.ventas.service.EstadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/estados")
@Tag(
    name = "Estados de Venta",
    description = "Gestiona los estados posibles de una venta (PENDIENTE, PAGADO, CANCELADO, etc.)"
)
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los estados",
        description = "Retorna una lista con todos los estados registrados en el sistema."
    )
    public List<Estado> getAllEstados(){
        return estadoService.getAllEstados();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener un estado por ID",
        description = "Retorna un estado específico según su identificador."
    )
    public Estado getEstadoById(@PathVariable Long id){
        return estadoService.getEstadoById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo estado",
        description = "Permite registrar un estado adicional para el flujo de ventas."
    )
    public Estado createEstado(@RequestBody Estado estado){
        return estadoService.saveEstado(estado);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un estado existente",
        description = "Actualiza el nombre de un estado según su ID."
    )
    public Estado updateEstado(@PathVariable Long id, @RequestBody Estado estado) {
        Estado estadoExistente = estadoService.getEstadoById(id);
        if (estadoExistente != null){
            estadoExistente.setNombre(estado.getNombre() );
            return estadoService.saveEstado(estadoExistente);
        }
        return null;
    }
}
