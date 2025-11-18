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

import com.retromatic.backend_retromatic.juegos.model.Clasificacion;
import com.retromatic.backend_retromatic.juegos.service.ClasificacionService;


@RestController
@RequestMapping("/api/clasificaciones")
//@Tag(name = "Clasificacion Management System")
public class ClasificacionController {

    @Autowired
    private ClasificacionService clasificacionService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Clasificacion> getAllClasificacions(){
        return clasificacionService.getAllClasificaciones();
    }

    @GetMapping("/id")
    //Operation()
    public Clasificacion getClasificacionById(@PathVariable Long id){
        return clasificacionService.getClasificacionById(id);
    }

    @PostMapping
    //Operation()
    public Clasificacion createClasificacion(@RequestBody Clasificacion clasificacion){
        return clasificacionService.saveClasificacion(clasificacion);
    }

    @PutMapping("/{id}")
    public Clasificacion updateClasificacion(@PathVariable Long id, @RequestBody Clasificacion clasificacion) {
        Clasificacion clasificacionExistente = clasificacionService.getClasificacionById(id);
        if (clasificacionExistente != null){
            clasificacionExistente.setCodigo(clasificacion.getCodigo());
            clasificacionExistente.setEdadMinima(clasificacion.getEdadMinima());
            return clasificacionService.saveClasificacion(clasificacionExistente);
        }
        return null;
    }
}
