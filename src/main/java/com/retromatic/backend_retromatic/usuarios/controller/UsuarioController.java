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

import com.retromatic.backend_retromatic.usuarios.model.Usuario;
import com.retromatic.backend_retromatic.usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/usuarios")
@Tag(
    name = "Usuarios",
    description = "Operaciones para gestionar usuarios del sistema (solo uso administrativo)"
)
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Retorna una lista con todos los usuarios registrados en el sistema."
    )
    public List<Usuario> getAllUsuarios(){
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener usuario por ID",
        description = "Retorna un usuario específico según su identificador."
    )
    public Usuario getUsuarioById(@PathVariable Long id){
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear usuario manualmente",
        description = "Crea un usuario de forma manual."
    )
    public Usuario createUsuario(@RequestBody Usuario usuario){
        return usuarioService.saveUsuario(usuario);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un usuario existente",
        description = "Modifica los datos de un usuario (nombre, apellido, correo, contraseña)."
    )
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioService.getUsuarioById(id);
        if (usuarioExistente != null){
            usuarioExistente.setNombre(usuario.getNombre() );
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setContrasenna(usuario.getContrasenna());
            usuarioExistente.setCorreo(usuario.getCorreo());
            return usuarioService.saveUsuario(usuarioExistente);
        }
        return null;
    }
}
