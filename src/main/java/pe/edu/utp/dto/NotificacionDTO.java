package pe.edu.utp.dto;

import pe.edu.utp.entity.Notificacion;
import java.time.LocalDateTime;

public class NotificacionDTO {
    private int idNotificacion;
    private int idUsuario;
    private String mensaje;
    private boolean leido;
    private LocalDateTime fecha;
    private String nombreUsuario;

    // Constructores
    public NotificacionDTO() {}

    public NotificacionDTO(Notificacion notificacion, String nombreUsuario) {
        this.idNotificacion = notificacion.getIdNotificacion();
        this.idUsuario = notificacion.getIdUsuario();
        this.mensaje = notificacion.getMensaje();
        this.leido = notificacion.isLeido();
        this.fecha = notificacion.getFecha();
        this.nombreUsuario = nombreUsuario;
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

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}
