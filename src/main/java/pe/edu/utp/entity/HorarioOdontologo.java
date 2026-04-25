package pe.edu.utp.entity;

import java.time.LocalTime;

public class HorarioOdontologo {
    private int idHorario;
    private int idOdontologo;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    
    // Para joins
    private String nombreOdontologo;

    // Constructores
    public HorarioOdontologo() {}

    public HorarioOdontologo(int idOdontologo, String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        this.idOdontologo = idOdontologo;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y Setters
    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }

    public int getIdOdontologo() { return idOdontologo; }
    public void setIdOdontologo(int idOdontologo) { this.idOdontologo = idOdontologo; }

    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public String getNombreOdontologo() { return nombreOdontologo; }
    public void setNombreOdontologo(String nombreOdontologo) { this.nombreOdontologo = nombreOdontologo; }
}
