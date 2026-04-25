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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/reservar-cita")
public class ReservarCitaController extends HttpServlet {
    
    private ServicioDAO servicioDAO;
    private UsuarioDAO usuarioDAO;
    private CitaDAO citaDAO;
    private NotificacionDAO notificacionDAO;
    private PagoDAO pagoDAO;
    
    @Override
    public void init() throws ServletException {
        servicioDAO = new ServicioDAOImpl();
        usuarioDAO = new UsuarioDAOImpl();
        citaDAO = new CitaDAOImpl();
        notificacionDAO = new NotificacionDAOImpl();
        pagoDAO = new PagoDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        // Solo manejar horarios por AJAX (los doctores ya no)
        if ("getHorarios".equals(action)) {
            getHorariosDisponibles(request, response);
            return;
        }
        
        // Verificar si ya tiene una reserva activa
        if (citaDAO.tieneReservaActiva(usuario.getIdUsuario())) {
            request.setAttribute("error", "Ya tienes una cita activa. Completa o cancela tu cita actual antes de reservar una nueva.");
            request.getRequestDispatcher("/WEB-INF/views/dashboard-paciente.jsp").forward(request, response);
            return;
        }
        
        try {
            // Obtener servicios disponibles para reserva
            List<Servicio> serviciosDisponibles = servicioDAO.listarDisponiblesParaReserva();
            request.setAttribute("serviciosDisponibles", serviciosDisponibles);
            
            // Cargar TODOS los doctores desde el inicio
            List<Usuario> doctoresDisponibles = usuarioDAO.listarPorRol("odontologo");
            request.setAttribute("doctoresDisponibles", doctoresDisponibles);
            
            System.out.println("[ReservarCita] Doctores cargados: " + doctoresDisponibles.size());
            for (Usuario doctor : doctoresDisponibles) {
                System.out.println("[ReservarCita] Doctor: ID=" + doctor.getIdUsuario() + 
                                 ", Nombre=" + doctor.getNombre() + ", Teléfono=" + doctor.getTelefono());
            }
            
            // Si viene un servicio preseleccionado
            String servicioId = request.getParameter("servicio");
            if (servicioId != null && !servicioId.isEmpty()) {
                try {
                    int id = Integer.parseInt(servicioId);
                    Servicio servicioPreseleccionado = servicioDAO.obtenerPorId(id);
                    if (servicioPreseleccionado != null && !servicioPreseleccionado.isRequiereConsulta()) {
                        request.setAttribute("servicioPreseleccionado", servicioPreseleccionado);
                    }
                } catch (NumberFormatException e) {
                    // Ignorar ID inválido
                }
            }
            
            request.getRequestDispatcher("/WEB-INF/views/reservar-cita.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error cargando la página de reserva: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/dashboard-paciente.jsp").forward(request, response);
        }
    }
    
    private void getHorariosDisponibles(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String doctorId = request.getParameter("doctor");
            String fechaStr = request.getParameter("fecha");
            
            if (doctorId == null || fechaStr == null || doctorId.isEmpty() || fechaStr.isEmpty()) {
                response.getWriter().write("[]");
                return;
            }
            
            int idDoctor = Integer.parseInt(doctorId);
            LocalDate fecha = LocalDate.parse(fechaStr);
            
            // Verificar que la fecha no sea anterior a hoy
            if (fecha.isBefore(LocalDate.now())) {
                response.getWriter().write("[]");
                return;
            }
            
            // Generar horarios disponibles cada 45 minutos de 8:00 AM a 6:00 PM
            List<String> horariosDisponibles = new ArrayList<>();
            LocalTime horaInicio = LocalTime.of(8, 0); // 8:00 AM
            LocalTime horaFin = LocalTime.of(18, 0);   // 6:00 PM
            
            LocalTime horaActual = horaInicio;
            while (horaActual.isBefore(horaFin)) {
                // Verificar si el horario está ocupado
                if (!citaDAO.existeCitaEnHorario(idDoctor, fecha, horaActual)) {
                    horariosDisponibles.add(horaActual.toString());
                }
                horaActual = horaActual.plusMinutes(45); // Intervalos de 45 minutos
            }
            
            StringBuilder json = new StringBuilder();
            json.append("[");
            
            for (int i = 0; i < horariosDisponibles.size(); i++) {
                if (i > 0) json.append(",");
                json.append("\"").append(horariosDisponibles.get(i)).append("\"");
            }
            
            json.append("]");
            
            System.out.println("[getHorarios] Doctor: " + idDoctor + ", Fecha: " + fecha + 
                             ", Horarios disponibles: " + horariosDisponibles.size());
            
            response.getWriter().write(json.toString());
            
        } catch (Exception e) {
            System.err.println("[getHorarios] Error: " + e.getMessage());
            e.printStackTrace();
            response.getWriter().write("[]");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            String servicioId = request.getParameter("servicio");
            String odontologoId = request.getParameter("odontologo");
            String fechaParam = request.getParameter("fecha");
            String horaParam = request.getParameter("hora");
            String observaciones = request.getParameter("observaciones");
            
            // Validar datos
            if (servicioId == null || odontologoId == null || fechaParam == null || horaParam == null ||
                servicioId.isEmpty() || odontologoId.isEmpty() || fechaParam.isEmpty() || horaParam.isEmpty()) {
                
                request.setAttribute("error", "Todos los campos son obligatorios");
                doGet(request, response);
                return;
            }
            
            int idServicio = Integer.parseInt(servicioId);
            int idOdontologo = Integer.parseInt(odontologoId);
            LocalDate fecha = LocalDate.parse(fechaParam);
            LocalTime hora = LocalTime.parse(horaParam);
            
            // Verificar que la fecha no sea anterior a hoy
            if (fecha.isBefore(LocalDate.now())) {
                request.setAttribute("error", "No se puede reservar en fechas pasadas");
                doGet(request, response);
                return;
            }
            
            // Verificar que el servicio no requiera consulta previa
            Servicio servicio = servicioDAO.obtenerPorId(idServicio);
            if (servicio == null || servicio.isRequiereConsulta()) {
                request.setAttribute("error", "Este servicio requiere consulta previa");
                doGet(request, response);
                return;
            }
            
            // Verificar que no tenga reserva activa
            if (citaDAO.tieneReservaActiva(usuario.getIdUsuario())) {
                request.setAttribute("error", "Ya tienes una cita activa");
                doGet(request, response);
                return;
            }
            
            // Verificar disponibilidad del horario
            if (citaDAO.existeCitaEnHorario(idOdontologo, fecha, hora)) {
                request.setAttribute("error", "El horario seleccionado ya no está disponible");
                doGet(request, response);
                return;
            }
            
            // Crear la cita
            Cita nuevaCita = new Cita();
            nuevaCita.setIdPaciente(usuario.getIdUsuario());
            nuevaCita.setIdOdontologo(idOdontologo);
            nuevaCita.setIdServicio(idServicio);
            nuevaCita.setFecha(fecha);
            nuevaCita.setHora(hora);
            nuevaCita.setEstado("sin_pagar");
            nuevaCita.setObservaciones(observaciones != null ? observaciones.trim() : "");
            
            boolean citaCreada = citaDAO.crear(nuevaCita);
            
            if (citaCreada) {
                try {
                    // Obtener el ID de la cita recién creada
                    int idCita = citaDAO.obtenerUltimaIdCita(usuario.getIdUsuario());
                    System.out.println("[ReservarCita] ID de cita obtenida: " + idCita);
                    
                    // Crear el pago pendiente
                    Pago pago = new Pago();
                    pago.setIdCita(idCita);
                    pago.setMonto(servicio.getPrecio().doubleValue());
                    pago.setMetodoPago(null);
                    pago.setEstadoPago("pendiente");
                    pago.setFechaLimitePago(LocalDateTime.now().plusMinutes(5));
                    
                    boolean pagoCreado = pagoDAO.crear(pago);
                    System.out.println("[ReservarCita] Pago creado: " + pagoCreado + " para cita ID: " + idCita);
                    
                } catch (Exception e) {
                    System.err.println("[ReservarCita] Error creando pago: " + e.getMessage());
                    e.printStackTrace();
                }
                
                // Crear notificación
                Usuario odontologo = usuarioDAO.obtenerPorId(idOdontologo);
                
                String mensaje = String.format(
                    "Tu cita de %s con %s ha sido reservada para el %s a las %s. Tienes 5 minutos para completar el pago.",
                    servicio.getNombre(),
                    odontologo.getNombre(),
                    fecha.toString(),
                    hora.toString()
                );
                
                Notificacion notificacion = new Notificacion(usuario.getIdUsuario(), mensaje);
                notificacionDAO.crear(notificacion);
                
                // Redirigir al dashboard con mensaje de éxito
                session.setAttribute("mensaje", "Cita reservada exitosamente. Completa el pago en los próximos 5 minutos.");
                response.sendRedirect(request.getContextPath() + "/dashboard-paciente");
            } else {
                request.setAttribute("error", "Error al crear la cita. Inténtalo nuevamente.");
                doGet(request, response);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Datos inválidos en el formulario");
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error procesando la reserva: " + e.getMessage());
            doGet(request, response);
        }
    }
}
