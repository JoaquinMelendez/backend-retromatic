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


@RestController
@RequestMapping("/api/usuarios")
//@Tag(name = "Usuario Management System")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Usuario> getAllUsuarios(){
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/id")
    //Operation()
    public Usuario getUsuarioById(@PathVariable Long id){
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    //Operation()
    public Usuario createUsuario(@RequestBody Usuario usuario){
        return usuarioService.saveUsuario(usuario);
    }

    @PutMapping("/{id}")
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
