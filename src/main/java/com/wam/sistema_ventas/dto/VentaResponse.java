package com.wam.sistema_ventas.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class VentaResponse {
    private Long id;
    private String numeroVenta;
    private String clienteNombre;
    private String usuarioNombre;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String estado;
    private List<DetalleVentaResponse> detalles;

    public VentaResponse(Long id, String numeroVenta, String clienteNombre, String usuarioNombre, LocalDateTime fecha, BigDecimal total, String estado, List<DetalleVentaResponse> detalles) {
        this.id = id;
        this.numeroVenta = numeroVenta;
        this.clienteNombre = clienteNombre;
        this.usuarioNombre = usuarioNombre;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.detalles = detalles;
    }

    public VentaResponse(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleVentaResponse> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaResponse> detalles) {
        this.detalles = detalles;
    }
}
