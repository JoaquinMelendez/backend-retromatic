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

import com.retromatic.backend_retromatic.ventas.model.VentaJuego;
import com.retromatic.backend_retromatic.ventas.service.VentaJuegoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/ventaJuegos")
@Tag(
    name = "VentaJuego",
    description = "Operaciones CRUD para los ítems individuales dentro de una venta"
)
public class VentaJuegoController {

    @Autowired
    private VentaJuegoService ventaJuegoService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los ítems de venta",
        description = "Retorna una lista completa de todos los registros de VentaJuego."
    )
    public List<VentaJuego> getAllVentaJuegos(){
        return ventaJuegoService.getAllVentaJuegos();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener un ítem de venta por ID",
        description = "Retorna un registro VentaJuego específico según su identificador."
    )
    public VentaJuego getVentaJuegoById(@PathVariable Long id){
        return ventaJuegoService.getVentaJuegoById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear un ítem de venta",
        description = "Crea manualmente un ítem VentaJuego (normalmente lo hace el carrito)."
    )
    public VentaJuego createVentaJuego(@RequestBody VentaJuego ventaJuego){
        return ventaJuegoService.saveVentaJuego(ventaJuego);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un ítem de venta",
        description = "Actualiza un registro VentaJuego."
    )
    public VentaJuego updateVentaJuego(@PathVariable Long id, @RequestBody VentaJuego ventaJuego) {
        VentaJuego ventaJuegoExistente = ventaJuegoService.getVentaJuegoById(id);
        if (ventaJuegoExistente != null){
            ventaJuegoExistente.setNombre(ventaJuego.getNombre() );
            return ventaJuegoService.saveVentaJuego(ventaJuegoExistente);
        }
        return null;
    }
}
