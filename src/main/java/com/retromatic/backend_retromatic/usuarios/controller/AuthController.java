package com.retromatic.backend_retromatic.usuarios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retromatic.backend_retromatic.config.JwtUtils;
import com.retromatic.backend_retromatic.usuarios.model.LoginRequest;
import com.retromatic.backend_retromatic.usuarios.model.LoginResponse;
import com.retromatic.backend_retromatic.usuarios.model.Rol;
import com.retromatic.backend_retromatic.usuarios.model.Usuario;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ComunaRepository comunaRepository;
    private final DireccionRepository direccionRepository;
    
    // Inyecciones para seguridad
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasenna())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo());
        String token = jwtUtils.generateToken(usuario);

        LoginResponse response = new LoginResponse(
            token,
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
        if (usuarioRepository.findByCorreo(request.getCorreo()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Correo ya existe");
        }

        Rol rol = rolRepository.findByNombre("CLIENTE").orElseThrow();
        
        Usuario u = new Usuario();
        u.setNombre(request.getNombre());
        u.setApellido(request.getApellido());
        u.setCorreo(request.getCorreo());
        u.setContrasenna(request.getContrasenna());
        u.setRol(rol);
        
        Usuario guardado = usuarioRepository.save(u);


        String token = jwtUtils.generateToken(guardado);

        LoginResponse response = new LoginResponse(
            token,
            guardado.getId(),
            guardado.getNombre(),
            guardado.getApellido(),
            guardado.getCorreo(),
            guardado.getRol() != null ? guardado.getRol().getNombre() : null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}