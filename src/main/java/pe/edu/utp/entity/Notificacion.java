package pe.edu.utp.entity;

import java.time.LocalDateTime;

public class Notificacion {
    private int idNotificacion;
    private int idUsuario;
    private String mensaje;
    private boolean leido;
    private LocalDateTime fecha;

    // Constructores
    public Notificacion() {}

    public Notificacion(int idUsuario, String mensaje) {
        this.idUsuario = idUsuario;
        this.mensaje = mensaje;
        this.leido = false;
    }

    // Getters y Setters
    public int getIdNotificacion() { return idNotificacion; }
    public void setIdNotificacion(int idNotificacion) { this.idNotificacion = idNotificacion; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public boolean isLeido() { return leido; }
    public void setLeido(boolean leido) { this.leido = leido; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
