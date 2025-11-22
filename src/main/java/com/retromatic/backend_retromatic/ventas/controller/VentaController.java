package com.retromatic.backend_retromatic.ventas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retromatic.backend_retromatic.ventas.model.Venta;
import com.retromatic.backend_retromatic.ventas.service.VentaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    //GET usuarioId
    @GetMapping("/carrito/{usuarioId}")
    public ResponseEntity<Venta> obtenerCarrito(@PathVariable Long usuarioId) {
        Venta carrito = ventaService.obtenerCarrito(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    //POST usuarioId + juegoId 
    @PostMapping("/carrito/{usuarioId}/agregar/{juegoId}")
    public ResponseEntity<Venta> agregarJuego(
            @PathVariable Long usuarioId,
            @PathVariable Long juegoId
    ) {
        Venta carrito = ventaService.agregarJuegoAlCarrito(usuarioId, juegoId);
        return ResponseEntity.ok(carrito);
    }

    //DELETE usuarioId + ventaJuegoId
    @DeleteMapping("/carrito/{usuarioId}/item/{ventaJuegoId}")
    public ResponseEntity<Venta> eliminarItem(
            @PathVariable Long usuarioId,
            @PathVariable Long ventaJuegoId
    ) {
        Venta carrito = ventaService.eliminarItemDeCarrito(usuarioId, ventaJuegoId);
        return ResponseEntity.ok(carrito);
    }

    //DELETE usuarioId
    @DeleteMapping("/carrito/{usuarioId}")
    public ResponseEntity<Venta> vaciarCarrito(@PathVariable Long usuarioId) {
        Venta carrito = ventaService.vaciarCarrito(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    //POST usuarioId + metodoPagoId
    @PostMapping("/carrito/{usuarioId}/confirmar/{metodoPagoId}")
    public ResponseEntity<Venta> confirmarCarrito(
            @PathVariable Long usuarioId,
            @PathVariable Long metodoPagoId
    ) {
        Venta venta = ventaService.confirmarCarrito(usuarioId, metodoPagoId);
        return ResponseEntity.ok(venta);
    }

    @PostMapping("/carrito/{usuarioId}/item/{ventaJuegoId}/decrementar")
    public ResponseEntity<Venta> decrementarItem(
            @PathVariable Long usuarioId,
            @PathVariable Long ventaJuegoId
    ) {
        Venta carrito = ventaService.decrementarCantidadEnCarrito(usuarioId, ventaJuegoId);
        return ResponseEntity.ok(carrito);
    }
    @GetMapping("/pagadas")
    public ResponseEntity<List<Venta>> obtenerVentasPagadas() {
        List<Venta> ventas = ventaService.obtenerVentasPagadas();
        return ResponseEntity.ok(ventas);
    }
}
