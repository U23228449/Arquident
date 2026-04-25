package pe.edu.utp.daoimpl;

import pe.edu.utp.dao.HorarioOdontologoDAO;
import pe.edu.utp.entity.HorarioOdontologo;
import pe.edu.utp.config.Conexion;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioOdontologoDAOImpl implements HorarioOdontologoDAO {

    @Override
    public boolean crear(HorarioOdontologo horario) {
        String sql = "INSERT INTO horarios_odontologos (id_odontologo, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, horario.getIdOdontologo());
            ps.setString(2, horario.getDiaSemana());
            ps.setTime(3, Time.valueOf(horario.getHoraInicio()));
            ps.setTime(4, Time.valueOf(horario.getHoraFin()));
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public HorarioOdontologo obtenerPorId(int id) {
        HorarioOdontologo horario = null;
        String sql = "SELECT h.*, u.nombre as nombre_odontologo FROM horarios_odontologos h " +
                    "INNER JOIN usuarios u ON h.id_odontologo = u.id_usuario WHERE h.id_horario = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    horario = mapearHorario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horario;
    }

    @Override
    public List<HorarioOdontologo> listarTodos() {
        List<HorarioOdontologo> horarios = new ArrayList<>();
        String sql = "SELECT h.*, u.nombre as nombre_odontologo FROM horarios_odontologos h " +
                    "INNER JOIN usuarios u ON h.id_odontologo = u.id_usuario ORDER BY u.nombre, h.dia_semana";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                horarios.add(mapearHorario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

    @Override
    public List<HorarioOdontologo> listarPorOdontologo(int idOdontologo) {
        List<HorarioOdontologo> horarios = new ArrayList<>();
        String sql = "SELECT h.*, u.nombre as nombre_odontologo FROM horarios_odontologos h " +
                    "INNER JOIN usuarios u ON h.id_odontologo = u.id_usuario WHERE h.id_odontologo = ? ORDER BY h.dia_semana";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idOdontologo);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    horarios.add(mapearHorario(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

    @Override
    public boolean actualizar(HorarioOdontologo horario) {
        String sql = "UPDATE horarios_odontologos SET dia_semana = ?, hora_inicio = ?, hora_fin = ? WHERE id_horario = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, horario.getDiaSemana());
            ps.setTime(2, Time.valueOf(horario.getHoraInicio()));
            ps.setTime(3, Time.valueOf(horario.getHoraFin()));
            ps.setInt(4, horario.getIdHorario());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM horarios_odontologos WHERE id_horario = ?";
        
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
    public boolean existeHorario(int idOdontologo, String diaSemana) {
        String sql = "SELECT COUNT(*) FROM horarios_odontologos WHERE id_odontologo = ? AND dia_semana = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idOdontologo);
            ps.setString(2, diaSemana);
            
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

    private HorarioOdontologo mapearHorario(ResultSet rs) throws SQLException {
        HorarioOdontologo horario = new HorarioOdontologo();
        horario.setIdHorario(rs.getInt("id_horario"));
        horario.setIdOdontologo(rs.getInt("id_odontologo"));
        horario.setDiaSemana(rs.getString("dia_semana"));
        horario.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
        horario.setHoraFin(rs.getTime("hora_fin").toLocalTime());
        horario.setNombreOdontologo(rs.getString("nombre_odontologo"));
        return horario;
    }
}
