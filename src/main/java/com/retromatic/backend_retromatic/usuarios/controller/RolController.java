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

import com.retromatic.backend_retromatic.usuarios.model.Rol;
import com.retromatic.backend_retromatic.usuarios.service.RolService;


@RestController
@RequestMapping("/api/roles")
//@Tag(name = "Rol Management System")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Rol> getAllRoles(){
        return rolService.getAllRoles();
    }

    @GetMapping("/id")
    //Operation()
    public Rol getRolById(@PathVariable Long id){
        return rolService.getRolById(id);
    }

    @PostMapping
    //Operation()
    public Rol createRol(@RequestBody Rol rol){
        return rolService.saveRol(rol);
    }

    @PutMapping("/{id}")
    public Rol updateRol(@PathVariable Long id, @RequestBody Rol rol) {
        Rol rolExistente = rolService.getRolById(id);
        if (rolExistente != null){
            rolExistente.setNombre(rol.getNombre() );
            return rolService.saveRol(rolExistente);
        }
        return null;
    }
}
