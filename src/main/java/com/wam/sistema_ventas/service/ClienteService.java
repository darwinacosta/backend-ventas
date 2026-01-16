package com.wam.sistema_ventas.service;

import com.wam.sistema_ventas.model.Cliente;
import com.wam.sistema_ventas.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public List<Cliente> obtenerActivos() {
        return clienteRepository.findByActivoTrue();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional
    public Cliente crear(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente clienteExistente = obtenerPorId(id);
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellido(cliente.getApellido());
        clienteExistente.setDni(cliente.getDni());
        clienteExistente.setTelefono(cliente.getTelefono());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setDireccion(cliente.getDireccion());
        return clienteRepository.save(clienteExistente);
    }

    @Transactional
    public void eliminar(Long id) {
        Cliente cliente = obtenerPorId(id);
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }
}
