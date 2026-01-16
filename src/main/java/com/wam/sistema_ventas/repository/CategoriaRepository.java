package com.wam.sistema_ventas.repository;


import com.wam.sistema_ventas.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByActivoTrue();
}
