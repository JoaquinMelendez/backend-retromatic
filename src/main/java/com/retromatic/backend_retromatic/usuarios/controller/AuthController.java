package com.retromatic.backend_retromatic.usuarios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.retromatic.backend_retromatic.usuarios.model.LoginRequest;
import com.retromatic.backend_retromatic.usuarios.model.LoginResponse;
import com.retromatic.backend_retromatic.usuarios.model.Usuario;
import com.retromatic.backend_retromatic.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;

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
}
