package pe.edu.utp.controller;

import pe.edu.utp.dao.*;
import pe.edu.utp.daoimpl.*;
import pe.edu.utp.entity.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard-secretaria")
public class DashboardSecretariaController extends HttpServlet {
    
    private CitaDAO citaDAO;
    private UsuarioDAO usuarioDAO;
    private PagoDAO pagoDAO;
    private NotificacionDAO notificacionDAO;
    
    @Override
    public void init() throws ServletException {
        citaDAO = new CitaDAOImpl();
        usuarioDAO = new UsuarioDAOImpl();
        pagoDAO = new PagoDAOImpl();
        notificacionDAO = new NotificacionDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !"secretaria".equals(usuario.getNombreRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            String vista = request.getParameter("vista");
            if (vista == null) vista = "citas";
            
            // Obtener usuarios pendientes para mostrar en todas las vistas
            List<Usuario> usuariosPendientes = usuarioDAO.listarUsuariosPendientesValidacion();
            request.setAttribute("usuariosPendientes", usuariosPendientes);
            
            switch (vista) {
                case "citas":
                    cargarVistaCitas(request, response);
                    break;
                case "reportes":
                    cargarVistaReportes(request, response);
                    break;
                case "pagos":
                    cargarVistaPagos(request, response);
                    break;
                default:
                    cargarVistaCitas(request, response);
                    break;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error cargando el dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/dashboard-secretaria.jsp").forward(request, response);
        }
    }
    
    private void cargarVistaCitas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener fecha seleccionada
        String fechaParam = request.getParameter("fecha");
        LocalDate fechaSeleccionada = fechaParam != null ? LocalDate.parse(fechaParam) : LocalDate.now();
        
        // Obtener doctor seleccionado (filtro)
        String doctorParam = request.getParameter("doctor");
        Integer doctorSeleccionado = null;
        if (doctorParam != null && !doctorParam.isEmpty() && !"todos".equals(doctorParam)) {
            doctorSeleccionado = Integer.parseInt(doctorParam);
        }
        
        // Obtener todas las citas del día
        List<Cita> citasDelDia;
        if (doctorSeleccionado != null) {
            citasDelDia = citaDAO.listarCitasDelDiaPorOdontologo(doctorSeleccionado, fechaSeleccionada);
        } else {
            citasDelDia = citaDAO.listarTodasCitasDelDia(fechaSeleccionada);
        }
        
        // Obtener lista de doctores para el filtro
        List<Usuario> doctores = usuarioDAO.listarPorRol("odontologo");
        
        // Estadísticas rápidas del día
        Map<String, Integer> estadisticasDelDia = citaDAO.obtenerEstadisticasDelDia(fechaSeleccionada);
        
        request.setAttribute("vista", "citas");
        request.setAttribute("citasDelDia", citasDelDia);
        request.setAttribute("doctores", doctores);
        request.setAttribute("fechaSeleccionada", fechaSeleccionada);
        request.setAttribute("doctorSeleccionado", doctorSeleccionado);
        request.setAttribute("estadisticasDelDia", estadisticasDelDia);
        request.setAttribute("fechaHoy", LocalDate.now());
        
        request.getRequestDispatcher("/WEB-INF/views/dashboard-secretaria.jsp").forward(request, response);
    }
    
    private void cargarVistaReportes(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener rango de fechas para el reporte
        String fechaInicioParam = request.getParameter("fechaInicio");
        String fechaFinParam = request.getParameter("fechaFin");
        
        LocalDate fechaInicio = fechaInicioParam != null ? LocalDate.parse(fechaInicioParam) : LocalDate.now().minusDays(30);
        LocalDate fechaFin = fechaFinParam != null ? LocalDate.parse(fechaFinParam) : LocalDate.now();
        
        // Obtener reportes
        Map<String, Object> reporteGeneral = citaDAO.obtenerReporteGeneral(fechaInicio, fechaFin);
        List<Map<String, Object>> reportePorDoctor = citaDAO.obtenerReportePorDoctor(fechaInicio, fechaFin);
        List<Map<String, Object>> reportePorServicio = citaDAO.obtenerReportePorServicio(fechaInicio, fechaFin);
        Map<String, Double> reporteIngresos = pagoDAO.obtenerReporteIngresos(fechaInicio, fechaFin);
        
        request.setAttribute("vista", "reportes");
        request.setAttribute("fechaInicio", fechaInicio);
        request.setAttribute("fechaFin", fechaFin);
        request.setAttribute("reporteGeneral", reporteGeneral);
        request.setAttribute("reportePorDoctor", reportePorDoctor);
        request.setAttribute("reportePorServicio", reportePorServicio);
        request.setAttribute("reporteIngresos", reporteIngresos);
        
        request.getRequestDispatcher("/WEB-INF/views/dashboard-secretaria.jsp").forward(request, response);
    }
    
    private void cargarVistaPagos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener pagos pendientes
        List<Map<String, Object>> pagosPendientes = pagoDAO.listarPagosPendientesConDetalles();
        
        // Obtener pagos próximos a vencer
        List<Map<String, Object>> pagosProximosVencer = pagoDAO.listarPagosProximosVencer(3); // 3 días
        
        request.setAttribute("vista", "pagos");
        request.setAttribute("pagosPendientes", pagosPendientes);
        request.setAttribute("pagosProximosVencer", pagosProximosVencer);
        
        request.getRequestDispatcher("/WEB-INF/views/dashboard-secretaria.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !"secretaria".equals(usuario.getNombreRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "confirmarCita":
                    confirmarCita(request, response);
                    break;
                case "enviarPromocion":
                    enviarPromocion(request, response);
                    break;
                case "marcarPagado":
                    marcarPagado(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/dashboard-secretaria");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error procesando la acción: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/dashboard-secretaria");
        }
    }
    
    private void confirmarCita(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int idCita = Integer.parseInt(request.getParameter("idCita"));
            
            boolean actualizado = citaDAO.cambiarEstado(idCita, "confirmada");
            
            if (actualizado) {
                // Obtener datos de la cita para notificación
                Cita cita = citaDAO.obtenerPorId(idCita);
                if (cita != null) {
                    String mensaje = String.format(
                        "Tu cita de %s para el %s a las %s ha sido confirmada. Te esperamos puntualmente.",
                        cita.getNombreServicio(),
                        cita.getFecha().toString(),
                        cita.getHora().toString()
                    );
                    
                    Notificacion notificacion = new Notificacion(cita.getIdPaciente(), mensaje);
                    notificacionDAO.crear(notificacion);
                }
                
                request.getSession().setAttribute("mensaje", "Cita confirmada exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al confirmar la cita");
            }
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "ID de cita inválido");
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard-secretaria");
    }
    
    private void enviarPromocion(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String mensajePromocion = request.getParameter("mensajePromocion");
            
            if (mensajePromocion == null || mensajePromocion.trim().isEmpty()) {
                request.getSession().setAttribute("error", "El mensaje de promoción no puede estar vacío");
                response.sendRedirect(request.getContextPath() + "/dashboard-secretaria");
                return;
            }
            
            // Obtener todos los pacientes
            List<Usuario> pacientes = usuarioDAO.listarPorRol("paciente");
            
            int notificacionesEnviadas = 0;
            for (Usuario paciente : pacientes) {
                Notificacion notificacion = new Notificacion(paciente.getIdUsuario(), 
                    "🎉 PROMOCIÓN: " + mensajePromocion.trim());
                if (notificacionDAO.crear(notificacion)) {
                    notificacionesEnviadas++;
                }
            }
            
            request.getSession().setAttribute("mensaje", 
                "Promoción enviada a " + notificacionesEnviadas + " pacientes");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error enviando la promoción: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard-secretaria");
    }
    
    private void marcarPagado(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int idPago = Integer.parseInt(request.getParameter("idPago"));
            String metodoPago = request.getParameter("metodoPago");
            
            boolean actualizado = pagoDAO.marcarComoPagado(idPago, metodoPago, LocalDateTime.now());
            
            if (actualizado) {
                // Cambiar estado de la cita a confirmada
                Map<String, Object> detallePago = pagoDAO.obtenerDetallePago(idPago);
                if (detallePago != null) {
                    int idCita = (Integer) detallePago.get("id_cita");
                    citaDAO.cambiarEstado(idCita, "confirmada");
                    
                    // Notificar al paciente
                    String nombrePaciente = (String) detallePago.get("nombre_paciente");
                    int idPaciente = (Integer) detallePago.get("id_paciente");
                    
                    String mensaje = "Tu pago ha sido confirmado. Tu cita está confirmada y te esperamos puntualmente.";
                    Notificacion notificacion = new Notificacion(idPaciente, mensaje);
                    notificacionDAO.crear(notificacion);
                }
                
                request.getSession().setAttribute("mensaje", "Pago registrado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al registrar el pago");
            }
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "ID de pago inválido");
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard-secretaria?vista=pagos");
    }
}
