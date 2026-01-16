package com.wam.sistema_ventas.dto;

import lombok.Data;


@Data
public class LoginResponse {
    private String token;
    private String tipo = "Bearer";
    private String username;
    private String nombre;
    private String rol;

    // Constructor personalizado
    public LoginResponse(String token, String tipo, String username, String nombre, String rol) {
        this.token = token;
        this.tipo = tipo;
        this.username = username;
        this.nombre = nombre;
        this.rol = rol;
    }

    public LoginResponse(){
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
