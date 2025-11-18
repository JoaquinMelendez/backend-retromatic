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

import com.retromatic.backend_retromatic.juegos.model.Plataforma;
import com.retromatic.backend_retromatic.juegos.service.PlataformaService;


@RestController
@RequestMapping("/api/plataformas")
//@Tag(name = "Plataforma Management System")
public class PlataformaController {

    @Autowired
    private PlataformaService plataformaService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Plataforma> getAllPlataformas(){
        return plataformaService.getAllPlataformas();
    }

    @GetMapping("/id")
    //Operation()
    public Plataforma getPlataformaById(@PathVariable Long id){
        return plataformaService.getPlataformaById(id);
    }

    @PostMapping
    //Operation()
    public Plataforma createPlataforma(@RequestBody Plataforma plataforma){
        return plataformaService.savePlataforma(plataforma);
    }

    @PutMapping("/{id}")
    public Plataforma updatePlataforma(@PathVariable Long id, @RequestBody Plataforma plataforma) {
        Plataforma plataformaExistente = plataformaService.getPlataformaById(id);
        if (plataformaExistente != null){
            plataformaExistente.setNombre(plataforma.getNombre() );
            return plataformaService.savePlataforma(plataformaExistente);
        }
        return null;
    }
}
