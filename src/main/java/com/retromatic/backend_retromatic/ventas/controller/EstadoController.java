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


@RestController
@RequestMapping("/api/estados")
//@Tag(name = "Estado Management System")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Estado> getAllEstados(){
        return estadoService.getAllEstados();
    }

    @GetMapping("/id")
    //Operation()
    public Estado getEstadoById(@PathVariable Long id){
        return estadoService.getEstadoById(id);
    }

    @PostMapping
    //Operation()
    public Estado createEstado(@RequestBody Estado estado){
        return estadoService.saveEstado(estado);
    }

    @PutMapping("/{id}")
    public Estado updateEstado(@PathVariable Long id, @RequestBody Estado estado) {
        Estado estadoExistente = estadoService.getEstadoById(id);
        if (estadoExistente != null){
            estadoExistente.setNombre(estado.getNombre() );
            return estadoService.saveEstado(estadoExistente);
        }
        return null;
    }
}
