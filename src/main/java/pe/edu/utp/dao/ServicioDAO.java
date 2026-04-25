package pe.edu.utp.dao;

import pe.edu.utp.entity.Servicio;
import java.util.List;

public interface ServicioDAO {
    List<Servicio> listarTodos();
    List<Servicio> listarDisponiblesParaReserva();
    List<Servicio> listarSinConsulta();
    List<Servicio> listarConConsulta();
    List<Servicio> listarPorOdontologo(int idOdontologo);
    List<Servicio> listarDestacados();
    Servicio obtenerPorId(int id);
    boolean crear(Servicio servicio);
    boolean actualizar(Servicio servicio);
    boolean eliminar(int id);
    int obtenerDuracionPorDefecto(int idServicio);
}
