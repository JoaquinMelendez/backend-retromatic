package com.retromatic.backend_retromatic.venta;

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

import com.retromatic.backend_retromatic.ventas.model.Estado;
import com.retromatic.backend_retromatic.ventas.repository.EstadoRepository;
import com.retromatic.backend_retromatic.ventas.service.EstadoService;

@ExtendWith(MockitoExtension.class)
class EstadoServiceTest {

    @InjectMocks
    private EstadoService estadoService;

    @Mock
    private EstadoRepository estadoRepository;

    private Estado createEstado() {
        Estado estado = new Estado();
        estado.setId(1L);
        estado.setNombre("COMPLETADA");
        return estado;
    }

    @Test
    void testGetAllEstados() {
        when(estadoRepository.findAll()).thenReturn(List.of(createEstado()));
        List<Estado> estados = estadoService.getAllEstados();

        assertNotNull(estados);
        assertEquals(1, estados.size());
        assertEquals("COMPLETADA", estados.get(0).getNombre());
    }

    @Test
    void testGetEstadoById() {
        when(estadoRepository.findById(1L)).thenReturn(Optional.of(createEstado()));
        Estado estado = estadoService.getEstadoById(1L);

        assertNotNull(estado);
        assertEquals("COMPLETADA", estado.getNombre());
    }

    @Test
    void testSaveEstado() {
        Estado estado = createEstado();
        when(estadoRepository.save(any(Estado.class))).thenReturn(estado);

        Estado savedEstado = estadoService.saveEstado(estado);

        assertNotNull(savedEstado);
        assertEquals("COMPLETADA", savedEstado.getNombre());
        assertEquals(1L, savedEstado.getId());
    }

    @Test
    void testDeleteEstado() {
        doNothing().when(estadoRepository).deleteById(1L);

        estadoService.deleteEstado(1L);

        verify(estadoRepository, times(1)).deleteById(1L);
    }
}