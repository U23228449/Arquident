package pe.edu.utp.dao;

import pe.edu.utp.entity.FAQ;
import java.util.List;

public interface FAQDAO {
    List<FAQ> listarTodas();
    List<FAQ> listarPorUsuario(int idUsuario);
    FAQ obtenerPorId(int id);
    boolean crear(FAQ faq);
    boolean actualizar(FAQ faq);
    boolean eliminar(int id);
}
