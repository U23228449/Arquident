package pe.edu.utp.daoimpl;

import pe.edu.utp.dao.NotificacionDAO;
import pe.edu.utp.entity.Notificacion;
import pe.edu.utp.dto.NotificacionDTO;
import pe.edu.utp.config.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDAOImpl implements NotificacionDAO {

    @Override
    public boolean crear(Notificacion notificacion) {
        String sql = "INSERT INTO notificaciones (id_usuario, mensaje, leido, fecha) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, notificacion.getIdUsuario());
            ps.setString(2, notificacion.getMensaje());
            ps.setBoolean(3, notificacion.isLeido());
            ps.setTimestamp(4, Timestamp.valueOf(notificacion.getFecha() != null ? notificacion.getFecha() : LocalDateTime.now()));
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Notificacion obtenerPorId(int id) {
        Notificacion notificacion = null;
        String sql = "SELECT * FROM notificaciones WHERE id_notificacion = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    notificacion = mapearNotificacion(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notificacion;
    }

    @Override
    public List<Notificacion> listarTodas() {
        List<Notificacion> notificaciones = new ArrayList<>();
        String sql = "SELECT * FROM notificaciones ORDER BY fecha DESC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                notificaciones.add(mapearNotificacion(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notificaciones;
    }

    @Override
    public List<NotificacionDTO> listarTodasConUsuario() {
        List<NotificacionDTO> notificaciones = new ArrayList<>();
        String sql = "SELECT n.*, u.nombre as nombre_usuario FROM notificaciones n " +
                    "INNER JOIN usuarios u ON n.id_usuario = u.id_usuario " +
                    "ORDER BY n.fecha DESC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Notificacion notificacion = mapearNotificacion(rs);
                String nombreUsuario = rs.getString("nombre_usuario");
                NotificacionDTO dto = new NotificacionDTO(notificacion, nombreUsuario);
                notificaciones.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notificaciones;
    }

    @Override
    public List<Notificacion> listarPorUsuario(int idUsuario) {
        List<Notificacion> notificaciones = new ArrayList<>();
        String sql = "SELECT * FROM notificaciones WHERE id_usuario = ? ORDER BY fecha DESC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    notificaciones.add(mapearNotificacion(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notificaciones;
    }

    @Override
    public boolean actualizar(Notificacion notificacion) {
        String sql = "UPDATE notificaciones SET mensaje = ?, leido = ? WHERE id_notificacion = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, notificacion.getMensaje());
            ps.setBoolean(2, notificacion.isLeido());
            ps.setInt(3, notificacion.getIdNotificacion());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM notificaciones WHERE id_notificacion = ?";
        
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
    public boolean marcarComoLeida(int id) {
        String sql = "UPDATE notificaciones SET leido = true WHERE id_notificacion = ?";
        
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
    public boolean marcarTodasComoLeidas(int idUsuario) {
        String sql = "UPDATE notificaciones SET leido = true WHERE id_usuario = ? AND leido = false";
        
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
    public List<Notificacion> listarNoLeidasPorUsuario(int idUsuario) {
        List<Notificacion> notificaciones = new ArrayList<>();
        String sql = "SELECT * FROM notificaciones WHERE id_usuario = ? AND leido = false ORDER BY fecha DESC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    notificaciones.add(mapearNotificacion(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notificaciones;
    }

    @Override
    public int contarNoLeidas(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM notificaciones WHERE id_usuario = ? AND leido = false";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Notificacion mapearNotificacion(ResultSet rs) throws SQLException {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(rs.getInt("id_notificacion"));
        notificacion.setIdUsuario(rs.getInt("id_usuario"));
        notificacion.setMensaje(rs.getString("mensaje"));
        notificacion.setLeido(rs.getBoolean("leido"));
        
        Timestamp timestamp = rs.getTimestamp("fecha");
        if (timestamp != null) {
            notificacion.setFecha(timestamp.toLocalDateTime());
        }
        
        return notificacion;
    }
}
