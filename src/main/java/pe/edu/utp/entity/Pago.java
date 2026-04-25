package pe.edu.utp.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class Pago {
    private int idPago;
    private int idCita;
    private double monto;
    private String metodoPago;
    private String estadoPago;
    private LocalDateTime fechaPago;
    private LocalDateTime fechaLimitePago;
    
    // Campos adicionales para JSP (Date para fmt:formatDate)
    private Date fechaPagoDate;
    private Date fechaLimitePagoDate;
    
    // Para joins
    private String nombrePaciente;

    // Constructores
    public Pago() {}

    public Pago(int idCita, double monto, String estadoPago) {
        this.idCita = idCita;
        this.monto = monto;
        this.estadoPago = estadoPago;
    }

    // Getters y Setters
    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { 
        this.fechaPago = fechaPago;
        // Convertir a Date para JSP
        if (fechaPago != null) {
            this.fechaPagoDate = java.sql.Timestamp.valueOf(fechaPago);
        }
    }

    public LocalDateTime getFechaLimitePago() { return fechaLimitePago; }
    public void setFechaLimitePago(LocalDateTime fechaLimitePago) { 
        this.fechaLimitePago = fechaLimitePago;
        // Convertir a Date para JSP
        if (fechaLimitePago != null) {
            this.fechaLimitePagoDate = java.sql.Timestamp.valueOf(fechaLimitePago);
        }
    }

    // Getters para Date (para JSP)
    public Date getFechaPagoDate() { return fechaPagoDate; }
    public void setFechaPagoDate(Date fechaPagoDate) { this.fechaPagoDate = fechaPagoDate; }

    public Date getFechaLimitePagoDate() { return fechaLimitePagoDate; }
    public void setFechaLimitePagoDate(Date fechaLimitePagoDate) { this.fechaLimitePagoDate = fechaLimitePagoDate; }

    // GETTER Y SETTER PARA nombrePaciente - NECESARIOS PARA EL JSP
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }
}
