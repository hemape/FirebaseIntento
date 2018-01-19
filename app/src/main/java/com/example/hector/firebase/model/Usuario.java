package com.example.hector.firebase.model;

/**
 * Created by Hector on 15/12/2017.
 */

public class Usuario {



    private String usuario;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String correo;

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", correo='" + correo + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Usuario(String nombre, String apellidos, String direccion, String correo, String usuario) {

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.correo = correo;
        this.usuario = usuario;
    }
    public  Usuario(){

    }
}
