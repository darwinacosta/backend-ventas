package com.wam.sistema_ventas.service;

import com.wam.sistema_ventas.model.Categoria;
import com.wam.sistema_ventas.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> obtenerActivas() {
        return categoriaRepository.findByActivoTrue();
    }

    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    @Transactional
    public Categoria crear(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria actualizar(Long id, Categoria categoria) {
        Categoria categoriaExistente = obtenerPorId(id);
        categoriaExistente.setNombre(categoria.getNombre());
        categoriaExistente.setDescripcion(categoria.getDescripcion());
        return categoriaRepository.save(categoriaExistente);
    }

    @Transactional
    public void eliminar(Long id) {
        Categoria categoria = obtenerPorId(id);
        categoria.setActivo(false);
        categoriaRepository.save(categoria);
    }
}
