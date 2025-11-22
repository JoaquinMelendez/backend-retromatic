package com.retromatic.backend_retromatic.usuarios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retromatic.backend_retromatic.usuarios.model.LoginRequest;
import com.retromatic.backend_retromatic.usuarios.model.LoginResponse;
import com.retromatic.backend_retromatic.usuarios.model.RegisterRequest;
import com.retromatic.backend_retromatic.usuarios.model.Rol;
import com.retromatic.backend_retromatic.usuarios.model.Usuario;
import com.retromatic.backend_retromatic.usuarios.repository.RolRepository;
import com.retromatic.backend_retromatic.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ComunaRepository comunaRepository;
    private final DireccionRepository direccionRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Correo o contraseña incorrectos");
        }

        if (!usuario.getContrasenna().equals(request.getContrasenna())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Correo o contraseña incorrectos");
        }

        LoginResponse response = new LoginResponse(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getCorreo(),
            usuario.getRol() != null ? usuario.getRol().getNombre() : null
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        Usuario existente = usuarioRepository.findByCorreo(request.getCorreo());
        if (existente != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un usuario con ese correo");
        }

        Rol rolPorDefecto = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no configurado"));

        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setApellido(request.getApellido());
        nuevo.setCorreo(request.getCorreo());
        nuevo.setContrasenna(request.getContrasenna());
        nuevo.setRol(rolPorDefecto);

        Usuario guardado = usuarioRepository.save(nuevo);

        if (request.getComunaId() != null && request.getDireccion() != null
                && !request.getDireccion().isBlank()) {

            Comuna comuna = comunaRepository.findById(request.getComunaId())
                    .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));

            Direccion dir = new Direccion();
            dir.setDescripcion(request.getDireccion());
            dir.setComuna(comuna);
            dir.setUsuario(guardado);

            direccionRepository.save(dir);
        }

        LoginResponse response = new LoginResponse(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getApellido(),
                guardado.getCorreo(),
                guardado.getRol() != null ? guardado.getRol().getNombre() : null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
