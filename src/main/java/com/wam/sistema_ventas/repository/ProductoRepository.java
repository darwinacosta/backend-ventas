package com.wam.sistema_ventas.repository;

import com.wam.sistema_ventas.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
    Optional<Producto> findByCodigo(String codigo);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByStockLessThanEqual(Integer stock);
}
