package pe.edu.utp.entity;

import java.math.BigDecimal;

public class Servicio {
    private int idServicio;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private boolean requiereConsulta;
    
    // Constructor vacío
    public Servicio() {}
    
    // Constructor completo
    public Servicio(int idServicio, String nombre, String descripcion, BigDecimal precio, boolean requiereConsulta) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.requiereConsulta = requiereConsulta;
    }
    
    // Getters y Setters
    public int getIdServicio() {
        return idServicio;
    }
    
    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public BigDecimal getPrecio() {
        return precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public boolean isRequiereConsulta() {
        return requiereConsulta;
    }
    
    public void setRequiereConsulta(boolean requiereConsulta) {
        this.requiereConsulta = requiereConsulta;
    }
    
    @Override
    public String toString() {
        return "Servicio{" +
                "idServicio=" + idServicio +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", requiereConsulta=" + requiereConsulta +
                '}';
    }
}
