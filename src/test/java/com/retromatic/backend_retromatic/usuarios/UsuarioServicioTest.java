package com.retromatic.backend_retromatic.usuarios;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retromatic.backend_retromatic.usuarios.model.Rol;
import com.retromatic.backend_retromatic.usuarios.model.Usuario;
import com.retromatic.backend_retromatic.usuarios.repository.UsuarioRepository;
import com.retromatic.backend_retromatic.usuarios.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario createUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setCorreo("juan.perez@email.com");
        usuario.setContrasenna("123456");
        
        Rol rol = new Rol();
        rol.setId(2L);
        rol.setNombre("CLIENTE");
        usuario.setRol(rol);

        usuario.setDirecciones(Collections.emptyList());
        
        return usuario;
    }

    @Test
    void testGetAllUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.getAllUsuarios();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
        assertEquals("CLIENTE", usuarios.get(0).getRol().getNombre());
    }

    @Test
    void testGetUsuarioById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(createUsuario()));
        Usuario usuario = usuarioService.getUsuarioById(1L);

        assertNotNull(usuario);
        assertEquals("Juan", usuario.getNombre());
        assertEquals("juan.perez@email.com", usuario.getCorreo());
    }

    @Test
    void testSaveUsuario() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario savedUsuario = usuarioService.saveUsuario(usuario);

        assertNotNull(savedUsuario);
        assertEquals("Juan", savedUsuario.getNombre());
        assertEquals(1L, savedUsuario.getId());
    }

    @Test
    void testDeleteUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.deleteUsuario(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}