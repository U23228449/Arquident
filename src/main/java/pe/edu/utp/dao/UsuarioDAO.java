package pe.edu.utp.dao;

import pe.edu.utp.entity.Usuario;
import java.util.List;

public interface UsuarioDAO {
    Usuario autenticar(String correo, String contrasena);
    List<Usuario> listarOdontologos();
    Usuario obtenerPorId(int id);
    boolean registrar(Usuario usuario);
    boolean actualizar(Usuario usuario);
    List<Usuario> listarPorRol(int rolId);
    boolean crear(Usuario usuario);
    Usuario obtenerPorCorreo(String correo);
    List<Usuario> listarTodos();
    List<Usuario> listarPorRol(String rol);
    boolean actualizarPerfil(Usuario usuario);
    boolean cambiarPassword(int idUsuario, String nuevaPassword);
    boolean eliminar(int id);
    boolean existeCorreo(String correo);
    Usuario validarCredenciales(String correo, String password);
    
    // Nuevos métodos para validación
    boolean existeDni(String dni);
    boolean registrarConValidacion(Usuario usuario);
    List<Usuario> listarUsuariosPendientesValidacion();
    boolean aprobarUsuario(int idUsuario);
    boolean rechazarUsuario(int idUsuario);
}
