package pe.edu.utp.daoimpl;

import pe.edu.utp.dao.PagoDAO;
import pe.edu.utp.entity.Pago;
import pe.edu.utp.config.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagoDAOImpl implements PagoDAO {

    @Override
    public boolean crear(Pago pago) {
        String sql = "INSERT INTO pagos (id_cita, monto, metodo_pago, estado_pago, fecha_limite_pago) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, pago.getIdCita());
            ps.setDouble(2, pago.getMonto());
            ps.setString(3, pago.getMetodoPago());
            ps.setString(4, pago.getEstadoPago());
            ps.setTimestamp(5, pago.getFechaLimitePago() != null ? 
                Timestamp.valueOf(pago.getFechaLimitePago()) : null);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Pago obtenerPorId(int id) {
        Pago pago = null;
        String sql = "SELECT * FROM pagos WHERE id_pago = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pago = mapearPago(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pago;
    }

    @Override
    public Pago obtenerPorCita(int idCita) {
        Pago pago = null;
        String sql = "SELECT * FROM pagos WHERE id_cita = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCita);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pago = mapearPago(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pago;
    }

    @Override
    public List<Pago> listarTodos() {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT * FROM pagos ORDER BY fecha_pago DESC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                pagos.add(mapearPago(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagos;
    }

    @Override
    public List<Pago> listarPorPaciente(int idPaciente) {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT p.* FROM pagos p " +
                    "INNER JOIN citas c ON p.id_cita = c.id_cita " +
                    "WHERE c.id_paciente = ? ORDER BY p.fecha_pago DESC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idPaciente);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pagos.add(mapearPago(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagos;
    }

    @Override
    public List<Pago> listarPorEstado(String estado) {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT * FROM pagos WHERE estado_pago = ? ORDER BY fecha_pago DESC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, estado);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pagos.add(mapearPago(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagos;
    }

    @Override
    public boolean actualizar(Pago pago) {
        String sql = "UPDATE pagos SET monto = ?, metodo_pago = ?, estado_pago = ?, " +
                    "fecha_pago = ?, fecha_limite_pago = ? WHERE id_pago = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, pago.getMonto());
            ps.setString(2, pago.getMetodoPago());
            ps.setString(3, pago.getEstadoPago());
            ps.setTimestamp(4, pago.getFechaPago() != null ? 
                Timestamp.valueOf(pago.getFechaPago()) : null);
            ps.setTimestamp(5, pago.getFechaLimitePago() != null ? 
                Timestamp.valueOf(pago.getFechaLimitePago()) : null);
            ps.setInt(6, pago.getIdPago());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM pagos WHERE id_pago = ?";
        
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
    public boolean existePagoPorCita(int idCita) {
        String sql = "SELECT COUNT(*) FROM pagos WHERE id_cita = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCita);
            
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
    public boolean procesarPago(int idCita, String metodoPago) {
        String sql = "UPDATE pagos SET estado_pago = 'pagado', metodo_pago = ?, fecha_pago = ? WHERE id_cita = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, metodoPago);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(3, idCita);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void limpiarPagosExpirados() {
        String sql = "UPDATE pagos SET estado_pago = 'expirado' WHERE estado_pago = 'pendiente' AND fecha_limite_pago < ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> listarPagosPendientesConDetalles() {
        List<Map<String, Object>> pagos = new ArrayList<>();
        String sql = "SELECT p.*, c.fecha, c.hora, u.nombre as nombre_paciente, s.nombre as nombre_servicio " +
                    "FROM pagos p " +
                    "INNER JOIN citas c ON p.id_cita = c.id_cita " +
                    "INNER JOIN usuarios u ON c.id_paciente = u.id_usuario " +
                    "INNER JOIN servicios s ON c.id_servicio = s.id_servicio " +
                    "WHERE p.estado_pago = 'pendiente' " +
                    "ORDER BY p.fecha_limite_pago ASC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Map<String, Object> pago = new HashMap<>();
                pago.put("idPago", rs.getInt("id_pago"));
                pago.put("idCita", rs.getInt("id_cita"));
                pago.put("monto", rs.getDouble("monto"));
                pago.put("fechaCita", rs.getDate("fecha"));
                pago.put("horaCita", rs.getTime("hora"));
                pago.put("nombrePaciente", rs.getString("nombre_paciente"));
                pago.put("nombreServicio", rs.getString("nombre_servicio"));
                pago.put("fechaLimite", rs.getTimestamp("fecha_limite_pago"));
                pagos.add(pago);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagos;
    }

    @Override
    public List<Map<String, Object>> listarPagosProximosVencer(int dias) {
        List<Map<String, Object>> pagos = new ArrayList<>();
        String sql = "SELECT p.*, c.fecha, c.hora, u.nombre as nombre_paciente, s.nombre as nombre_servicio " +
                    "FROM pagos p " +
                    "INNER JOIN citas c ON p.id_cita = c.id_cita " +
                    "INNER JOIN usuarios u ON c.id_paciente = u.id_usuario " +
                    "INNER JOIN servicios s ON c.id_servicio = s.id_servicio " +
                    "WHERE p.estado_pago = 'pendiente' " +
                    "AND p.fecha_limite_pago BETWEEN ? AND ? " +
                    "ORDER BY p.fecha_limite_pago ASC";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime limite = ahora.plusDays(dias);
            
            ps.setTimestamp(1, Timestamp.valueOf(ahora));
            ps.setTimestamp(2, Timestamp.valueOf(limite));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> pago = new HashMap<>();
                    pago.put("idPago", rs.getInt("id_pago"));
                    pago.put("idCita", rs.getInt("id_cita"));
                    pago.put("monto", rs.getDouble("monto"));
                    pago.put("fechaCita", rs.getDate("fecha"));
                    pago.put("horaCita", rs.getTime("hora"));
                    pago.put("nombrePaciente", rs.getString("nombre_paciente"));
                    pago.put("nombreServicio", rs.getString("nombre_servicio"));
                    pago.put("fechaLimite", rs.getTimestamp("fecha_limite_pago"));
                    pagos.add(pago);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagos;
    }

    @Override
    public Map<String, Double> obtenerReporteIngresos(LocalDate fechaInicio, LocalDate fechaFin) {
        Map<String, Double> reporte = new HashMap<>();
        String sql = "SELECT " +
                    "SUM(CASE WHEN estado_pago = 'pagado' THEN monto ELSE 0 END) as ingresos_confirmados, " +
                    "SUM(CASE WHEN estado_pago = 'pendiente' THEN monto ELSE 0 END) as ingresos_pendientes, " +
                    "COUNT(CASE WHEN estado_pago = 'pagado' THEN 1 END) as pagos_realizados, " +
                    "COUNT(CASE WHEN estado_pago = 'pendiente' THEN 1 END) as pagos_pendientes " +
                    "FROM pagos p " +
                    "INNER JOIN citas c ON p.id_cita = c.id_cita " +
                    "WHERE c.fecha BETWEEN ? AND ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, Date.valueOf(fechaInicio));
            ps.setDate(2, Date.valueOf(fechaFin));
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    reporte.put("ingresosConfirmados", rs.getDouble("ingresos_confirmados"));
                    reporte.put("ingresosPendientes", rs.getDouble("ingresos_pendientes"));
                    reporte.put("pagosRealizados", (double) rs.getInt("pagos_realizados"));
                    reporte.put("pagosPendientes", (double) rs.getInt("pagos_pendientes"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reporte;
    }

    @Override
    public boolean marcarComoPagado(int idPago, String metodoPago, LocalDateTime fechaPago) {
        String sql = "UPDATE pagos SET estado_pago = 'pagado', metodo_pago = ?, fecha_pago = ? WHERE id_pago = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, metodoPago);
            ps.setTimestamp(2, Timestamp.valueOf(fechaPago));
            ps.setInt(3, idPago);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Object> obtenerDetallePago(int idPago) {
        Map<String, Object> detalle = new HashMap<>();
        String sql = "SELECT p.*, c.fecha, c.hora, u.nombre as nombre_paciente, u.correo, " +
                    "s.nombre as nombre_servicio, od.nombre as nombre_odontologo " +
                    "FROM pagos p " +
                    "INNER JOIN citas c ON p.id_cita = c.id_cita " +
                    "INNER JOIN usuarios u ON c.id_paciente = u.id_usuario " +
                    "INNER JOIN servicios s ON c.id_servicio = s.id_servicio " +
                    "INNER JOIN usuarios od ON c.id_odontologo = od.id_usuario " +
                    "WHERE p.id_pago = ?";
        
        try (Connection conn = Conexion.getConection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idPago);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    detalle.put("idPago", rs.getInt("id_pago"));
                    detalle.put("idCita", rs.getInt("id_cita"));
                    detalle.put("monto", rs.getDouble("monto"));
                    detalle.put("metodoPago", rs.getString("metodo_pago"));
                    detalle.put("estadoPago", rs.getString("estado_pago"));
                    detalle.put("fechaPago", rs.getTimestamp("fecha_pago"));
                    detalle.put("fechaLimite", rs.getTimestamp("fecha_limite_pago"));
                    detalle.put("fechaCita", rs.getDate("fecha"));
                    detalle.put("horaCita", rs.getTime("hora"));
                    detalle.put("nombrePaciente", rs.getString("nombre_paciente"));
                    detalle.put("correoPaciente", rs.getString("correo"));
                    detalle.put("nombreServicio", rs.getString("nombre_servicio"));
                    detalle.put("nombreOdontologo", rs.getString("nombre_odontologo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalle;
    }

    private Pago mapearPago(ResultSet rs) throws SQLException {
        Pago pago = new Pago();
        pago.setIdPago(rs.getInt("id_pago"));
        pago.setIdCita(rs.getInt("id_cita"));
        pago.setMonto(rs.getDouble("monto"));
        pago.setMetodoPago(rs.getString("metodo_pago"));
        pago.setEstadoPago(rs.getString("estado_pago"));
        
        Timestamp fechaPago = rs.getTimestamp("fecha_pago");
        if (fechaPago != null) {
            pago.setFechaPago(fechaPago.toLocalDateTime());
        }
        
        Timestamp fechaLimite = rs.getTimestamp("fecha_limite_pago");
        if (fechaLimite != null) {
            pago.setFechaLimitePago(fechaLimite.toLocalDateTime());
        }
        
        return pago;
    }
}
