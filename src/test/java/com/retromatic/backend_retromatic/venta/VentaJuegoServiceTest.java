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

import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.ventas.model.Venta;
import com.retromatic.backend_retromatic.ventas.model.VentaJuego;
import com.retromatic.backend_retromatic.ventas.repository.VentaJuegoRepository;
import com.retromatic.backend_retromatic.ventas.service.VentaJuegoService;

@ExtendWith(MockitoExtension.class)
class VentaJuegoServiceTest {

    @InjectMocks
    private VentaJuegoService ventaJuegoService;

    @Mock
    private VentaJuegoRepository ventaJuegoRepository;

    private VentaJuego createVentaJuego() {
        VentaJuego ventaJuego = new VentaJuego();
        ventaJuego.setId(1L);
        ventaJuego.setPrecio(59990);
        ventaJuego.setCantidad(2);
        
        Juego juego = new Juego();
        juego.setId(10L);
        juego.setTitulo("Zelda");
        ventaJuego.setJuego(juego);

        Venta venta = new Venta();
        venta.setId(20L);
        ventaJuego.setVenta(venta);

        return ventaJuego;
    }

    @Test
    void testGetAllVentaJuegos() {
        when(ventaJuegoRepository.findAll()).thenReturn(List.of(createVentaJuego()));
        List<VentaJuego> lista = ventaJuegoService.getAllVentaJuegos();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(59990, lista.get(0).getPrecio());
        assertEquals("Zelda", lista.get(0).getJuego().getTitulo());
    }

    @Test
    void testGetVentaJuegoById() {
        when(ventaJuegoRepository.findById(1L)).thenReturn(Optional.of(createVentaJuego()));
        VentaJuego ventaJuego = ventaJuegoService.getVentaJuegoById(1L);

        assertNotNull(ventaJuego);
        assertEquals(1L, ventaJuego.getId());
        assertEquals(2, ventaJuego.getCantidad());
    }

    @Test
    void testSaveVentaJuego() {
        VentaJuego ventaJuego = createVentaJuego();
        when(ventaJuegoRepository.save(any(VentaJuego.class))).thenReturn(ventaJuego);

        VentaJuego saved = ventaJuegoService.saveVentaJuego(ventaJuego);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        assertEquals(59990, saved.getPrecio());
    }

    @Test
    void testDeleteVentaJuego() {
        doNothing().when(ventaJuegoRepository).deleteById(1L);

        ventaJuegoService.deleteVentaJuego(1L);

        verify(ventaJuegoRepository, times(1)).deleteById(1L);
    }
}