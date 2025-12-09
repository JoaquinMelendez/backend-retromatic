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

import com.retromatic.backend_retromatic.juegos.model.Clasificacion;
import com.retromatic.backend_retromatic.juegos.repository.ClasificacionRepository;
import com.retromatic.backend_retromatic.juegos.service.ClasificacionService;

@ExtendWith(MockitoExtension.class)
public class ClasificacionServiceTest {

    @InjectMocks
    private ClasificacionService clasificacionService;

    @Mock
    private ClasificacionRepository clasificacionRepository;

    private Clasificacion createClasificacion() {
        Clasificacion clasificacion = new Clasificacion();
        clasificacion.setId(1L);
        clasificacion.setCodigo("TE"); // Todo Espectador
        clasificacion.setEdadMinima(0);
        return clasificacion;
    }

    @Test
    void testFindAll() {
        when(clasificacionRepository.findAll()).thenReturn(List.of(createClasificacion()));
        List<Clasificacion> clasificaciones = clasificacionService.getAllClasificaciones();

        assertNotNull(clasificaciones);
        assertEquals(1, clasificaciones.size());
        assertEquals("TE", clasificaciones.get(0).getCodigo());
    }

    @Test
    void testFindById() {
        when(clasificacionRepository.findById(1L)).thenReturn(Optional.of(createClasificacion()));
        Clasificacion clasificacion = clasificacionService.getClasificacionById(1L);

        assertNotNull(clasificacion);
        assertEquals("TE", clasificacion.getCodigo());
        assertEquals(0, clasificacion.getEdadMinima());
    }

    @Test
    void testSave() {
        Clasificacion clasificacion = createClasificacion();
        when(clasificacionRepository.save(any(Clasificacion.class))).thenReturn(clasificacion);

        Clasificacion savedClasificacion = clasificacionService.saveClasificacion(clasificacion);

        assertNotNull(savedClasificacion);
        assertEquals("TE", savedClasificacion.getCodigo());
        assertEquals(1L, savedClasificacion.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(clasificacionRepository).deleteById(1L);

        clasificacionService.deleteClasificacion(1L);

        verify(clasificacionRepository, times(1)).deleteById(1L);
    }
}