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


@RestController
@RequestMapping("/api/regiones")
//@Tag(name = "Region Management System")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Region> getAllRegiones(){
        return regionService.getAllRegiones();
    }

    @GetMapping("/id")
    //Operation()
    public Region getRegionById(@PathVariable Long id){
        return regionService.getRegionById(id);
    }

    @PostMapping
    //Operation()
    public Region createRegion(@RequestBody Region region){
        return regionService.saveRegion(region);
    }

    @PutMapping("/{id}")
    public Region updateRegion(@PathVariable Long id, @RequestBody Region region) {
        Region regionExistente = regionService.getRegionById(id);
        if (regionExistente != null){
            regionExistente.setNombre(region.getNombre() );
            return regionService.saveRegion(regionExistente);
        }
        return null;
    }
}
