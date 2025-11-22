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


@RestController
@RequestMapping("/api/direcciones")
//@Tag(name = "Direccion Management System")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Direccion> getAllDirecciones(){
        return direccionService.getAllDirecciones();
    }

    @GetMapping("/id")
    //Operation()
    public Direccion getDireccionById(@PathVariable Long id){
        return direccionService.getDireccionById(id);
    }

    @PostMapping
    //Operation()
    public Direccion createDireccion(@RequestBody Direccion direccion){
        return direccionService.saveDireccion(direccion);
    }

    @PutMapping("/{id}")
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
