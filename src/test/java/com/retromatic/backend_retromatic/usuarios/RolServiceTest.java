package com.retromatic.backend_retromatic.usuarios;

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
import com.retromatic.backend_retromatic.usuarios.repository.RolRepository;
import com.retromatic.backend_retromatic.usuarios.service.RolService;

@ExtendWith(MockitoExtension.class)
class RolServiceTest {

    @InjectMocks
    private RolService rolService;

    @Mock
    private RolRepository rolRepository;

    private Rol createRol() {
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("ADMIN");
        return rol;
    }

    @Test
    void testGetAllRoles() {
        when(rolRepository.findAll()).thenReturn(List.of(createRol()));
        List<Rol> roles = rolService.getAllRoles();

        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("ADMIN", roles.get(0).getNombre());
    }

    @Test
    void testGetRolById() {
        when(rolRepository.findById(1L)).thenReturn(Optional.of(createRol()));
        Rol rol = rolService.getRolById(1L);

        assertNotNull(rol);
        assertEquals("ADMIN", rol.getNombre());
    }

    @Test
    void testSaveRol() {
        Rol rol = createRol();
        when(rolRepository.save(any(Rol.class))).thenReturn(rol);

        Rol savedRol = rolService.saveRol(rol);

        assertNotNull(savedRol);
        assertEquals("ADMIN", savedRol.getNombre());
        assertEquals(1L, savedRol.getId());
    }

    @Test
    void testDeleteRol() {
        doNothing().when(rolRepository).deleteById(1L);

        rolService.deleteRol(1L);

        verify(rolRepository, times(1)).deleteById(1L);
    }
}