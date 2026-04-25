package pe.edu.utp.daoimpl;

import pe.edu.utp.dao.CitaDAO;
import pe.edu.utp.entity.Cita;
import pe.edu.utp.config.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CitaDAOImpl implements CitaDAO {

    @Override
    public boolean crear(Cita cita) {
        String sql = "INSERT INTO citas (id_paciente, id_odontologo, id_servicio, fecha, hora, estado, observaciones) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdOdontologo());
            ps.setInt(3, cita.getIdServicio());
            ps.setDate(4, Date.valueOf(cita.getFecha()));
            ps.setTime(5, Time.valueOf(cita.getHora()));
            ps.setString(6, cita.getEstado());
            ps.setString(7, cita.getObservaciones());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Cita obtenerPorId(int id) {
        Cita cita = null;
        String sql = "SELECT c.*, u1.nombre as nombre_paciente, u2.nombre as nombre_odontologo, "
                + "s.nombre as nombre_servicio, s.precio as precio_servicio FROM citas c "
                + "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario "
                + "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario "
                + "INNER JOIN servicios s ON c.id_servicio = s.id_servicio "
                + "WHERE c.id_cita = ?";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cita = mapearCitaCompleta(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cita;
    }

    @Override
    public List<Cita> listarPorPaciente(int idPaciente) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, u1.nombre as nombre_paciente, u2.nombre as nombre_odontologo, "
                + "s.nombre as nombre_servicio, s.precio as precio_servicio FROM citas c "
                + "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario "
                + "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario "
                + "INNER JOIN servicios s ON c.id_servicio = s.id_servicio "
                + "WHERE c.id_paciente = ? ORDER BY c.fecha DESC, c.hora DESC";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    citas.add(mapearCitaCompleta(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    @Override
    public List<Cita> listarPorOdontologo(int idOdontologo) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, u1.nombre as nombre_paciente, u1.telefono as telefono_paciente, "
                + "u2.nombre as nombre_odontologo, s.nombre as nombre_servicio, s.precio as precio_servicio FROM citas c "
                + "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario "
                + "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario "
                + "INNER JOIN servicios s ON c.id_servicio = s.id_servicio "
                + "WHERE c.id_odontologo = ? ORDER BY c.fecha DESC, c.hora DESC";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idOdontologo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cita cita = mapearCitaCompleta(rs);
                    cita.setTelefonoPaciente(rs.getString("telefono_paciente"));
                    citas.add(cita);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    @Override
    public List<Cita> listarPorFecha(String fecha) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, u1.nombre as nombre_paciente, u2.nombre as nombre_odontologo, "
                + "s.nombre as nombre_servicio, s.precio as precio_servicio FROM citas c "
                + "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario "
                + "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario "
                + "INNER JOIN servicios s ON c.id_servicio = s.id_servicio "
                + "WHERE c.fecha = ? ORDER BY c.hora";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fecha));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    citas.add(mapearCitaCompleta(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    @Override
    public List<Cita> listarTodas() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, u1.nombre as nombre_paciente, u2.nombre as nombre_odontologo, "
                + "s.nombre as nombre_servicio, s.precio as precio_servicio FROM citas c "
                + "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario "
                + "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario "
                + "INNER JOIN servicios s ON c.id_servicio = s.id_servicio "
                + "ORDER BY c.fecha DESC, c.hora DESC";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                citas.add(mapearCitaCompleta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    @Override
    public boolean actualizar(Cita cita) {
        String sql = "UPDATE citas SET id_odontologo = ?, id_servicio = ?, fecha = ?, hora = ?, estado = ?, observaciones = ?, receta = ? "
                + "WHERE id_cita = ?";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cita.getIdOdontologo());
            ps.setInt(2, cita.getIdServicio());
            ps.setDate(3, Date.valueOf(cita.getFecha()));
            ps.setTime(4, Time.valueOf(cita.getHora()));
            ps.setString(5, cita.getEstado());
            ps.setString(6, cita.getObservaciones());
            ps.setString(7, cita.getReceta());
            ps.setInt(8, cita.getIdCita());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM citas WHERE id_cita = ?";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Cita> obtenerCitasPendientesPago(int idPaciente) {
        return listarPendientesPagoPorPaciente(idPaciente);
    }

    @Override
    public List<Cita> obtenerProximasCitas(int idPaciente) {
        return listarProximasPorPaciente(idPaciente);
    }

    @Override
    public List<Cita> obtenerHistorialCitas(int idPaciente) {
        return listarHistorialPorPaciente(idPaciente);
    }

    @Override
    public boolean existeReservaActiva(int idPaciente) {
        return tieneReservaActiva(idPaciente);
    }

    @Override
    public boolean cambiarEstado(int idCita, String nuevoEstado) {
        String sql = "UPDATE citas SET estado = ? WHERE id_cita = ?";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idCita);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Cita> listarProximasPorPaciente(int idPaciente) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, u1.nombre as nombre_paciente, u2.nombre as nombre_odontologo, "
                + "s.nombre as nombre_servicio, s.precio as precio_servicio FROM citas c "
                + "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario "
                + "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario "
                + "INNER JOIN servicios s ON c.id_servicio = s.id_servicio "
                + "WHERE c.id_paciente = ? AND c.fecha >= CURDATE() "
                + "AND c.estado IN ('confirmada', 'reservada', 'sin_pagar') "
                + "ORDER BY c.fecha ASC, c.hora ASC";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    citas.add(mapearCitaCompleta(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    @Override
    public List<Cita> listarPendientesPagoPorPaciente(int idPaciente) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, u1.nombre as nombre_paciente, u2.nombre as nombre_odontologo, "
                + "s.nombre as nombre_servicio, s.precio as precio_servicio FROM citas c "
                + "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario "
                + "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario "
                + "INNER JOIN servicios s ON c.id_servicio = s.id_servicio "
                + "WHERE c.id_paciente = ? AND (c.estado = 'sin_pagar' OR c.estado = 'derivada_sin_pagar') "
                + "ORDER BY c.fecha_creacion ASC";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    citas.add(mapearCitaCompleta(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    @Override
    public List<Cita> listarHistorialPorPaciente(int idPaciente) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, u1.nombre as nombre_paciente, u2.nombre as nombre_odontologo, "
                + "s.nombre as nombre_servicio, s.precio as precio_servicio FROM citas c "
                + "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario "
                + "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario "
                + "INNER JOIN servicios s ON c.id_servicio = s.id_servicio "
                + "WHERE c.id_paciente = ? AND (c.estado = 'finalizada' OR c.estado = 'cancelada' OR c.fecha < CURDATE()) "
                + "ORDER BY c.fecha DESC, c.hora DESC LIMIT 10";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    citas.add(mapearCitaCompleta(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    @Override
    public boolean tieneReservaActiva(int idPaciente) {
        String sql = "SELECT COUNT(*) FROM citas WHERE id_paciente = ? AND estado IN ('sin_pagar', 'confirmada', 'derivada_sin_pagar', 'reservada')";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);

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
    public boolean existeCitaEnHorario(int idOdontologo, LocalDate fecha, LocalTime hora) {
        String sql = "SELECT COUNT(*) FROM citas WHERE id_odontologo = ? AND fecha = ? AND hora = ? AND estado != 'cancelada'";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idOdontologo);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setTime(3, Time.valueOf(hora));

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

    private Cita mapearCitaCompleta(ResultSet rs) throws SQLException {
        Cita cita = new Cita();
        cita.setIdCita(rs.getInt("id_cita"));
        cita.setIdPaciente(rs.getInt("id_paciente"));
        cita.setIdOdontologo(rs.getInt("id_odontologo"));
        cita.setIdServicio(rs.getInt("id_servicio"));
        cita.setFecha(rs.getDate("fecha").toLocalDate());
        cita.setHora(rs.getTime("hora").toLocalTime());
        cita.setEstado(rs.getString("estado"));
        cita.setObservaciones(rs.getString("observaciones"));
        cita.setReceta(rs.getString("receta"));

        // Datos adicionales de los joins
        cita.setNombrePaciente(rs.getString("nombre_paciente"));
        cita.setNombreOdontologo(rs.getString("nombre_odontologo"));
        cita.setNombreServicio(rs.getString("nombre_servicio"));
        cita.setPrecioServicio(rs.getDouble("precio_servicio"));

        // Campos opcionales con timestamps
        try {
            if (rs.getTimestamp("hora_inicio_real") != null) {
                cita.setHoraInicioReal(rs.getTimestamp("hora_inicio_real").toLocalDateTime());
            }
        } catch (SQLException e) {
            // Campo no existe en esta consulta, ignorar
        }

        try {
            if (rs.getTimestamp("hora_fin_real") != null) {
                cita.setHoraFinReal(rs.getTimestamp("hora_fin_real").toLocalDateTime());
            }
        } catch (SQLException e) {
            // Campo no existe en esta consulta, ignorar
        }

        try {
            if (rs.getTimestamp("fecha_creacion") != null) {
                cita.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
            }
        } catch (SQLException e) {
            // Campo no existe en esta consulta, ignorar
        }

        return cita;
    }

    @Override
    public int obtenerUltimaIdCita(int idPaciente) {
        String sql = "SELECT id_cita FROM citas WHERE id_paciente = ? ORDER BY id_cita DESC LIMIT 1";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_cita");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Métodos adicionales para CitaDAOImpl.java
    @Override
    public List<Cita> listarCitasDelDiaPorOdontologo(int idOdontologo, LocalDate fecha) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, u1.nombre as nombre_paciente, u1.telefono as telefono_paciente, "
                + "u2.nombre as nombre_odontologo, s.nombre as nombre_servicio, s.precio as precio_servicio "
                + "FROM citas c "
                + "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario "
                + "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario "
                + "INNER JOIN servicios s ON c.id_servicio = s.id_servicio "
                + "WHERE c.id_odontologo = ? AND c.fecha = ? "
                + "AND c.estado IN ('confirmada', 'sin_pagar', 'en_atencion', 'reservada') "
                + "ORDER BY c.hora ASC";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idOdontologo);
            ps.setDate(2, Date.valueOf(fecha));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cita cita = mapearCitaCompleta(rs);
                    cita.setTelefonoPaciente(rs.getString("telefono_paciente"));
                    citas.add(cita);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    @Override
    public boolean marcarInicioAtencion(int idCita, LocalDateTime horaInicio) {
        String sql = "UPDATE citas SET hora_inicio_real = ?, estado = 'en_atencion' WHERE id_cita = ?";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(horaInicio));
            ps.setInt(2, idCita);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean finalizarAtencion(int idCita, String observaciones, String receta, LocalDateTime horaFin) {
        String sql = "UPDATE citas SET observaciones = ?, receta = ?, hora_fin_real = ?, estado = 'finalizada' WHERE id_cita = ?";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, observaciones);
            ps.setString(2, receta);
            ps.setTimestamp(3, Timestamp.valueOf(horaFin));
            ps.setInt(4, idCita);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Cita> listarCitasPorRangoFechas(int idOdontologo, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, u1.nombre as nombre_paciente, u1.telefono as telefono_paciente, "
                + "u2.nombre as nombre_odontologo, s.nombre as nombre_servicio, s.precio as precio_servicio "
                + "FROM citas c "
                + "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario "
                + "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario "
                + "INNER JOIN servicios s ON c.id_servicio = s.id_servicio "
                + "WHERE c.id_odontologo = ? AND c.fecha BETWEEN ? AND ? "
                + "AND c.estado IN ('confirmada', 'sin_pagar', 'en_atencion', 'reservada', 'finalizada') "
                + "ORDER BY c.fecha ASC, c.hora ASC";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idOdontologo);
            ps.setDate(2, Date.valueOf(fechaInicio));
            ps.setDate(3, Date.valueOf(fechaFin));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cita cita = mapearCitaCompleta(rs);
                    cita.setTelefonoPaciente(rs.getString("telefono_paciente"));
                    citas.add(cita);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }
// Métodos adicionales para CitaDAOImpl.java

@Override
public List<Cita> listarTodasCitasDelDia(LocalDate fecha) {
    List<Cita> citas = new ArrayList<>();
    String sql = "SELECT c.*, u1.nombre as nombre_paciente, u1.telefono as telefono_paciente, " +
                "u2.nombre as nombre_odontologo, s.nombre as nombre_servicio, s.precio as precio_servicio " +
                "FROM citas c " +
                "INNER JOIN usuarios u1 ON c.id_paciente = u1.id_usuario " +
                "INNER JOIN usuarios u2 ON c.id_odontologo = u2.id_usuario " +
                "INNER JOIN servicios s ON c.id_servicio = s.id_servicio " +
                "WHERE c.fecha = ? " +
                "AND c.estado IN ('confirmada', 'sin_pagar', 'en_atencion', 'reservada', 'finalizada', 'derivada_sin_pagar') " +
                "ORDER BY c.hora ASC, u2.nombre ASC";
    
    try (Connection conn = Conexion.getConection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setDate(1, Date.valueOf(fecha));
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cita cita = mapearCitaCompleta(rs);
                cita.setTelefonoPaciente(rs.getString("telefono_paciente"));
                citas.add(cita);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return citas;
}

@Override
public Map<String, Integer> obtenerEstadisticasDelDia(LocalDate fecha) {
    Map<String, Integer> estadisticas = new HashMap<>();
    String sql = "SELECT estado, COUNT(*) as cantidad FROM citas WHERE fecha = ? GROUP BY estado";
    
    try (Connection conn = Conexion.getConection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setDate(1, Date.valueOf(fecha));
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                estadisticas.put(rs.getString("estado"), rs.getInt("cantidad"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return estadisticas;
}

@Override
public Map<String, Object> obtenerReporteGeneral(LocalDate fechaInicio, LocalDate fechaFin) {
    Map<String, Object> reporte = new HashMap<>();
    String sql = "SELECT " +
                "COUNT(*) as total_citas, " +
                "SUM(CASE WHEN estado = 'finalizada' THEN 1 ELSE 0 END) as citas_completadas, " +
                "SUM(CASE WHEN estado = 'cancelada' THEN 1 ELSE 0 END) as citas_canceladas, " +
                "SUM(CASE WHEN estado = 'sin_pagar' OR estado = 'derivada_sin_pagar' THEN 1 ELSE 0 END) as citas_pendientes_pago, " +
                "SUM(CASE WHEN estado = 'confirmada' THEN 1 ELSE 0 END) as citas_confirmadas " +
                "FROM citas WHERE fecha BETWEEN ? AND ?";
    
    try (Connection conn = Conexion.getConection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setDate(1, Date.valueOf(fechaInicio));
        ps.setDate(2, Date.valueOf(fechaFin));
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                reporte.put("total_citas", rs.getInt("total_citas"));
                reporte.put("citas_completadas", rs.getInt("citas_completadas"));
                reporte.put("citas_canceladas", rs.getInt("citas_canceladas"));
                reporte.put("citas_pendientes_pago", rs.getInt("citas_pendientes_pago"));
                reporte.put("citas_confirmadas", rs.getInt("citas_confirmadas"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return reporte;
}

@Override
public List<Map<String, Object>> obtenerReportePorDoctor(LocalDate fechaInicio, LocalDate fechaFin) {
    List<Map<String, Object>> reporte = new ArrayList<>();
    String sql = "SELECT u.nombre as doctor, " +
                "COUNT(*) as total_citas, " +
                "SUM(CASE WHEN c.estado = 'finalizada' THEN 1 ELSE 0 END) as citas_completadas " +
                "FROM citas c " +
                "INNER JOIN usuarios u ON c.id_odontologo = u.id_usuario " +
                "WHERE c.fecha BETWEEN ? AND ? " +
                "GROUP BY c.id_odontologo, u.nombre " +
                "ORDER BY total_citas DESC";
    
    try (Connection conn = Conexion.getConection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setDate(1, Date.valueOf(fechaInicio));
        ps.setDate(2, Date.valueOf(fechaFin));
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("doctor", rs.getString("doctor"));
                fila.put("total_citas", rs.getInt("total_citas"));
                fila.put("citas_completadas", rs.getInt("citas_completadas"));
                reporte.add(fila);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return reporte;
}

@Override
public List<Map<String, Object>> obtenerReportePorServicio(LocalDate fechaInicio, LocalDate fechaFin) {
    List<Map<String, Object>> reporte = new ArrayList<>();
    String sql = "SELECT s.nombre as servicio, " +
                "COUNT(*) as total_citas, " +
                "SUM(CASE WHEN c.estado = 'finalizada' THEN 1 ELSE 0 END) as citas_completadas, " +
                "s.precio as precio_servicio " +
                "FROM citas c " +
                "INNER JOIN servicios s ON c.id_servicio = s.id_servicio " +
                "WHERE c.fecha BETWEEN ? AND ? " +
                "GROUP BY c.id_servicio, s.nombre, s.precio " +
                "ORDER BY total_citas DESC";
    
    try (Connection conn = Conexion.getConection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setDate(1, Date.valueOf(fechaInicio));
        ps.setDate(2, Date.valueOf(fechaFin));
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("servicio", rs.getString("servicio"));
                fila.put("total_citas", rs.getInt("total_citas"));
                fila.put("citas_completadas", rs.getInt("citas_completadas"));
                fila.put("precio_servicio", rs.getDouble("precio_servicio"));
                reporte.add(fila);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return reporte;
}

}
