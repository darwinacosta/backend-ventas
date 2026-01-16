package com.wam.sistema_ventas.dto;


public class DetalleVentaRequest {
    private Long productoId;
    private Integer cantidad;

    public DetalleVentaRequest(Long productoId, Integer cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public DetalleVentaRequest() {
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
