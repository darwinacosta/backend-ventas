package com.wam.sistema_ventas.config;


import com.wam.sistema_ventas.model.*;
import com.wam.sistema_ventas.repository.CategoriaRepository;
import com.wam.sistema_ventas.repository.ClienteRepository;
import com.wam.sistema_ventas.repository.ProductoRepository;
import com.wam.sistema_ventas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor manual (reemplaza @RequiredArgsConstructor)
    public DataInitializer(UsuarioRepository usuarioRepository,
                           CategoriaRepository categoriaRepository,
                           ProductoRepository productoRepository,
                           ClienteRepository clienteRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen datos
        if (usuarioRepository.count() > 0) {
            System.out.println("Los datos ya fueron inicializados");
            return;
        }

        // Crear usuarios
        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setNombre("Administrador");
        admin.setEmail("admin@sistema.com");
        admin.setRol(Rol.ADMIN);
        admin.setActivo(true);
        usuarioRepository.save(admin);

        Usuario vendedor = new Usuario();
        vendedor.setUsername("vendedor");
        vendedor.setPassword(passwordEncoder.encode("vendedor123"));
        vendedor.setNombre("Juan Vendedor");
        vendedor.setEmail("vendedor@sistema.com");
        vendedor.setRol(Rol.VENDEDOR);
        vendedor.setActivo(true);
        usuarioRepository.save(vendedor);

        Usuario cajero = new Usuario();
        cajero.setUsername("cajero");
        cajero.setPassword(passwordEncoder.encode("cajero123"));
        cajero.setNombre("María Cajera");
        cajero.setEmail("cajero@sistema.com");
        cajero.setRol(Rol.CAJERO);
        cajero.setActivo(true);
        usuarioRepository.save(cajero);

        // Crear categorías
        Categoria electro = new Categoria();
        electro.setNombre("Electrónica");
        electro.setDescripcion("Productos electrónicos");
        electro.setActivo(true);
        categoriaRepository.save(electro);

        Categoria hogar = new Categoria();
        hogar.setNombre("Hogar");
        hogar.setDescripcion("Artículos para el hogar");
        hogar.setActivo(true);
        categoriaRepository.save(hogar);

        Categoria ropa = new Categoria();
        ropa.setNombre("Ropa");
        ropa.setDescripcion("Prendas de vestir");
        ropa.setActivo(true);
        categoriaRepository.save(ropa);

        // Crear productos
        Producto laptop = new Producto();
        laptop.setCodigo("ELEC001");
        laptop.setNombre("Laptop HP");
        laptop.setDescripcion("Laptop HP Core i5 8GB RAM");
        laptop.setPrecio(new BigDecimal("1200.00"));
        laptop.setStock(10);
        laptop.setStockMinimo(2);
        laptop.setCategoria(electro);
        laptop.setActivo(true);
        productoRepository.save(laptop);

        Producto mouse = new Producto();
        mouse.setCodigo("ELEC002");
        mouse.setNombre("Mouse Logitech");
        mouse.setDescripcion("Mouse inalámbrico Logitech");
        mouse.setPrecio(new BigDecimal("25.00"));
        mouse.setStock(50);
        mouse.setStockMinimo(10);
        mouse.setCategoria(electro);
        mouse.setActivo(true);
        productoRepository.save(mouse);

        Producto teclado = new Producto();
        teclado.setCodigo("ELEC003");
        teclado.setNombre("Teclado Mecánico");
        teclado.setDescripcion("Teclado mecánico RGB");
        teclado.setPrecio(new BigDecimal("80.00"));
        teclado.setStock(30);
        teclado.setStockMinimo(5);
        teclado.setCategoria(electro);
        teclado.setActivo(true);
        productoRepository.save(teclado);

        Producto silla = new Producto();
        silla.setCodigo("HOG001");
        silla.setNombre("Silla de Oficina");
        silla.setDescripcion("Silla ergonómica para oficina");
        silla.setPrecio(new BigDecimal("150.00"));
        silla.setStock(15);
        silla.setStockMinimo(3);
        silla.setCategoria(hogar);
        silla.setActivo(true);
        productoRepository.save(silla);

        Producto camiseta = new Producto();
        camiseta.setCodigo("ROP001");
        camiseta.setNombre("Camiseta Polo");
        camiseta.setDescripcion("Camiseta polo 100% algodón");
        camiseta.setPrecio(new BigDecimal("20.00"));
        camiseta.setStock(100);
        camiseta.setStockMinimo(20);
        camiseta.setCategoria(ropa);
        camiseta.setActivo(true);
        productoRepository.save(camiseta);

        // Crear clientes
        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Carlos");
        cliente1.setApellido("Pérez");
        cliente1.setDni("12345678");
        cliente1.setTelefono("555-1234");
        cliente1.setEmail("carlos@email.com");
        cliente1.setDireccion("Av. Principal 123");
        cliente1.setActivo(true);
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Ana");
        cliente2.setApellido("García");
        cliente2.setDni("87654321");
        cliente2.setTelefono("555-5678");
        cliente2.setEmail("ana@email.com");
        cliente2.setDireccion("Calle Secundaria 456");
        cliente2.setActivo(true);
        clienteRepository.save(cliente2);

        Cliente cliente3 = new Cliente();
        cliente3.setNombre("Luis");
        cliente3.setApellido("Martínez");
        cliente3.setDni("11223344");
        cliente3.setTelefono("555-9012");
        cliente3.setEmail("luis@email.com");
        cliente3.setDireccion("Jr. Los Olivos 789");
        cliente3.setActivo(true);
        clienteRepository.save(cliente3);

        System.out.println("========================================");
        System.out.println("DATOS INICIALES CREADOS EXITOSAMENTE");
        System.out.println("========================================");
        System.out.println("USUARIOS:");
        System.out.println("- admin/admin123 (ADMIN)");
        System.out.println("- vendedor/vendedor123 (VENDEDOR)");
        System.out.println("- cajero/cajero123 (CAJERO)");
        System.out.println("========================================");
    }
}
