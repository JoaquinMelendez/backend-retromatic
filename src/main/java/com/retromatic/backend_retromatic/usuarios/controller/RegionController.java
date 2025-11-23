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

import com.retromatic.backend_retromatic.usuarios.model.Region;
import com.retromatic.backend_retromatic.usuarios.service.RegionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/regiones")
@Tag(
    name = "Regiones",
    description = "Operaciones para gestionar regiones dentro del sistema"
)
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    @Operation(
        summary = "Obtener todas las regiones",
        description = "Retorna una lista con todas las regiones registradas."
    )
    public List<Region> getAllRegiones(){
        return regionService.getAllRegiones();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener región por ID",
        description = "Retorna una región específica según su identificador."
    )
    public Region getRegionById(@PathVariable Long id){
        return regionService.getRegionById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva región",
        description = "Permite registrar una nueva región en el sistema."
    )
    public Region createRegion(@RequestBody Region region){
        return regionService.saveRegion(region);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar región existente",
        description = "Modifica los datos de una región registrada mediante su ID."
    )
    public Region updateRegion(@PathVariable Long id, @RequestBody Region region) {
        Region regionExistente = regionService.getRegionById(id);
        if (regionExistente != null){
            regionExistente.setNombre(region.getNombre() );
            return regionService.saveRegion(regionExistente);
        }
        return null;
    }
}
