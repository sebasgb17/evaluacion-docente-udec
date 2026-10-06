package com.udec.evaluacion.model;

public class Usuario {
    private String correo;
    private String clave;
    private String nombre;
    private Rol rol;

    public Usuario() {}

    public Usuario(String correo, String clave, String nombre, Rol rol) {
        this.correo = correo;
        this.clave = clave;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}