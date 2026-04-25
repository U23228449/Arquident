package pe.edu.utp.dao;

import pe.edu.utp.entity.OdontologoServicio;
import java.util.List;

public interface OdontologoServicioDAO {
    boolean crear(OdontologoServicio asignacion);
    List<OdontologoServicio> listarTodos();
    List<OdontologoServicio> listarPorOdontologo(int idOdontologo);
    List<OdontologoServicio> listarPorServicio(int idServicio);
    boolean eliminar(int idOdontologo, int idServicio);
    boolean existeAsignacion(int idOdontologo, int idServicio);
}
