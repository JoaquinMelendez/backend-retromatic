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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("v1/api/clasificaciones")
@Tag(
    name = "Clasificaciones",
    description = "Operaciones para gestionar clasificaciones de edad de los juegos"
)
public class ClasificacionController {

    @Autowired
    private ClasificacionService clasificacionService;

    @GetMapping
    @Operation(
        summary = "Obtener todas las clasificaciones",
        description = "Retorna una lista con todas las clasificaciones registradas en el sistema."
    )
    public List<Clasificacion> getAllClasificacions(){
        return clasificacionService.getAllClasificaciones();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Buscar clasificación por ID",
        description = "Retorna una única clasificación según su identificador."
    )
    public Clasificacion getClasificacionById(@PathVariable Long id){
        return clasificacionService.getClasificacionById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva clasificación",
        description = "Permite registrar una nueva clasificación de edad en el sistema."
    )
    public Clasificacion createClasificacion(@RequestBody Clasificacion clasificacion){
        return clasificacionService.saveClasificacion(clasificacion);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar una clasificación existente",
        description = "Actualiza los datos de una clasificación ya registrada mediante su ID."
    )
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
