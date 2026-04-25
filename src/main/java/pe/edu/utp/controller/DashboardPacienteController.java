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
import java.util.List;
import java.time.ZoneId;
import java.util.Date;

@WebServlet("/dashboard-paciente")
public class DashboardPacienteController extends HttpServlet {

    private CitaDAO citaDAO;
    private PagoDAO pagoDAO;
    private NotificacionDAO notificacionDAO;
    private ServicioDAO servicioDAO;

    @Override
    public void init() throws ServletException {
        citaDAO = new CitaDAOImpl();
        pagoDAO = new PagoDAOImpl();
        notificacionDAO = new NotificacionDAOImpl();
        servicioDAO = new ServicioDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || !usuario.getNombreRol().equals("paciente")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Limpiar pagos expirados
        pagoDAO.limpiarPagosExpirados();

        // Obtener datos del dashboard
        int idPaciente = usuario.getIdUsuario();

        // Citas próximas (confirmadas y sin pagar)
        List<Cita> citasProximas = citaDAO.listarProximasPorPaciente(idPaciente);

        // Citas pendientes de pago
        List<Cita> citasPendientesPago = citaDAO.listarPendientesPagoPorPaciente(idPaciente);

        // Historial de citas
        List<Cita> historialCitas = citaDAO.listarHistorialPorPaciente(idPaciente);

        // Notificaciones no leídas
        List<Notificacion> notificacionesOriginales = notificacionDAO.listarNoLeidasPorUsuario(idPaciente);
        List<NotificacionVista> notificaciones = new java.util.ArrayList<>();

        for (Notificacion n : notificacionesOriginales) {
            notificaciones.add(new NotificacionVista(n));
        }
        int contadorNotificaciones = notificacionDAO.contarNoLeidas(idPaciente);

 
        // Servicios disponibles (para reservas rápidas)
        List<Servicio> serviciosDestacados = servicioDAO.listarTodos();
        if (serviciosDestacados.size() > 4) {
            serviciosDestacados = serviciosDestacados.subList(0, 4);
        }

        // Verificar si tiene reserva activa
        boolean tieneReservaActiva = citaDAO.tieneReservaActiva(idPaciente);

        // Establecer atributos
        request.setAttribute("citasProximas", citasProximas);
        request.setAttribute("citasPendientesPago", citasPendientesPago);
        request.setAttribute("historialCitas", historialCitas);
        request.setAttribute("notificaciones", notificaciones);
        request.setAttribute("contadorNotificaciones", contadorNotificaciones);
        request.setAttribute("serviciosDestacados", serviciosDestacados);
        request.setAttribute("tieneReservaActiva", tieneReservaActiva);

        request.getRequestDispatcher("/WEB-INF/views/dashboard-paciente.jsp").forward(request, response);
    }

    public class NotificacionVista {

        private Notificacion notificacion;
        private String fechaFormateada;

        public NotificacionVista(Notificacion notificacion) {
            this.notificacion = notificacion;
            if (notificacion.getFecha() != null) {
                this.fechaFormateada = notificacion.getFecha()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            }
        }

        public Notificacion getNotificacion() {
            return notificacion;
        }

        public String getFechaFormateada() {
            return fechaFormateada;
        }
    }
}
