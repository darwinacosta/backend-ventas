package com.wam.sistema_ventas.service;

import com.wam.sistema_ventas.model.Usuario;
import com.wam.sistema_ventas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El username ya existe");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizar(Long id, Usuario usuario) {
        Usuario usuarioExistente = obtenerPorId(id);
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setRol(usuario.getRol());

        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void eliminar(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}
