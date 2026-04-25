package pe.edu.utp.dao;

import pe.edu.utp.entity.Cita;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface CitaDAO {
    boolean crear(Cita cita);
    Cita obtenerPorId(int id);
    List<Cita> listarTodas();
    List<Cita> listarPorPaciente(int idPaciente);
    List<Cita> listarPorOdontologo(int idOdontologo);
    List<Cita> listarPorFecha(String fecha);
    boolean actualizar(Cita cita);
    boolean eliminar(int id);
    
    // Métodos específicos para pacientes
    List<Cita> obtenerCitasPendientesPago(int idPaciente);
    List<Cita> obtenerProximasCitas(int idPaciente);
    List<Cita> obtenerHistorialCitas(int idPaciente);
    boolean existeReservaActiva(int idPaciente);
    
    // Métodos de estado
    boolean cambiarEstado(int idCita, String nuevoEstado);
    
    // Métodos auxiliares
    List<Cita> listarProximasPorPaciente(int idPaciente);
    List<Cita> listarPendientesPagoPorPaciente(int idPaciente);
    List<Cita> listarHistorialPorPaciente(int idPaciente);
    boolean tieneReservaActiva(int idPaciente);
    boolean existeCitaEnHorario(int idOdontologo, LocalDate fecha, LocalTime hora);
    int obtenerUltimaIdCita(int idPaciente);
    
    // Métodos para odontólogos
    List<Cita> listarCitasDelDiaPorOdontologo(int idOdontologo, LocalDate fecha);
    boolean marcarInicioAtencion(int idCita, LocalDateTime horaInicio);
    boolean finalizarAtencion(int idCita, String observaciones, String receta, LocalDateTime horaFin);
    List<Cita> listarCitasPorRangoFechas(int idOdontologo, LocalDate fechaInicio, LocalDate fechaFin);
    
    // Métodos para secretaria
    List<Cita> listarTodasCitasDelDia(LocalDate fecha);
    Map<String, Integer> obtenerEstadisticasDelDia(LocalDate fecha);
    Map<String, Object> obtenerReporteGeneral(LocalDate fechaInicio, LocalDate fechaFin);
    List<Map<String, Object>> obtenerReportePorDoctor(LocalDate fechaInicio, LocalDate fechaFin);
    List<Map<String, Object>> obtenerReportePorServicio(LocalDate fechaInicio, LocalDate fechaFin);
}
