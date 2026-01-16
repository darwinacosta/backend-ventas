package com.wam.sistema_ventas.service;

import com.wam.sistema_ventas.dto.LoginRequest;
import com.wam.sistema_ventas.dto.LoginResponse;
import com.wam.sistema_ventas.model.Usuario;
import com.wam.sistema_ventas.repository.UsuarioRepository;
import com.wam.sistema_ventas.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponse login(LoginRequest request) {
        System.out.println("=======================================");
        System.out.println("=== INICIO DE LOGIN ===");
        System.out.println("Username recibido: " + request.getUsername());
        System.out.println("Password recibida: " + request.getPassword());

        try {
            // Verificar si el usuario existe
            Usuario usuarioExistente = usuarioRepository.findByUsername(request.getUsername())
                    .orElse(null);

            if (usuarioExistente == null) {
                System.err.println("ERROR: Usuario no encontrado en la base de datos");
                throw new RuntimeException("Usuario no encontrado");
            }

            System.out.println("Usuario encontrado: " + usuarioExistente.getNombre());
            System.out.println("Rol: " + usuarioExistente.getRol());
            System.out.println("Password hash (primeros 30 chars): " +
                    usuarioExistente.getPassword().substring(0, Math.min(30, usuarioExistente.getPassword().length())));

            // Autenticar
            System.out.println("Intentando autenticar...");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            System.out.println("Autenticación exitosa!");

            // Generar token
            String token = jwtUtil.generateToken(request.getUsername());
            System.out.println("Token generado: " + token.substring(0, Math.min(50, token.length())) + "...");

            // Buscar usuario nuevamente
            Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Crear respuesta
            LoginResponse response = new LoginResponse(
                    token,
                    "Bearer",
                    usuario.getUsername(),
                    usuario.getNombre(),
                    usuario.getRol().name()
            );

            System.out.println("Response creada:");
            System.out.println("  - Token: " + (response.getToken() != null ? "OK" : "NULL"));
            System.out.println("  - Tipo: " + response.getTipo());
            System.out.println("  - Username: " + response.getUsername());
            System.out.println("  - Nombre: " + response.getNombre());
            System.out.println("  - Rol: " + response.getRol());
            System.out.println("=== FIN LOGIN EXITOSO ===");
            System.out.println("=======================================");

            return response;

        } catch (BadCredentialsException e) {
            System.err.println("ERROR: Credenciales incorrectas");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("=======================================");
            throw new RuntimeException("Usuario o contraseña incorrectos");
        } catch (Exception e) {
            System.err.println("ERROR en login: " + e.getClass().getName());
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.err.println("=======================================");
            throw new RuntimeException("Error al iniciar sesión: " + e.getMessage());
        }
    }
}
