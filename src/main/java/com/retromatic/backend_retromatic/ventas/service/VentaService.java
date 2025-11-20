package com.retromatic.backend_retromatic.ventas.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final VentaJuegoRepository ventaJuegoRepository;
    private final EstadoRepository estadoRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final UsuarioRepository usuarioRepository;
    private final JuegoRepository juegoRepository;

    public Venta obtenerOCrearCarrito(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Estado estadoCarrito = estadoRepository.findByNombre("CARRITO")
                .orElseThrow(() -> new RuntimeException("Problema en el CARRITO"));

        return ventaRepository
                .findByUsuarioIdAndEstadoNombre(usuarioId, "CARRITO")
                .orElseGet(() -> {
                    Venta nueva = new Venta();
                    nueva.setUsuario(usuario);
                    nueva.setEstado(estadoCarrito);
                    nueva.setJuegos(new ArrayList<>());
                    nueva.setTotal(0);
                    nueva.setFechaHora(LocalDateTime.now());
                    return ventaRepository.save(nueva);
                });
    }

    public Venta obtenerCarrito(Long usuarioId) {
        return obtenerOCrearCarrito(usuarioId);
    }

    public Venta agregarJuegoAlCarrito(Long usuarioId, Long juegoId) {
        Venta carrito = obtenerOCrearCarrito(usuarioId);

        Juego juego = juegoRepository.findById(juegoId)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        VentaJuego item = new VentaJuego();
        item.setVenta(carrito);
        item.setJuego(juego);
        item.setNombre(juego.getTitulo());

        ventaJuegoRepository.save(item);

        int total = carrito.getJuegos().stream()
                .map(vj -> vj.getJuego().getPrecio())
                .mapToInt(Integer::intValue)
                .sum();

        carrito.setTotal(total);
        return ventaRepository.save(carrito);
    }

    public Venta eliminarItemDeCarrito(Long usuarioId, Long ventaJuegoId) {
        Venta carrito = obtenerOCrearCarrito(usuarioId);

        VentaJuego item = ventaJuegoRepository.findById(ventaJuegoId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (!item.getVenta().getId().equals(carrito.getId())) {
            throw new RuntimeException("El item no pertenece al carrito del usuario");
        }

        ventaJuegoRepository.delete(item);

        int total = carrito.getJuegos().stream()
                .map(vj -> vj.getJuego().getPrecio())
                .mapToInt(Integer::intValue)
                .sum();

        carrito.setTotal(total);
        return ventaRepository.save(carrito);
    }

    public Venta vaciarCarrito(Long usuarioId) {
        Venta carrito = obtenerOCrearCarrito(usuarioId);

        ventaJuegoRepository.deleteAll(carrito.getJuegos());
        carrito.getJuegos().clear();
        carrito.setTotal(0);

        return ventaRepository.save(carrito);
    }

    public Venta confirmarCarrito(Long usuarioId, Long metodoPagoId) {
        Venta carrito = obtenerOCrearCarrito(usuarioId);

        Estado estadoPendiente = estadoRepository.findByNombre("PAGADA")
                .orElseThrow(() -> new RuntimeException("Estado 'PAGADA' no configurado"));

        MetodoPago metodoPago = metodoPagoRepository.findById(metodoPagoId)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        carrito.setEstado(estadoPendiente);
        carrito.setMetodoPago(metodoPago);
        carrito.setFechaHora(LocalDateTime.now());

        return ventaRepository.save(carrito);
    }
}
