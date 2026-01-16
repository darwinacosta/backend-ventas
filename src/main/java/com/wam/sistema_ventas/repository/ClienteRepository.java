package com.wam.sistema_ventas.repository;

import com.wam.sistema_ventas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByActivoTrue();
    Optional<Cliente> findByDni(String dni);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}
