package com.wam.sistema_ventas.service;

import com.wam.sistema_ventas.model.Producto;
import com.wam.sistema_ventas.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public List<Producto> obtenerActivos() {
        return productoRepository.findByActivoTrue();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto obtenerPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional
    public Producto crear(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizar(Long id, Producto producto) {
        Producto productoExistente = obtenerPorId(id);
        productoExistente.setCodigo(producto.getCodigo());
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setStock(producto.getStock());
        productoExistente.setStockMinimo(producto.getStockMinimo());
        productoExistente.setCategoria(producto.getCategoria());
        return productoRepository.save(productoExistente);
    }

    @Transactional
    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Transactional
    public void actualizarStock(Long id, Integer cantidad) {
        Producto producto = obtenerPorId(id);
        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }
}
