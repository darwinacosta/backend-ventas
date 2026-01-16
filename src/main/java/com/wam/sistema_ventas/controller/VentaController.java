package com.wam.sistema_ventas.controller;

import com.wam.sistema_ventas.dto.VentaRequest;
import com.wam.sistema_ventas.dto.VentaResponse;
import com.wam.sistema_ventas.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<VentaResponse>> obtenerTodas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @GetMapping("/numero/{numeroVenta}")
    public ResponseEntity<VentaResponse> obtenerPorNumero(@PathVariable String numeroVenta) {
        return ResponseEntity.ok(ventaService.obtenerPorNumero(numeroVenta));
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<VentaResponse>> obtenerPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(ventaService.obtenerPorFechas(inicio, fin));
    }

    @PostMapping
    public ResponseEntity<VentaResponse> crear(@RequestBody VentaRequest request, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.crear(request, username));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        ventaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
