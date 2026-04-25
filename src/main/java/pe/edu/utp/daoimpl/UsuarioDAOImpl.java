package pe.edu.utp.daoimpl;

import pe.edu.utp.dao.UsuarioDAO;
import pe.edu.utp.entity.Usuario;
import pe.edu.utp.config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario autenticar(String correo, String contrasena) {
        Usuario usuario = null;
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.rol_id = r.id_rol " +
                    "WHERE u.correo = ? AND u.contrasena = ? AND u.cuenta_validada = TRUE";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, correo);
            ps.setString(2, contrasena);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuarioCompleto(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public List<Usuario> listarOdontologos() {
        List<Usuario> odontologos = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol_id = 3 AND cuenta_validada = TRUE";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Usuario odontologo = new Usuario();
                odontologo.setIdUsuario(rs.getInt("id_usuario"));
                odontologo.setNombre(rs.getString("nombre"));
                odontologo.setCorreo(rs.getString("correo"));
                odontologo.setTelefono(rs.getString("telefono"));
                odontologos.add(odontologo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return odontologos;
    }

    @Override
    public Usuario obtenerPorId(int id) {
        Usuario usuario = null;
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.rol_id = r.id_rol " +
                    "WHERE u.id_usuario = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuarioCompleto(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contrasena, rol_id, telefono, direccion, fecha_nacimiento, cuenta_validada) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, TRUE)";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getContrasena());
            ps.setInt(4, usuario.getRolId());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getDireccion());
            ps.setDate(7, usuario.getFechaNacimiento() != null ? 
                      Date.valueOf(usuario.getFechaNacimiento()) : null);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean registrarConValidacion(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contrasena, rol_id, telefono, direccion, " +
                    "fecha_nacimiento, dni, foto_dni_frontal, foto_dni_reverso, cuenta_validada, fecha_solicitud_registro) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, FALSE, NOW())";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getContrasena());
            ps.setInt(4, usuario.getRolId());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getDireccion());
            ps.setDate(7, usuario.getFechaNacimiento() != null ? 
                      Date.valueOf(usuario.getFechaNacimiento()) : null);
            ps.setString(8, usuario.getDni());
            ps.setString(9, usuario.getFotoDniFrontal());
            ps.setString(10, usuario.getFotoDniReverso());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean existeDni(String dni) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE dni = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, dni);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Usuario> listarUsuariosPendientesValidacion() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.rol_id = r.id_rol " +
                    "WHERE u.cuenta_validada = FALSE AND u.rol_id = 1 " +
                    "ORDER BY u.fecha_solicitud_registro DESC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(mapearUsuarioCompleto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public boolean aprobarUsuario(int idUsuario) {
        String sql = "UPDATE usuarios SET cuenta_validada = TRUE WHERE id_usuario = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean rechazarUsuario(int idUsuario) {
        // Eliminar el usuario rechazado
        String sql = "DELETE FROM usuarios WHERE id_usuario = ? AND cuenta_validada = FALSE";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, telefono = ?, direccion = ?, fecha_nacimiento = ? " +
                    "WHERE id_usuario = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getTelefono());
            ps.setString(3, usuario.getDireccion());
            ps.setDate(4, usuario.getFechaNacimiento() != null ? 
                      Date.valueOf(usuario.getFechaNacimiento()) : null);
            ps.setInt(5, usuario.getIdUsuario());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Usuario> listarPorRol(int rolId) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.rol_id = r.id_rol " +
                    "WHERE u.rol_id = ? AND u.cuenta_validada = TRUE ORDER BY u.nombre";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, rolId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    @Override
    public boolean crear(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, telefono, contrasena, rol_id, cuenta_validada) VALUES (?, ?, ?, ?, ?, TRUE)";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getContrasena());
            ps.setInt(5, usuario.getRolId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario obtenerPorCorreo(String correo) {
        Usuario usuario = null;
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.rol_id = r.id_rol WHERE u.correo = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, correo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuarioCompleto(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.rol_id = r.id_rol " +
                    "WHERE u.cuenta_validada = TRUE ORDER BY u.nombre";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public List<Usuario> listarPorRol(String rol) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.rol_id = r.id_rol " +
                    "WHERE r.nombre_rol = ? AND u.cuenta_validada = TRUE ORDER BY u.nombre";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, rol);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public boolean actualizarPerfil(Usuario usuario) {
        String sql = "UPDATE usuarios SET correo = ?, telefono = ? WHERE id_usuario = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getTelefono());
            ps.setInt(3, usuario.getIdUsuario());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cambiarPassword(int idUsuario, String nuevaPassword) {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE id_usuario = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nuevaPassword);
            ps.setInt(2, idUsuario);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean existeCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, correo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Usuario validarCredenciales(String correo, String password) {
        Usuario usuario = null;
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u " +
                    "INNER JOIN roles r ON u.rol_id = r.id_rol " +
                    "WHERE u.correo = ? AND u.contrasena = ? AND u.cuenta_validada = TRUE";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, correo);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setTelefono(rs.getString("telefono"));
        usuario.setContrasena(rs.getString("contrasena"));
        usuario.setRolId(rs.getInt("rol_id"));
        usuario.setNombreRol(rs.getString("nombre_rol"));
        
        // Campos de validación
        usuario.setDni(rs.getString("dni"));
        usuario.setCuentaValidada(rs.getBoolean("cuenta_validada"));
        
        return usuario;
    }

    private Usuario mapearUsuarioCompleto(ResultSet rs) throws SQLException {
        Usuario usuario = mapearUsuario(rs);
        usuario.setDireccion(rs.getString("direccion"));
        usuario.setFotoDniFrontal(rs.getString("foto_dni_frontal"));
        usuario.setFotoDniReverso(rs.getString("foto_dni_reverso"));
        
        if (rs.getDate("fecha_nacimiento") != null) {
            usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        }
        if (rs.getTimestamp("fecha_registro") != null) {
            usuario.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        }
        if (rs.getTimestamp("fecha_solicitud_registro") != null) {
            usuario.setFechaSolicitudRegistro(rs.getTimestamp("fecha_solicitud_registro").toLocalDateTime());
        }
        
        return usuario;
    }
}
