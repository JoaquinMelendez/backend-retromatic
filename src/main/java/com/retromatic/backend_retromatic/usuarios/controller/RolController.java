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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/roles")
@Tag(
    name = "Roles",
    description = "Operaciones para gestionar los roles de usuario dentro del sistema"
)
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los roles",
        description = "Retorna una lista con todos los roles registrados (ADMIN, CLIENTE, etc.)."
    )
    public List<Rol> getAllRoles(){
        return rolService.getAllRoles();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener un rol por ID",
        description = "Retorna un rol específico según su identificador."
    )
    public Rol getRolById(@PathVariable Long id){
        return rolService.getRolById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo rol",
        description = "Permite registrar un nuevo rol dentro del sistema."
    )
    public Rol createRol(@RequestBody Rol rol){
        return rolService.saveRol(rol);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un rol existente",
        description = "Modifica el nombre de un rol según su ID."
    )
    public Rol updateRol(@PathVariable Long id, @RequestBody Rol rol) {
        Rol rolExistente = rolService.getRolById(id);
        if (rolExistente != null){
            rolExistente.setNombre(rol.getNombre() );
            return rolService.saveRol(rolExistente);
        }
        return null;
    }
}
