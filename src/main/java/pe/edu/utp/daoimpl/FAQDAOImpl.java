package pe.edu.utp.daoimpl;

import pe.edu.utp.dao.FAQDAO;
import pe.edu.utp.entity.FAQ;
import pe.edu.utp.config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FAQDAOImpl implements FAQDAO {

    @Override
    public List<FAQ> listarTodas() {
        List<FAQ> faqs = new ArrayList<>();
        String sql = "SELECT f.*, u.nombre as nombre_usuario_creador FROM faq f " +
                    "LEFT JOIN usuarios u ON f.id_usuario_creador = u.id_usuario " +
                    "ORDER BY f.fecha_creacion DESC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                FAQ faq = mapearFAQCompleta(rs);
                faqs.add(faq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faqs;
    }

    @Override
    public List<FAQ> listarPorUsuario(int idUsuario) {
        List<FAQ> faqs = new ArrayList<>();
        String sql = "SELECT f.*, u.nombre as nombre_usuario_creador FROM faq f " +
                    "LEFT JOIN usuarios u ON f.id_usuario_creador = u.id_usuario " +
                    "WHERE f.id_usuario_creador = ? ORDER BY f.fecha_creacion DESC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FAQ faq = mapearFAQCompleta(rs);
                    faqs.add(faq);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faqs;
    }

    @Override
    public FAQ obtenerPorId(int id) {
        FAQ faq = null;
        String sql = "SELECT f.*, u.nombre as nombre_usuario_creador FROM faq f " +
                    "LEFT JOIN usuarios u ON f.id_usuario_creador = u.id_usuario " +
                    "WHERE f.id_pregunta = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    faq = mapearFAQCompleta(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faq;
    }

    @Override
    public boolean crear(FAQ faq) {
        String sql = "INSERT INTO faq (pregunta, respuesta, id_usuario_creador) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, faq.getPregunta());
            ps.setString(2, faq.getRespuesta());
            ps.setInt(3, faq.getIdUsuarioCreador());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(FAQ faq) {
        String sql = "UPDATE faq SET pregunta = ?, respuesta = ? WHERE id_pregunta = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, faq.getPregunta());
            ps.setString(2, faq.getRespuesta());
            ps.setInt(3, faq.getIdPregunta());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM faq WHERE id_pregunta = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private FAQ mapearFAQCompleta(ResultSet rs) throws SQLException {
        FAQ faq = new FAQ();
        faq.setIdPregunta(rs.getInt("id_pregunta"));
        faq.setPregunta(rs.getString("pregunta"));
        faq.setRespuesta(rs.getString("respuesta"));
        faq.setIdUsuarioCreador(rs.getInt("id_usuario_creador"));
        faq.setNombreUsuarioCreador(rs.getString("nombre_usuario_creador"));
        
        if (rs.getTimestamp("fecha_creacion") != null) {
            faq.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
        }
        if (rs.getTimestamp("fecha_actualizacion") != null) {
            faq.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime());
        }
        
        return faq;
    }
}
