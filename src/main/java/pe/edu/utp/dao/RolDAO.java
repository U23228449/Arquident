package pe.edu.utp.dao;

import pe.edu.utp.entity.Rol;
import java.util.List;

public interface RolDAO {
    List<Rol> listarTodos();
    Rol obtenerPorId(int id);
    boolean crear(Rol rol);
    boolean actualizar(Rol rol);
    boolean eliminar(int id);
    boolean existeNombre(String nombreRol);
}
