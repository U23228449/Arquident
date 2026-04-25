package pe.edu.utp.daoimpl;

import pe.edu.utp.dao.OdontologoServicioDAO;
import pe.edu.utp.entity.OdontologoServicio;
import pe.edu.utp.config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoServicioDAOImpl implements OdontologoServicioDAO {

    @Override
    public boolean crear(OdontologoServicio asignacion) {
        String sql = "INSERT INTO odontologo_servicios (id_odontologo, id_servicio) VALUES (?, ?)";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, asignacion.getIdOdontologo());
            ps.setInt(2, asignacion.getIdServicio());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OdontologoServicio> listarTodos() {
        List<OdontologoServicio> asignaciones = new ArrayList<>();
        String sql = "SELECT os.*, u.nombre as nombre_odontologo, s.nombre as nombre_servicio, s.precio " +
                    "FROM odontologo_servicios os " +
                    "INNER JOIN usuarios u ON os.id_odontologo = u.id_usuario " +
                    "INNER JOIN servicios s ON os.id_servicio = s.id_servicio " +
                    "ORDER BY u.nombre, s.nombre";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                asignaciones.add(mapearAsignacion(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaciones;
    }

    @Override
    public List<OdontologoServicio> listarPorOdontologo(int idOdontologo) {
        List<OdontologoServicio> asignaciones = new ArrayList<>();
        String sql = "SELECT os.*, u.nombre as nombre_odontologo, s.nombre as nombre_servicio, s.precio " +
                    "FROM odontologo_servicios os " +
                    "INNER JOIN usuarios u ON os.id_odontologo = u.id_usuario " +
                    "INNER JOIN servicios s ON os.id_servicio = s.id_servicio " +
                    "WHERE os.id_odontologo = ? ORDER BY s.nombre";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idOdontologo);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asignaciones.add(mapearAsignacion(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaciones;
    }

    @Override
    public List<OdontologoServicio> listarPorServicio(int idServicio) {
        List<OdontologoServicio> asignaciones = new ArrayList<>();
        String sql = "SELECT os.*, u.nombre as nombre_odontologo, s.nombre as nombre_servicio, s.precio " +
                    "FROM odontologo_servicios os " +
                    "INNER JOIN usuarios u ON os.id_odontologo = u.id_usuario " +
                    "INNER JOIN servicios s ON os.id_servicio = s.id_servicio " +
                    "WHERE os.id_servicio = ? ORDER BY u.nombre";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idServicio);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asignaciones.add(mapearAsignacion(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaciones;
    }

    @Override
    public boolean eliminar(int idOdontologo, int idServicio) {
        String sql = "DELETE FROM odontologo_servicios WHERE id_odontologo = ? AND id_servicio = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idOdontologo);
            ps.setInt(2, idServicio);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean existeAsignacion(int idOdontologo, int idServicio) {
        String sql = "SELECT COUNT(*) FROM odontologo_servicios WHERE id_odontologo = ? AND id_servicio = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idOdontologo);
            ps.setInt(2, idServicio);
            
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

    private OdontologoServicio mapearAsignacion(ResultSet rs) throws SQLException {
        OdontologoServicio asignacion = new OdontologoServicio();
        asignacion.setIdOdontologo(rs.getInt("id_odontologo"));
        asignacion.setIdServicio(rs.getInt("id_servicio"));
        asignacion.setNombreOdontologo(rs.getString("nombre_odontologo"));
        asignacion.setNombreServicio(rs.getString("nombre_servicio"));
        asignacion.setPrecio(rs.getBigDecimal("precio"));
        return asignacion;
    }
}
