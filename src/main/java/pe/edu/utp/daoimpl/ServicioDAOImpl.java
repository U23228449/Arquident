package pe.edu.utp.daoimpl;

import pe.edu.utp.dao.ServicioDAO;
import pe.edu.utp.entity.Servicio;
import pe.edu.utp.config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAOImpl implements ServicioDAO {

    @Override
    public List<Servicio> listarDisponiblesParaReserva() {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT id_servicio, nombre, descripcion, precio, "
                + "COALESCE(requiere_consulta, false) as requiere_consulta "
                + "FROM servicios WHERE COALESCE(requiere_consulta, false) = false "
                + "ORDER BY nombre";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("id_servicio"));
                servicio.setNombre(rs.getString("nombre"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getBigDecimal("precio"));
                servicio.setRequiereConsulta(rs.getBoolean("requiere_consulta"));
                servicios.add(servicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicios;
    }

    @Override
    public List<Servicio> listarTodos() {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT * FROM servicios ORDER BY nombre";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("id_servicio"));
                servicio.setNombre(rs.getString("nombre"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getBigDecimal("precio"));

                // Si no existe la columna requiere_consulta, asumir false por defecto
                try {
                    servicio.setRequiereConsulta(rs.getBoolean("requiere_consulta"));
                } catch (SQLException e) {
                    servicio.setRequiereConsulta(false);
                }

                servicios.add(servicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicios;
    }

    @Override
    public List<Servicio> listarSinConsulta() {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT * FROM servicios WHERE requiere_consulta = false OR requiere_consulta IS NULL ORDER BY nombre";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("id_servicio"));
                servicio.setNombre(rs.getString("nombre"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getBigDecimal("precio"));

                try {
                    servicio.setRequiereConsulta(rs.getBoolean("requiere_consulta"));
                } catch (SQLException e) {
                    servicio.setRequiereConsulta(false);
                }

                servicios.add(servicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicios;
    }

    @Override
    public List<Servicio> listarConConsulta() {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT * FROM servicios WHERE requiere_consulta = true ORDER BY nombre";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("id_servicio"));
                servicio.setNombre(rs.getString("nombre"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getBigDecimal("precio"));
                servicio.setRequiereConsulta(rs.getBoolean("requiere_consulta"));
                servicios.add(servicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicios;
    }

    @Override
    public Servicio obtenerPorId(int id) {
        Servicio servicio = null;
        String sql = "SELECT * FROM servicios WHERE id_servicio = ?";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    servicio = new Servicio();
                    servicio.setIdServicio(rs.getInt("id_servicio"));
                    servicio.setNombre(rs.getString("nombre"));
                    servicio.setDescripcion(rs.getString("descripcion"));
                    servicio.setPrecio(rs.getBigDecimal("precio"));

                    try {
                        servicio.setRequiereConsulta(rs.getBoolean("requiere_consulta"));
                    } catch (SQLException e) {
                        servicio.setRequiereConsulta(false);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicio;
    }

    @Override
    public List<Servicio> listarPorOdontologo(int idOdontologo) {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT s.* FROM servicios s "
                + "INNER JOIN odontologo_servicios os ON s.id_servicio = os.id_servicio "
                + "WHERE os.id_odontologo = ? ORDER BY s.nombre";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idOdontologo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Servicio servicio = new Servicio();
                    servicio.setIdServicio(rs.getInt("id_servicio"));
                    servicio.setNombre(rs.getString("nombre"));
                    servicio.setDescripcion(rs.getString("descripcion"));
                    servicio.setPrecio(rs.getBigDecimal("precio"));

                    try {
                        servicio.setRequiereConsulta(rs.getBoolean("requiere_consulta"));
                    } catch (SQLException e) {
                        servicio.setRequiereConsulta(false);
                    }

                    servicios.add(servicio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicios;
    }

    @Override
    public List<Servicio> listarDestacados() {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT * FROM servicios ORDER BY precio ASC LIMIT 5";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("id_servicio"));
                servicio.setNombre(rs.getString("nombre"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getBigDecimal("precio"));

                try {
                    servicio.setRequiereConsulta(rs.getBoolean("requiere_consulta"));
                } catch (SQLException e) {
                    servicio.setRequiereConsulta(false);
                }

                servicios.add(servicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicios;
    }

    @Override
    public boolean crear(Servicio servicio) {
        String sql = "INSERT INTO servicios (nombre, descripcion, precio, requiere_consulta) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setBigDecimal(3, servicio.getPrecio());
            ps.setBoolean(4, servicio.isRequiereConsulta());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Servicio servicio) {
        String sql = "UPDATE servicios SET nombre = ?, descripcion = ?, precio = ?, requiere_consulta = ? WHERE id_servicio = ?";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setBigDecimal(3, servicio.getPrecio());
            ps.setBoolean(4, servicio.isRequiereConsulta());
            ps.setInt(5, servicio.getIdServicio());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM servicios WHERE id_servicio = ?";

        try (Connection conn = Conexion.getConection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int obtenerDuracionPorDefecto(int idServicio) {
        // Todas las citas duran 45 minutos
        return 45;
    }
}
