package com.wam.sistema_ventas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroVenta;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta(){

    }

    public Venta(Long id, String numeroVenta, Cliente cliente, Usuario usuario, LocalDateTime fecha, BigDecimal total, EstadoVenta estado, List<DetalleVenta> detalles) {
        this.id = id;
        this.numeroVenta = numeroVenta;
        this.cliente = cliente;
        this.usuario = usuario;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.detalles = detalles;
    }

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
}
