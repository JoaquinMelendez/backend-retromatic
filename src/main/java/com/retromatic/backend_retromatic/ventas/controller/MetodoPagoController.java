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

import com.retromatic.backend_retromatic.ventas.model.MetodoPago;
import com.retromatic.backend_retromatic.ventas.service.MetodoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/metodoPagos")
@Tag(
    name = "Métodos de pago",
    description = "Operaciones para gestionar los métodos de pago disponibles en el sistema"
)
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los métodos de pago",
        description = "Retorna una lista con todos los métodos de pago registrados (ej: Tarjeta, Transferencia, etc.)."
    )
    public List<MetodoPago> getAllMetodoPagos(){
        return metodoPagoService.getAllMetodoPagos();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener método de pago por ID",
        description = "Retorna un método de pago específico según su identificador."
    )
    public MetodoPago getMetodoPagoById(@PathVariable Long id){
        return metodoPagoService.getMetodoPagoById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo método de pago",
        description = "Permite registrar un nuevo método de pago en el sistema."
    )
    public MetodoPago createMetodoPago(@RequestBody MetodoPago metodoPago){
        return metodoPagoService.saveMetodoPago(metodoPago);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un método de pago existente",
        description = "Modifica el nombre de un método de pago según su ID."
    )
    public MetodoPago updateMetodoPago(@PathVariable Long id, @RequestBody MetodoPago metodoPago) {
        MetodoPago metodoPagoExistente = metodoPagoService.getMetodoPagoById(id);
        if (metodoPagoExistente != null){
            metodoPagoExistente.setNombre(metodoPago.getNombre() );
            return metodoPagoService.saveMetodoPago(metodoPagoExistente);
        }
        return null;
    }
}
