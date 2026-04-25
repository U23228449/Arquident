package pe.edu.utp.dao;

import pe.edu.utp.entity.HorarioOdontologo;
import java.util.List;

public interface HorarioOdontologoDAO {
    boolean crear(HorarioOdontologo horario);
    HorarioOdontologo obtenerPorId(int id);
    List<HorarioOdontologo> listarTodos();
    List<HorarioOdontologo> listarPorOdontologo(int idOdontologo);
    boolean actualizar(HorarioOdontologo horario);
    boolean eliminar(int id);
    boolean existeHorario(int idOdontologo, String diaSemana);
}
