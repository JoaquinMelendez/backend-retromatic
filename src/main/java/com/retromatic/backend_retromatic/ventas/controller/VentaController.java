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

import com.retromatic.backend_retromatic.ventas.model.Venta;
import com.retromatic.backend_retromatic.ventas.service.VentaService;


@RestController
@RequestMapping("/api/ventas")
//@Tag(name = "Venta Management System")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Venta> getAllVentas(){
        return ventaService.getAllVentas();
    }

    @GetMapping("/id")
    //Operation()
    public Venta getVentaById(@PathVariable Long id){
        return ventaService.getVentaById(id);
    }

    @PostMapping
    //Operation()
    public Venta createVenta(@RequestBody Venta venta){
        return ventaService.saveVenta(venta);
    }

    @PutMapping("/{id}")
    public Venta updateVenta(@PathVariable Long id, @RequestBody Venta venta) {
        Venta ventaExistente = ventaService.getVentaById(id);
        if (ventaExistente != null){
            ventaExistente.setTotal(venta.getTotal());
            ventaExistente.setFechaHora(venta.getFechaHora());
            return ventaService.saveVenta(ventaExistente);
        }
        return null;
    }
}
