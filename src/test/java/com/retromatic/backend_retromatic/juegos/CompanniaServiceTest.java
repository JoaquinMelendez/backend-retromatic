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

import com.retromatic.backend_retromatic.juegos.model.Compannia;
import com.retromatic.backend_retromatic.juegos.repository.CompanniaRepository;
import com.retromatic.backend_retromatic.juegos.service.CompanniaService;

@ExtendWith(MockitoExtension.class)
class CompanniaServiceTest {

    @InjectMocks
    private CompanniaService companniaService;

    @Mock
    private CompanniaRepository companniaRepository;

    private Compannia createCompannia() {
        Compannia compannia = new Compannia();
        compannia.setId(1L);
        compannia.setNombre("Nintendo");
        return compannia;
    }

    @Test
    void testFindAll() {
        when(companniaRepository.findAll()).thenReturn(List.of(createCompannia()));
        List<Compannia> compannias = companniaService.getAllCompannias();

        assertNotNull(compannias);
        assertEquals(1, compannias.size());
        assertEquals("Nintendo", compannias.get(0).getNombre());
    }

    @Test
    void testFindById() {
        when(companniaRepository.findById(1L)).thenReturn(Optional.of(createCompannia()));
        Compannia compannia = companniaService.getCompanniaById(1L);

        assertNotNull(compannia);
        assertEquals("Nintendo", compannia.getNombre());
    }

    @Test
    void testSave() {
        Compannia compannia = createCompannia();
        when(companniaRepository.save(any(Compannia.class))).thenReturn(compannia);

        Compannia savedCompannia = companniaService.saveCompannia(compannia);

        assertNotNull(savedCompannia);
        assertEquals("Nintendo", savedCompannia.getNombre());
        assertEquals(1L, savedCompannia.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(companniaRepository).deleteById(1L);

        companniaService.deleteCompannia(1L);

        verify(companniaRepository, times(1)).deleteById(1L);
    }
}