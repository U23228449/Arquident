package pe.edu.utp.entity;

import java.time.LocalDateTime;

public class FAQ {
    private int idPregunta;
    private String pregunta;
    private String respuesta;
    private int idUsuarioCreador;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    // Para joins
    private String nombreUsuarioCreador;

    // Constructores
    public FAQ() {}

    public FAQ(String pregunta, String respuesta, int idUsuarioCreador) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.idUsuarioCreador = idUsuarioCreador;
    }

    // Getters y Setters
    public int getIdPregunta() { return idPregunta; }
    public void setIdPregunta(int idPregunta) { this.idPregunta = idPregunta; }

    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public int getIdUsuarioCreador() { return idUsuarioCreador; }
    public void setIdUsuarioCreador(int idUsuarioCreador) { this.idUsuarioCreador = idUsuarioCreador; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public String getNombreUsuarioCreador() { return nombreUsuarioCreador; }
    public void setNombreUsuarioCreador(String nombreUsuarioCreador) { this.nombreUsuarioCreador = nombreUsuarioCreador; }
}
