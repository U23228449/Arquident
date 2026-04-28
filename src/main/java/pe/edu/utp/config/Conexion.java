package pe.edu.utp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3307/arquidentdb?serverTimezone=America/Lima&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    static {
        try {
            // Cargar el driver una sola vez
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("ArquiDent: Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("ArquiDent: Error al cargar driver MySQL: " + e.getMessage());
            throw new RuntimeException("No se pudo cargar el driver de MySQL", e);
        }
    }
    
    public static Connection getConection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("ArquiDent: Conexión a base de datos establecida");
        } catch (SQLException e) {
            System.err.println("ArquiDent: Error de conexión a base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("ArquiDent: Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("ArquiDent: Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
