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

import com.retromatic.backend_retromatic.juegos.model.Plataforma;
import com.retromatic.backend_retromatic.juegos.repository.PlataformaRepository;
import com.retromatic.backend_retromatic.juegos.service.PlataformaService;

@ExtendWith(MockitoExtension.class)
class PlataformaServiceTest {

    @InjectMocks
    private PlataformaService plataformaService;

    @Mock
    private PlataformaRepository plataformaRepository;

    private Plataforma createPlataforma() {
        Plataforma plataforma = new Plataforma();
        plataforma.setId(1L);
        plataforma.setNombre("PlayStation 4");
        return plataforma;
    }

    @Test
    void testFindAll() {
        when(plataformaRepository.findAll()).thenReturn(List.of(createPlataforma()));
        List<Plataforma> plataformas = plataformaService.getAllPlataformas();

        assertNotNull(plataformas);
        assertEquals(1, plataformas.size());
        assertEquals("PlayStation 4", plataformas.get(0).getNombre());
    }

    @Test
    void testFindById() {
        when(plataformaRepository.findById(1L)).thenReturn(Optional.of(createPlataforma()));
        Plataforma plataforma = plataformaService.getPlataformaById(1L);

        assertNotNull(plataforma);
        assertEquals("PlayStation 4", plataforma.getNombre());
    }

    @Test
    void testSave() {
        Plataforma plataforma = createPlataforma();
        when(plataformaRepository.save(any(Plataforma.class))).thenReturn(plataforma);

        Plataforma savedPlataforma = plataformaService.savePlataforma(plataforma);

        assertNotNull(savedPlataforma);
        assertEquals("PlayStation 4", savedPlataforma.getNombre());
        assertEquals(1L, savedPlataforma.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(plataformaRepository).deleteById(1L);

        plataformaService.deletePlataforma(1L);

        verify(plataformaRepository, times(1)).deleteById(1L);
    }
}