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

import com.retromatic.backend_retromatic.usuarios.model.Comuna;
import com.retromatic.backend_retromatic.usuarios.service.ComunaService;


@RestController
@RequestMapping("/api/comunas")
//@Tag(name = "Comuna Management System")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Comuna> getAllComunas(){
        return comunaService.getAllComunas();
    }

    @GetMapping("/id")
    //Operation()
    public Comuna getComunaById(@PathVariable Long id){
        return comunaService.getComunaById(id);
    }

    @PostMapping
    //Operation()
    public Comuna createComuna(@RequestBody Comuna comuna){
        return comunaService.saveComuna(comuna);
    }

    @PutMapping("/{id}")
    public Comuna updateComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna comunaExistente = comunaService.getComunaById(id);
        if (comunaExistente != null){
            comunaExistente.setNombre(comuna.getNombre() );
            return comunaService.saveComuna(comunaExistente);
        }
        return null;
    }
}
