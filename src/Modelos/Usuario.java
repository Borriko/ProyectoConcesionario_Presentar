package Modelos;

import java.sql.Timestamp;

public class Usuario {

    // ================= ATRIBUTOS =================

    private int id;
    private String nombre;
    private String password;
    private String email;
    private String urlImagen;
    private double dinero;
    private Timestamp fechaRegistro;

    // ================= CONSTRUCTOR COMPLETO =================

    public Usuario(int id,
                   String nombre,
                   String password,
                   String email,
                   String urlImagen,
                   double dinero,
                   Timestamp fechaRegistro) {

        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.urlImagen = urlImagen;
        this.dinero = dinero;
        this.fechaRegistro = fechaRegistro;
    }

    // ================= GETTERS Y SETTERS =================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }


    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }


    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // ================= TO STRING =================

    @Override
    public String toString() {
        return nombre + " - " + email;
    }
}