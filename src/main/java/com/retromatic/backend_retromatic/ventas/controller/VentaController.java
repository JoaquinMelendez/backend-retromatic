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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/ventas")
@RequiredArgsConstructor
@Tag(
    name = "Ventas / Carrito",
    description = "Operaciones relacionadas con el carrito de compras y la confirmación de ventas"
)
public class VentaController {

    private final VentaService ventaService;

    @GetMapping("/carrito/{usuarioId}")
    @Operation(
        summary = "Obtener carrito activo de un usuario",
        description = "Retorna la venta en estado PENDIENTE correspondiente al carrito actual del usuario."
    )
    public ResponseEntity<Venta> obtenerCarrito(@PathVariable Long usuarioId) {
        Venta carrito = ventaService.obtenerCarrito(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/carrito/{usuarioId}/agregar/{juegoId}")
    @Operation(
        summary = "Agregar un juego al carrito",
        description = "Si el juego ya está en el carrito, aumenta la cantidad. Si no, lo agrega como nuevo ítem."
    )
    public ResponseEntity<Venta> agregarJuego(
            @PathVariable Long usuarioId,
            @PathVariable Long juegoId
    ) {
        Venta carrito = ventaService.agregarJuegoAlCarrito(usuarioId, juegoId);
        return ResponseEntity.ok(carrito);
    }

    @DeleteMapping("/carrito/{usuarioId}/item/{ventaJuegoId}")
    @Operation(
        summary = "Eliminar un ítem del carrito",
        description = "Elimina completamente un ítem específico del carrito del usuario."
    )
    public ResponseEntity<Venta> eliminarItem(
            @PathVariable Long usuarioId,
            @PathVariable Long ventaJuegoId
    ) {
        Venta carrito = ventaService.eliminarItemDeCarrito(usuarioId, ventaJuegoId);
        return ResponseEntity.ok(carrito);
    }

    @DeleteMapping("/carrito/{usuarioId}")
    @Operation(
        summary = "Vaciar todo el carrito",
        description = "Elimina todos los ítems del carrito del usuario."
    )
    public ResponseEntity<Venta> vaciarCarrito(@PathVariable Long usuarioId) {
        Venta carrito = ventaService.vaciarCarrito(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/carrito/{usuarioId}/confirmar/{metodoPagoId}")
    @Operation(
        summary = "Confirmar carrito y generar compra",
        description = "Cambia el estado de la venta de PENDIENTE a PAGADO."
    )
    public ResponseEntity<Venta> confirmarCarrito(
            @PathVariable Long usuarioId,
            @PathVariable Long metodoPagoId
    ) {
        Venta venta = ventaService.confirmarCarrito(usuarioId, metodoPagoId);
        return ResponseEntity.ok(venta);
    }

    @PostMapping("/carrito/{usuarioId}/item/{ventaJuegoId}/decrementar")
    @Operation(
        summary = "Decrementar cantidad de un ítem",
        description = """
            Reduce en 1 la cantidad del ítem seleccionado.
            Si llega a 0, se elimina del carrito.
            """
    )
    public ResponseEntity<Venta> decrementarItem(
            @PathVariable Long usuarioId,
            @PathVariable Long ventaJuegoId
    ) {
        Venta carrito = ventaService.decrementarCantidadEnCarrito(usuarioId, ventaJuegoId);
        return ResponseEntity.ok(carrito);
    }
    @GetMapping("/pagadas")
    @Operation(
        summary = "Obtener todas las ventas pagadas",
        description = "Retorna una lista de ventas con estado PAGADO. Útil para panel administrativo."
    )
    public ResponseEntity<List<Venta>> obtenerVentasPagadas() {
        List<Venta> ventas = ventaService.obtenerVentasPagadas();
        return ResponseEntity.ok(ventas);
    }
}
