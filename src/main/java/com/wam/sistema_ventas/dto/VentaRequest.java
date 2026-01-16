package com.wam.sistema_ventas.dto;

import java.util.List;


public class VentaRequest {
    private Long clienteId;
    private List<DetalleVentaRequest> detalles;

    public VentaRequest(Long clienteId, List<DetalleVentaRequest> detalles) {
        this.clienteId = clienteId;
        this.detalles = detalles;
    }

    public VentaRequest() {
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<DetalleVentaRequest> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaRequest> detalles) {
        this.detalles = detalles;
    }
}
