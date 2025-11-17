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

import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.service.JuegoService;


@RestController
@RequestMapping("/api/juegos")
//@Tag(name = "Juego Management System")
public class JuegoController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Juego> getAllJuegos(){
        return juegoService.getAllJuegos();
    }

    @GetMapping("/id")
    //Operation()
    public Juego getJuegoById(@PathVariable Long id){
        return juegoService.getJuegoById(id);
    }

    @PostMapping
    //Operation()
    public Juego createJuego(@RequestBody Juego juego){
        return juegoService.saveJuego(juego);
    }

    @PutMapping("/{id}")
    public Juego updateJuego(@PathVariable Long id, @RequestBody Juego juego) {
        Juego juegoExistente = juegoService.getJuegoById(id);
        if (juegoExistente != null){
            juegoExistente.setTitulo(juego.getTitulo() );
            juegoExistente.setPrecio(juego.getPrecio());
            return juegoService.saveJuego(juegoExistente);
        }
        return null;
    }
}
