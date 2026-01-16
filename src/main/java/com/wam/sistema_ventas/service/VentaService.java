package com.wam.sistema_ventas.service;

import com.wam.sistema_ventas.dto.DetalleVentaRequest;
import com.wam.sistema_ventas.dto.DetalleVentaResponse;
import com.wam.sistema_ventas.dto.VentaRequest;
import com.wam.sistema_ventas.dto.VentaResponse;
import com.wam.sistema_ventas.model.*;
import com.wam.sistema_ventas.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public VentaService(VentaRepository ventaRepository, ClienteService clienteService, ProductoService productoService, UsuarioService usuarioService) {
        this.ventaRepository = ventaRepository;
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    public List<VentaResponse> obtenerTodas() {
        return ventaRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public VentaResponse obtenerPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        return convertirAResponse(venta);
    }

    public VentaResponse obtenerPorNumero(String numeroVenta) {
        Venta venta = ventaRepository.findByNumeroVenta(numeroVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        return convertirAResponse(venta);
    }

    public List<VentaResponse> obtenerPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepository.findByFechaBetween(inicio, fin).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public VentaResponse crear(VentaRequest request, String username) {
        // Obtener cliente y usuario
        Cliente cliente = clienteService.obtenerPorId(request.getClienteId());
        Usuario usuario = usuarioService.obtenerPorUsername(username);

        // Crear venta
        Venta venta = new Venta();
        venta.setNumeroVenta(generarNumeroVenta());
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        venta.setFecha(LocalDateTime.now());
        venta.setEstado(EstadoVenta.COMPLETADA);

        // Crear detalles y calcular total
        BigDecimal total = BigDecimal.ZERO;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (DetalleVentaRequest detalleRequest : request.getDetalles()) {
            Producto producto = productoService.obtenerPorId(detalleRequest.getProductoId());

            // Validar stock
            if (producto.getStock() < detalleRequest.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Crear detalle
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());

            BigDecimal subtotal = producto.getPrecio()
                    .multiply(BigDecimal.valueOf(detalleRequest.getCantidad()));
            detalle.setSubtotal(subtotal);

            detalles.add(detalle);
            total = total.add(subtotal);

            // Actualizar stock del producto
            productoService.actualizarStock(producto.getId(), detalleRequest.getCantidad());
        }

        venta.setDetalles(detalles);
        venta.setTotal(total);

        Venta ventaGuardada = ventaRepository.save(venta);
        return convertirAResponse(ventaGuardada);
    }

    @Transactional
    public void cancelar(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        if (venta.getEstado() == EstadoVenta.CANCELADA) {
            throw new RuntimeException("La venta ya está cancelada");
        }

        // Devolver stock
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
        }

        venta.setEstado(EstadoVenta.CANCELADA);
        ventaRepository.save(venta);
    }

    private String generarNumeroVenta() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "V-" + fecha;
    }

    private VentaResponse convertirAResponse(Venta venta) {
        VentaResponse response = new VentaResponse();
        response.setId(venta.getId());
        response.setNumeroVenta(venta.getNumeroVenta());
        response.setClienteNombre(venta.getCliente().getNombre() + " " +
                (venta.getCliente().getApellido() != null ? venta.getCliente().getApellido() : ""));
        response.setUsuarioNombre(venta.getUsuario().getNombre());
        response.setFecha(venta.getFecha());
        response.setTotal(venta.getTotal());
        response.setEstado(venta.getEstado().name());

        List<DetalleVentaResponse> detallesResponse = venta.getDetalles().stream()
                .map(this::convertirDetalleAResponse)
                .collect(Collectors.toList());
        response.setDetalles(detallesResponse);

        return response;
    }

    private DetalleVentaResponse convertirDetalleAResponse(DetalleVenta detalle) {
        DetalleVentaResponse response = new DetalleVentaResponse();
        response.setId(detalle.getId());
        response.setProductoNombre(detalle.getProducto().getNombre());
        response.setCantidad(detalle.getCantidad());
        response.setPrecioUnitario(detalle.getPrecioUnitario());
        response.setSubtotal(detalle.getSubtotal());
        return response;
    }
}