package com.retromatic.backend_retromatic.ventas.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UsuarioRepository usuarioRepository;
    private final JuegoRepository juegoRepository;
    private final EstadoRepository estadoRepository;
    private final MetodoPagoRepository metodoPagoRepository;

    private static final String ESTADO_PENDIENTE = "PENDIENTE";
    private static final String ESTADO_PAGADO = "PAGADO";

    // Obtener carrito activo
    @Transactional(readOnly = true)
    public Venta obtenerCarrito(Long usuarioId) {
        return ventaRepository
                .findByUsuarioIdAndEstadoNombre(usuarioId, ESTADO_PENDIENTE)
                .orElseGet(() -> {
                    // Si no tiene carrito, devolvemos uno vacío "virtual" (no guardado)
                    Venta v = new Venta();
                    v.setTotal(0);
                    return v;
                });
    }


    @Transactional(readOnly = true)
    public List<Venta> obtenerVentasPagadas() {
        return ventaRepository.findByEstadoNombre("PAGADO");
    }

    // Obtener o crear carrito activo
    @Transactional
    protected Venta obtenerOCrearCarrito(Long usuarioId) {

        return ventaRepository
                .findByUsuarioIdAndEstadoNombre(usuarioId, ESTADO_PENDIENTE)
                .orElseGet(() -> {
                    Usuario usuario = usuarioRepository.findById(usuarioId)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                    Estado estadoPendiente = estadoRepository.findByNombre(ESTADO_PENDIENTE)
                            .orElseThrow(() -> new RuntimeException("Estado PENDIENTE no configurado"));

                    Venta nueva = new Venta();
                    nueva.setUsuario(usuario);
                    nueva.setEstado(estadoPendiente);
                    nueva.setTotal(0);
                    nueva.setFechaHora(null); // solo se setea al confirmar

                    return ventaRepository.save(nueva);
                });
    }

    // Agregar juego al carrito
    @Transactional
    public Venta agregarJuegoAlCarrito(Long usuarioId, Long juegoId) {

        Venta carrito = obtenerOCrearCarrito(usuarioId);

        Juego juego = juegoRepository.findById(juegoId)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        VentaJuego ventaJuego = ventaJuegoRepository
                .findByVentaIdAndJuegoId(carrito.getId(), juegoId)
                .orElse(null);

        if (ventaJuego == null) {
            ventaJuego = new VentaJuego();
            ventaJuego.setVenta(carrito);
            ventaJuego.setJuego(juego);
            ventaJuego.setPrecio(juego.getPrecio());
            ventaJuego.setCantidad(1);

        } else {
            ventaJuego.setCantidad(ventaJuego.getCantidad() + 1);
        }

        ventaJuegoRepository.save(ventaJuego);

        if (!carrito.getJuegos().contains(ventaJuego)) {
            carrito.getJuegos().add(ventaJuego);
        }

        recalcularTotal(carrito);
        return ventaRepository.save(carrito);
    }

    // Eliminar ítem del carrito
    @Transactional
    public Venta eliminarItemDeCarrito(Long usuarioId, Long ventaJuegoId) {

        Venta carrito = obtenerOCrearCarrito(usuarioId);

        VentaJuego ventaJuego = ventaJuegoRepository.findById(ventaJuegoId)
                .orElseThrow(() -> new RuntimeException("Item de carrito no encontrado"));

        if (ventaJuego.getVenta() == null || !ventaJuego.getVenta().getId().equals(carrito.getId())) {
            throw new RuntimeException("El item no pertenece al carrito del usuario");
        }

        carrito.getJuegos().remove(ventaJuego);
        ventaJuegoRepository.delete(ventaJuego);

        recalcularTotal(carrito);
        return ventaRepository.save(carrito);
    }

    // Vaciar carrito
    @Transactional
    public Venta vaciarCarrito(Long usuarioId) {

        Venta carrito = obtenerOCrearCarrito(usuarioId);

        // Eliminar todos los items
        for (VentaJuego vj : carrito.getJuegos()) {
            ventaJuegoRepository.delete(vj);
        }
        carrito.getJuegos().clear();

        carrito.setTotal(0);
        return ventaRepository.save(carrito);
    }

    // Confirmar carrito (simular compra)
    @Transactional
    public Venta confirmarCarrito(Long usuarioId, Long metodoPagoId) {

        Venta carrito = ventaRepository
                .findByUsuarioIdAndEstadoNombre(usuarioId, ESTADO_PENDIENTE)
                .orElseThrow(() -> new RuntimeException("No hay carrito activo para el usuario"));

        if (carrito.getJuegos() == null || carrito.getJuegos().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        MetodoPago metodoPago = metodoPagoRepository.findById(metodoPagoId)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        Estado estadoPagado = estadoRepository.findByNombre(ESTADO_PAGADO)
                .orElseThrow(() -> new RuntimeException("Estado PAGADO no configurado"));

        carrito.setMetodoPago(metodoPago);
        carrito.setEstado(estadoPagado);
        carrito.setFechaHora(LocalDateTime.now());

        recalcularTotal(carrito);

        return ventaRepository.save(carrito);
    }

    private void recalcularTotal(Venta venta) {
        if (venta.getJuegos() == null || venta.getJuegos().isEmpty()) {
            venta.setTotal(0);
            return;
        }

        int total = venta.getJuegos().stream()
                .mapToInt(vj -> {
                    Integer precio = vj.getPrecio() != null ? vj.getPrecio() : 0;
                    Integer cant = vj.getCantidad() != null ? vj.getCantidad() : 0;
                    return precio * cant;
                })
                .sum();

        venta.setTotal(total);
    }

    @Transactional
    public Venta decrementarCantidadEnCarrito(Long usuarioId, Long ventaJuegoId) {

        Venta carrito = obtenerOCrearCarrito(usuarioId);

        VentaJuego ventaJuego = ventaJuegoRepository.findById(ventaJuegoId)
                .orElseThrow(() -> new RuntimeException("Item de carrito no encontrado"));

        if (ventaJuego.getVenta() == null || !ventaJuego.getVenta().getId().equals(carrito.getId())) {
            throw new RuntimeException("El item no pertenece al carrito del usuario");
        }

        Integer cantidadActual = ventaJuego.getCantidad() != null ? ventaJuego.getCantidad() : 0;

        if (cantidadActual <= 1) {
            carrito.getJuegos().remove(ventaJuego);
            ventaJuegoRepository.delete(ventaJuego);
        } else {
            ventaJuego.setCantidad(cantidadActual - 1);
            ventaJuegoRepository.save(ventaJuego);

            if (!carrito.getJuegos().contains(ventaJuego)) {
                carrito.getJuegos().add(ventaJuego);
            }
        }

        recalcularTotal(carrito);
        return ventaRepository.save(carrito);
    }


}
