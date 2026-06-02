package Modelos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Coche {

    private int id;
    private String marca;
    private String modelo;
    private String tipoVehiculo;
    private int anio;
    private String color;
    private int kilometros;
    private String combustible;
    private String transmision;
    private Integer potencia; // puede ser null
    private double precio;
    private String descripcion;
    private String estado;
    private Timestamp fechaPublicacion;

    // ================= CONSTRUCTOR VACÍO =================
    public Coche() {}

    // ================= CONSTRUCTOR COMPLETO =================
    public Coche(int id, String marca, String modelo, String tipoVehiculo,
                 int anio, String color, int kilometros, String combustible,
                 String transmision, Integer potencia, double precio,
                 String descripcion, String estado, Timestamp fechaPublicacion) {

        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoVehiculo = tipoVehiculo;
        this.anio = anio;
        this.color = color;
        this.kilometros = kilometros;
        this.combustible = combustible;
        this.transmision = transmision;
        this.potencia = potencia;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaPublicacion = fechaPublicacion;
    }



    // ================= GETTERS Y SETTERS =================

    public int getId() { return id; }
    public void setId(int marca) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getKilometros() { return kilometros; }
    public void setKilometros(int kilometros) { this.kilometros = kilometros; }

    public String getCombustible() { return combustible; }
    public void setCombustible(String combustible) { this.combustible = combustible; }

    public String getTransmision() { return transmision; }
    public void setTransmision(String transmision) { this.transmision = transmision; }

    public Integer getPotencia() { return potencia; }
    public void setPotencia(Integer potencia) { this.potencia = potencia; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Timestamp getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(Timestamp fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    // ================= TO STRING =================
    @Override
    public String toString() {
        return marca + " " + modelo + " (" + anio + ") - " + precio + "€";
    }
}
