package com.wam.sistema_ventas.repository;

import com.wam.sistema_ventas.model.EstadoVenta;
import com.wam.sistema_ventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findByNumeroVenta(String numeroVenta);
    List<Venta> findByEstado(EstadoVenta estado);
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
