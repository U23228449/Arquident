package pe.edu.utp.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class Cita {
    private int idCita;
    private int idPaciente;
    private int idOdontologo;
    private int idServicio;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado; // reservada, sin_pagar, confirmada, en_atencion, finalizada, cancelada, derivada_sin_pagar
    private LocalDateTime horaInicioReal;
    private LocalDateTime horaFinReal;
    private String observaciones;
    private String receta;
    private LocalDateTime fechaCreacion;
    
    // Para joins
    private String nombrePaciente;
    private String nombreOdontologo;
    private String nombreServicio;
    private String telefonoPaciente;
    private double precioServicio;

    // Constructores
    public Cita() {}

    public Cita(int idPaciente, int idOdontologo, int idServicio, 
                LocalDate fecha, LocalTime hora, String observaciones) {
        this.idPaciente = idPaciente;
        this.idOdontologo = idOdontologo;
        this.idServicio = idServicio;
        this.fecha = fecha;
        this.hora = hora;
        this.observaciones = observaciones;
        this.estado = "sin_pagar";
    }

    // Getters y Setters
    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public int getIdOdontologo() { return idOdontologo; }
    public void setIdOdontologo(int idOdontologo) { this.idOdontologo = idOdontologo; }

    public int getIdServicio() { return idServicio; }
    public void setIdServicio(int idServicio) { this.idServicio = idServicio; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getHoraInicioReal() { return horaInicioReal; }
    public void setHoraInicioReal(LocalDateTime horaInicioReal) { this.horaInicioReal = horaInicioReal; }

    public LocalDateTime getHoraFinReal() { return horaFinReal; }
    public void setHoraFinReal(LocalDateTime horaFinReal) { this.horaFinReal = horaFinReal; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getReceta() { return receta; }
    public void setReceta(String receta) { this.receta = receta; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }

    public String getNombreOdontologo() { return nombreOdontologo; }
    public void setNombreOdontologo(String nombreOdontologo) { this.nombreOdontologo = nombreOdontologo; }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }

    public String getTelefonoPaciente() { return telefonoPaciente; }
    public void setTelefonoPaciente(String telefonoPaciente) { this.telefonoPaciente = telefonoPaciente; }

    public double getPrecioServicio() { return precioServicio; }
    public void setPrecioServicio(double precioServicio) { this.precioServicio = precioServicio; }
}
