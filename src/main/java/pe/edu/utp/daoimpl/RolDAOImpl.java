package pe.edu.utp.daoimpl;

import pe.edu.utp.dao.RolDAO;
import pe.edu.utp.entity.Rol;
import pe.edu.utp.config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAOImpl implements RolDAO {

    @Override
    public List<Rol> listarTodos() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles ORDER BY nombre_rol";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Rol rol = mapearRol(rs);
                roles.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Rol obtenerPorId(int id) {
        Rol rol = null;
        String sql = "SELECT * FROM roles WHERE id_rol = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rol = mapearRol(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rol;
    }

    @Override
    public boolean crear(Rol rol) {
        String sql = "INSERT INTO roles (nombre_rol) VALUES (?)";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, rol.getNombreRol());            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Rol rol) {
        String sql = "UPDATE roles SET nombre_rol = ? WHERE id_rol = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, rol.getNombreRol());
            ps.setInt(2, rol.getIdRol());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM roles WHERE id_rol = ?";
        
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
    public boolean existeNombre(String nombreRol) {
        String sql = "SELECT COUNT(*) FROM roles WHERE nombre_rol = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nombreRol);
            
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

    private Rol mapearRol(ResultSet rs) throws SQLException {
        Rol rol = new Rol();
        rol.setIdRol(rs.getInt("id_rol"));
        rol.setNombreRol(rs.getString("nombre_rol"));
        return rol;
    }
}
