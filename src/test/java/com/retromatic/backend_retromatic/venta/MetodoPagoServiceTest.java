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

import com.retromatic.backend_retromatic.ventas.model.MetodoPago;
import com.retromatic.backend_retromatic.ventas.repository.MetodoPagoRepository;
import com.retromatic.backend_retromatic.ventas.service.MetodoPagoService;

@ExtendWith(MockitoExtension.class)
class MetodoPagoServiceTest {

    @InjectMocks
    private MetodoPagoService metodoPagoService;

    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    private MetodoPago createMetodoPago() {
        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setId(1L);
        metodoPago.setNombre("Tarjeta de Crédito");
        return metodoPago;
    }

    @Test
    void testGetAllMetodoPagos() {
        when(metodoPagoRepository.findAll()).thenReturn(List.of(createMetodoPago()));
        List<MetodoPago> metodos = metodoPagoService.getAllMetodoPagos();

        assertNotNull(metodos);
        assertEquals(1, metodos.size());
        assertEquals("Tarjeta de Crédito", metodos.get(0).getNombre());
    }

    @Test
    void testGetMetodoPagoById() {
        when(metodoPagoRepository.findById(1L)).thenReturn(Optional.of(createMetodoPago()));
        MetodoPago metodoPago = metodoPagoService.getMetodoPagoById(1L);

        assertNotNull(metodoPago);
        assertEquals("Tarjeta de Crédito", metodoPago.getNombre());
    }

    @Test
    void testSaveMetodoPago() {
        MetodoPago metodoPago = createMetodoPago();
        when(metodoPagoRepository.save(any(MetodoPago.class))).thenReturn(metodoPago);

        MetodoPago savedMetodoPago = metodoPagoService.saveMetodoPago(metodoPago);

        assertNotNull(savedMetodoPago);
        assertEquals("Tarjeta de Crédito", savedMetodoPago.getNombre());
        assertEquals(1L, savedMetodoPago.getId());
    }

    @Test
    void testDeleteMetodoPago() {
        doNothing().when(metodoPagoRepository).deleteById(1L);

        metodoPagoService.deleteMetodoPago(1L);

        verify(metodoPagoRepository, times(1)).deleteById(1L);
    }
}