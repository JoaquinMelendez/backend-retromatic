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


@RestController
@RequestMapping("/api/metodoPagos")
//@Tag(name = "MetodoPago Management System")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<MetodoPago> getAllMetodoPagos(){
        return metodoPagoService.getAllMetodoPagos();
    }

    @GetMapping("/id")
    //Operation()
    public MetodoPago getMetodoPagoById(@PathVariable Long id){
        return metodoPagoService.getMetodoPagoById(id);
    }

    @PostMapping
    //Operation()
    public MetodoPago createMetodoPago(@RequestBody MetodoPago metodoPago){
        return metodoPagoService.saveMetodoPago(metodoPago);
    }

    @PutMapping("/{id}")
    public MetodoPago updateMetodoPago(@PathVariable Long id, @RequestBody MetodoPago metodoPago) {
        MetodoPago metodoPagoExistente = metodoPagoService.getMetodoPagoById(id);
        if (metodoPagoExistente != null){
            metodoPagoExistente.setNombre(metodoPago.getNombre() );
            return metodoPagoService.saveMetodoPago(metodoPagoExistente);
        }
        return null;
    }
}
