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

import com.retromatic.backend_retromatic.usuarios.model.Comuna;
import com.retromatic.backend_retromatic.usuarios.model.Direccion;
import com.retromatic.backend_retromatic.usuarios.repository.DireccionRepository;
import com.retromatic.backend_retromatic.usuarios.service.DireccionService;

@ExtendWith(MockitoExtension.class)
class DireccionServiceTest {

    @InjectMocks
    private DireccionService direccionService;

    @Mock
    private DireccionRepository direccionRepository;

    private Direccion createDireccion() {
        Direccion direccion = new Direccion();
        direccion.setId(1L);
        direccion.setDireccion("Avenida Siempre Viva");
        direccion.setEnumeracion(742);

        // Simulamos una Comuna para completar el objeto
        Comuna comuna = new Comuna();
        comuna.setId(1L);
        comuna.setNombre("Springfield");
        direccion.setComuna(comuna);

        return direccion;
    }

    @Test
    void testGetAllDirecciones() {
        when(direccionRepository.findAll()).thenReturn(List.of(createDireccion()));
        List<Direccion> direcciones = direccionService.getAllDirecciones();

        assertNotNull(direcciones);
        assertEquals(1, direcciones.size());
        assertEquals("Avenida Siempre Viva", direcciones.get(0).getDireccion());
    }

    @Test
    void testGetDireccionById() {
        when(direccionRepository.findById(1L)).thenReturn(Optional.of(createDireccion()));
        Direccion direccion = direccionService.getDireccionById(1L);

        assertNotNull(direccion);
        assertEquals("Avenida Siempre Viva", direccion.getDireccion());
        assertEquals(742, direccion.getEnumeracion());
    }

    @Test
    void testSaveDireccion() {
        Direccion direccion = createDireccion();
        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccion);

        Direccion savedDireccion = direccionService.saveDireccion(direccion);

        assertNotNull(savedDireccion);
        assertEquals("Avenida Siempre Viva", savedDireccion.getDireccion());
        assertEquals(1L, savedDireccion.getId());
    }

    @Test
    void testDeleteDireccion() {
        doNothing().when(direccionRepository).deleteById(1L);

        direccionService.deleteDireccion(1L);

        verify(direccionRepository, times(1)).deleteById(1L);
    }
}