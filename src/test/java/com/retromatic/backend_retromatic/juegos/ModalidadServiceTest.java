package com.retromatic.backend_retromatic.juegos;

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

import com.retromatic.backend_retromatic.juegos.model.Modalidad;
import com.retromatic.backend_retromatic.juegos.repository.ModalidadRepository;
import com.retromatic.backend_retromatic.juegos.service.ModalidadService;

@ExtendWith(MockitoExtension.class)
class ModalidadServiceTest {

    @InjectMocks
    private ModalidadService modalidadService;

    @Mock
    private ModalidadRepository modalidadRepository;

    private Modalidad createModalidad() {
        Modalidad modalidad = new Modalidad();
        modalidad.setId(1L);
        modalidad.setNombre("Multijugador");
        return modalidad;
    }

    @Test
    void testFindAll() {
        when(modalidadRepository.findAll()).thenReturn(List.of(createModalidad()));
        List<Modalidad> modalidades = modalidadService.getAllModalidades();

        assertNotNull(modalidades);
        assertEquals(1, modalidades.size());
        assertEquals("Multijugador", modalidades.get(0).getNombre());
    }

    @Test
    void testFindById() {
        when(modalidadRepository.findById(1L)).thenReturn(Optional.of(createModalidad()));
        Modalidad modalidad = modalidadService.getModalidadById(1L);

        assertNotNull(modalidad);
        assertEquals("Multijugador", modalidad.getNombre());
    }

    @Test
    void testSave() {
        Modalidad modalidad = createModalidad();
        when(modalidadRepository.save(any(Modalidad.class))).thenReturn(modalidad);

        Modalidad savedModalidad = modalidadService.saveModalidad(modalidad);

        assertNotNull(savedModalidad);
        assertEquals("Multijugador", savedModalidad.getNombre());
        assertEquals(1L, savedModalidad.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(modalidadRepository).deleteById(1L);

        modalidadService.deleteModalidad(1L);

        verify(modalidadRepository, times(1)).deleteById(1L);
    }
}
