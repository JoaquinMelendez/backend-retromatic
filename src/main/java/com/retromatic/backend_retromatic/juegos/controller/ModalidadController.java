package com.retromatic.backend_retromatic.juegos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retromatic.backend_retromatic.juegos.model.Modalidad;
import com.retromatic.backend_retromatic.juegos.service.ModalidadService;


@RestController
@RequestMapping("/api/modalidads")
//@Tag(name = "Modalidad Management System")
public class ModalidadController {

    @Autowired
    private ModalidadService modalidadService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Modalidad> getAllModalidads(){
        return modalidadService.getAllModalidades();
    }

    @GetMapping("/id")
    //Operation()
    public Modalidad getModalidadById(@PathVariable Long id){
        return modalidadService.getModalidadById(id);
    }

    @PostMapping
    //Operation()
    public Modalidad createModalidad(@RequestBody Modalidad modalidad){
        return modalidadService.saveModalidad(modalidad);
    }

    @PutMapping("/{id}")
    public Modalidad updateModalidad(@PathVariable Long id, @RequestBody Modalidad modalidad) {
        Modalidad modalidadExistente = modalidadService.getModalidadById(id);
        if (modalidadExistente != null){
            modalidadExistente.setNombre(modalidad.getNombre() );
            return modalidadService.saveModalidad(modalidadExistente);
        }
        return null;
    }
}
