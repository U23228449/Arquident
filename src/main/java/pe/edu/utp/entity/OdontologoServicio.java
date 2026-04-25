package pe.edu.utp.entity;

import java.math.BigDecimal;

public class OdontologoServicio {
    private int idOdontologo;
    private int idServicio;
    
    // Para joins
    private String nombreOdontologo;
    private String nombreServicio;
    private BigDecimal precio;

    // Constructores
    public OdontologoServicio() {}

    public OdontologoServicio(int idOdontologo, int idServicio) {
        this.idOdontologo = idOdontologo;
        this.idServicio = idServicio;
    }

    // Getters y Setters
    public int getIdOdontologo() { return idOdontologo; }
    public void setIdOdontologo(int idOdontologo) { this.idOdontologo = idOdontologo; }

    public int getIdServicio() { return idServicio; }
    public void setIdServicio(int idServicio) { this.idServicio = idServicio; }

    public String getNombreOdontologo() { return nombreOdontologo; }
    public void setNombreOdontologo(String nombreOdontologo) { this.nombreOdontologo = nombreOdontologo; }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}
