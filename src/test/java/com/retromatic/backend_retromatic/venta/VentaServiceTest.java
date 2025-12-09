package com.retromatic.backend_retromatic.venta;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.repository.JuegoRepository;
import com.retromatic.backend_retromatic.usuarios.model.Usuario;
import com.retromatic.backend_retromatic.usuarios.repository.UsuarioRepository;
import com.retromatic.backend_retromatic.ventas.model.Estado;
import com.retromatic.backend_retromatic.ventas.model.MetodoPago;
import com.retromatic.backend_retromatic.ventas.model.Venta;
import com.retromatic.backend_retromatic.ventas.model.VentaJuego;
import com.retromatic.backend_retromatic.ventas.repository.EstadoRepository;
import com.retromatic.backend_retromatic.ventas.repository.MetodoPagoRepository;
import com.retromatic.backend_retromatic.ventas.repository.VentaJuegoRepository;
import com.retromatic.backend_retromatic.ventas.repository.VentaRepository;
import com.retromatic.backend_retromatic.ventas.service.VentaService;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @InjectMocks
    private VentaService ventaService;

    @Mock private VentaRepository ventaRepository;
    @Mock private VentaJuegoRepository ventaJuegoRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private JuegoRepository juegoRepository;
    @Mock private EstadoRepository estadoRepository;
    @Mock private MetodoPagoRepository metodoPagoRepository;

    private Usuario createUsuario() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setNombre("Cliente");
        return u;
    }

    private Juego createJuego() {
        Juego j = new Juego();
        j.setId(10L);
        j.setTitulo("Sonic");
        j.setPrecio(5000);
        return j;
    }

    private Estado createEstado(String nombre) {
        Estado e = new Estado();
        e.setId(nombre.equals("PENDIENTE") ? 1L : 2L);
        e.setNombre(nombre);
        return e;
    }

    private Venta createCarritoPendiente() {
        Venta v = new Venta();
        v.setId(100L);
        v.setUsuario(createUsuario());
        v.setEstado(createEstado("PENDIENTE"));
        v.setTotal(0);
        v.setJuegos(new ArrayList<>());
        return v;
    }

    @Test
    void testAgregarJuegoAlCarrito_NuevoItem() {
        // Datos
        Long usuarioId = 1L;
        Long juegoId = 10L;
        Venta carrito = createCarritoPendiente();
        Juego juego = createJuego();

        when(ventaRepository.findByUsuarioIdAndEstadoNombre(usuarioId, "PENDIENTE"))
                .thenReturn(Optional.of(carrito));
        
        when(juegoRepository.findById(juegoId)).thenReturn(Optional.of(juego));
        
        when(ventaJuegoRepository.findByVentaIdAndJuegoId(carrito.getId(), juegoId))
                .thenReturn(Optional.empty());

        when(ventaRepository.save(any(Venta.class))).thenAnswer(i -> i.getArguments()[0]);

        Venta resultado = ventaService.agregarJuegoAlCarrito(usuarioId, juegoId);

        assertNotNull(resultado);
        assertEquals(1, resultado.getJuegos().size(), "Debería haber 1 juego en la lista");
        assertEquals(5000, resultado.getTotal(), "El total debería ser 5000");
        
        verify(ventaJuegoRepository).save(any(VentaJuego.class));
    }

    @Test
    void testVaciarCarrito() {
        Long usuarioId = 1L;
        Venta carrito = createCarritoPendiente();
        
        VentaJuego item = new VentaJuego();
        item.setId(55L);
        item.setPrecio(5000);
        item.setCantidad(1);
        item.setVenta(carrito);
        carrito.getJuegos().add(item);
        carrito.setTotal(5000);

        when(ventaRepository.findByUsuarioIdAndEstadoNombre(usuarioId, "PENDIENTE"))
                .thenReturn(Optional.of(carrito));
        
        when(ventaRepository.save(any(Venta.class))).thenAnswer(i -> i.getArguments()[0]);

        Venta resultado = ventaService.vaciarCarrito(usuarioId);

        // Verificaciones
        assertEquals(0, resultado.getTotal());
        assertEquals(0, resultado.getJuegos().size());
        verify(ventaJuegoRepository, times(1)).delete(item);
    }

    @Test
    void testConfirmarCarrito() {
        Long usuarioId = 1L;
        Long metodoPagoId = 5L;
        
        Venta carrito = createCarritoPendiente();
        VentaJuego item = new VentaJuego();
        item.setPrecio(1000); 
        item.setCantidad(1);
        carrito.getJuegos().add(item);
        carrito.setTotal(1000);

        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setId(metodoPagoId);
        metodoPago.setNombre("Debito");

        Estado estadoPagado = createEstado("PAGADO");

        when(ventaRepository.findByUsuarioIdAndEstadoNombre(usuarioId, "PENDIENTE"))
                .thenReturn(Optional.of(carrito));
        when(metodoPagoRepository.findById(metodoPagoId)).thenReturn(Optional.of(metodoPago));
        when(estadoRepository.findByNombre("PAGADO")).thenReturn(Optional.of(estadoPagado));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(i -> i.getArguments()[0]);

        Venta resultado = ventaService.confirmarCarrito(usuarioId, metodoPagoId);

        assertNotNull(resultado.getFechaHora(), "La fecha de venta no debería ser nula");
        assertEquals("PAGADO", resultado.getEstado().getNombre());
        assertEquals("Debito", resultado.getMetodoPago().getNombre());
    }

    @Test
    void testDecrementarCantidad() {
        Long usuarioId = 1L;
        Long ventaJuegoId = 777L;
        
        Venta carrito = createCarritoPendiente();
        
        VentaJuego item = new VentaJuego();
        item.setId(ventaJuegoId);
        item.setPrecio(2000);
        item.setCantidad(2);
        item.setVenta(carrito);
        
        carrito.getJuegos().add(item);
        carrito.setTotal(4000);

        when(ventaRepository.findByUsuarioIdAndEstadoNombre(usuarioId, "PENDIENTE"))
                .thenReturn(Optional.of(carrito));
        when(ventaJuegoRepository.findById(ventaJuegoId)).thenReturn(Optional.of(item));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(i -> i.getArguments()[0]);

        Venta resultado = ventaService.decrementarCantidadEnCarrito(usuarioId, ventaJuegoId);

        assertEquals(1, item.getCantidad());
        assertEquals(2000, resultado.getTotal());
        verify(ventaJuegoRepository).save(item);
    }
}