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


@RestController
@RequestMapping("/api/ventaJuegos")
//@Tag(name = "VentaJuego Management System")
public class VentaJuegoController {

    @Autowired
    private VentaJuegoService ventaJuegoService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<VentaJuego> getAllVentaJuegos(){
        return ventaJuegoService.getAllVentaJuegos();
    }

    @GetMapping("/id")
    //Operation()
    public VentaJuego getVentaJuegoById(@PathVariable Long id){
        return ventaJuegoService.getVentaJuegoById(id);
    }

    @PostMapping
    //Operation()
    public VentaJuego createVentaJuego(@RequestBody VentaJuego ventaJuego){
        return ventaJuegoService.saveVentaJuego(ventaJuego);
    }

    @PutMapping("/{id}")
    public VentaJuego updateVentaJuego(@PathVariable Long id, @RequestBody VentaJuego ventaJuego) {
        VentaJuego ventaJuegoExistente = ventaJuegoService.getVentaJuegoById(id);
        if (ventaJuegoExistente != null){
            ventaJuegoExistente.setNombre(ventaJuego.getNombre() );
            return ventaJuegoService.saveVentaJuego(ventaJuegoExistente);
        }
        return null;
    }
}
