package pe.edu.utp.dao;

import pe.edu.utp.entity.Notificacion;
import java.util.List;
import pe.edu.utp.dto.NotificacionDTO;

public interface NotificacionDAO {
    boolean crear(Notificacion notificacion);
    Notificacion obtenerPorId(int id);
    List<Notificacion> listarTodas();
    List<Notificacion> listarPorUsuario(int idUsuario);
    boolean actualizar(Notificacion notificacion);
    boolean eliminar(int id);
    boolean marcarComoLeida(int id);
    int contarNoLeidas(int idUsuario);
    List<Notificacion> listarNoLeidasPorUsuario(int idUsuario);
    boolean marcarTodasComoLeidas(int idUsuario);
    List<NotificacionDTO> listarTodasConUsuario();


}
