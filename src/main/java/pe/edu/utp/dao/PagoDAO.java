package pe.edu.utp.dao;

import pe.edu.utp.entity.Pago;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PagoDAO {
    boolean crear(Pago pago);
    Pago obtenerPorCita(int idCita);
    boolean actualizar(Pago pago);
    List<Pago> listarPorPaciente(int idPaciente);
    boolean procesarPago(int idCita, String metodoPago);
    void limpiarPagosExpirados();
    boolean eliminar(int id);
    List<Pago> listarTodos();
    Pago obtenerPorId(int id);
    List<Pago> listarPorEstado(String estado);
    boolean existePagoPorCita(int idCita);

    // Métodos para secretaria
    List<Map<String, Object>> listarPagosPendientesConDetalles();
    List<Map<String, Object>> listarPagosProximosVencer(int dias);
    Map<String, Double> obtenerReporteIngresos(LocalDate fechaInicio, LocalDate fechaFin);
    boolean marcarComoPagado(int idPago, String metodoPago, LocalDateTime fechaPago);
    Map<String, Object> obtenerDetallePago(int idPago);
}
