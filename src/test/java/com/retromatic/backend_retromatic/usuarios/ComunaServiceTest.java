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
import com.retromatic.backend_retromatic.usuarios.model.Region;
import com.retromatic.backend_retromatic.usuarios.repository.ComunaRepository;
import com.retromatic.backend_retromatic.usuarios.service.ComunaService;

@ExtendWith(MockitoExtension.class)
class ComunaServiceTest {

    @InjectMocks
    private ComunaService comunaService;

    @Mock
    private ComunaRepository comunaRepository;

    private Comuna createComuna() {
        Comuna comuna = new Comuna();
        comuna.setId(1L);
        comuna.setNombre("Providencia");
        
        // Simulamos una región para que el objeto esté completo
        Region region = new Region();
        region.setId(1L);
        region.setNombre("Metropolitana");
        comuna.setRegion(region);
        
        return comuna;
    }

    @Test
    void testGetAllComunas() {
        when(comunaRepository.findAll()).thenReturn(List.of(createComuna()));
        List<Comuna> comunas = comunaService.getAllComunas();

        assertNotNull(comunas);
        assertEquals(1, comunas.size());
        assertEquals("Providencia", comunas.get(0).getNombre());
    }

    @Test
    void testGetComunaById() {
        when(comunaRepository.findById(1L)).thenReturn(Optional.of(createComuna()));
        Comuna comuna = comunaService.getComunaById(1L);

        assertNotNull(comuna);
        assertEquals("Providencia", comuna.getNombre());
        assertEquals("Metropolitana", comuna.getRegion().getNombre());
    }

    @Test
    void testSaveComuna() {
        Comuna comuna = createComuna();
        when(comunaRepository.save(any(Comuna.class))).thenReturn(comuna);

        Comuna savedComuna = comunaService.saveComuna(comuna);

        assertNotNull(savedComuna);
        assertEquals("Providencia", savedComuna.getNombre());
        assertEquals(1L, savedComuna.getId());
    }

    @Test
    void testDeleteComuna() {
        doNothing().when(comunaRepository).deleteById(1L);

        comunaService.deleteComuna(1L);

        verify(comunaRepository, times(1)).deleteById(1L);
    }
}