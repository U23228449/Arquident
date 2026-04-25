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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/dashboard-odontologo")
public class DashboardOdontologoController extends HttpServlet {
    
    private CitaDAO citaDAO;
    private ServicioDAO servicioDAO;
    private PagoDAO pagoDAO;
    private NotificacionDAO notificacionDAO;
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        citaDAO = new CitaDAOImpl();
        servicioDAO = new ServicioDAOImpl();
        pagoDAO = new PagoDAOImpl();
        notificacionDAO = new NotificacionDAOImpl();
        usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !"odontologo".equals(usuario.getNombreRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // Obtener fecha seleccionada (si viene como parámetro)
            String fechaParam = request.getParameter("fecha");
            LocalDate fechaSeleccionada = fechaParam != null ? LocalDate.parse(fechaParam) : LocalDate.now();

            // Obtener citas del día seleccionado
            List<Cita> citasDelDia = citaDAO.listarCitasDelDiaPorOdontologo(
                usuario.getIdUsuario(), fechaSeleccionada);

            // Obtener citas de la próxima semana para el calendario
            List<Cita> citasProximaSemana = citaDAO.listarCitasPorRangoFechas(
                usuario.getIdUsuario(), LocalDate.now(), LocalDate.now().plusDays(7));

            // Obtener todos los servicios para citas derivadas
            List<Servicio> serviciosDisponibles = servicioDAO.listarTodos();

            request.setAttribute("citasDelDia", citasDelDia);
            request.setAttribute("citasProximaSemana", citasProximaSemana);
            request.setAttribute("serviciosDisponibles", serviciosDisponibles);
            request.setAttribute("fechaHoy", LocalDate.now());
            request.setAttribute("fechaSeleccionada", fechaSeleccionada);
            
            System.out.println("[DashboardOdontologo] Citas del día: " + citasDelDia.size());
            
            request.getRequestDispatcher("/WEB-INF/views/dashboard-odontologo.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error cargando el dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/dashboard-odontologo.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !"odontologo".equals(usuario.getNombreRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "marcarEntrada":
                    marcarEntradaPaciente(request, response, usuario);
                    break;
                case "finalizarCita":
                    finalizarCita(request, response, usuario);
                    break;
                case "crearCitaDerivada":
                    crearCitaDerivada(request, response, usuario);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/dashboard-odontologo");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error procesando la acción: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/dashboard-odontologo");
        }
    }
    
    private void marcarEntradaPaciente(HttpServletRequest request, HttpServletResponse response, Usuario usuario) 
            throws ServletException, IOException {
        
        try {
            int idCita = Integer.parseInt(request.getParameter("idCita"));
            
            // Actualizar hora de inicio real y cambiar estado a "en_atencion"
            boolean actualizado = citaDAO.marcarInicioAtencion(idCita, LocalDateTime.now());
            
            if (actualizado) {
                request.getSession().setAttribute("mensaje", "Entrada del paciente marcada correctamente");
            } else {
                request.getSession().setAttribute("error", "Error al marcar la entrada del paciente");
            }
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "ID de cita inválido");
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard-odontologo");
    }
    
    private void finalizarCita(HttpServletRequest request, HttpServletResponse response, Usuario usuario) 
            throws ServletException, IOException {
        
        try {
            int idCita = Integer.parseInt(request.getParameter("idCita"));
            String observaciones = request.getParameter("observaciones");
            String receta = request.getParameter("receta");
            
            // Obtener la cita actual
            Cita cita = citaDAO.obtenerPorId(idCita);
            if (cita == null) {
                request.getSession().setAttribute("error", "Cita no encontrada");
                response.sendRedirect(request.getContextPath() + "/dashboard-odontologo");
                return;
            }
            
            // Actualizar observaciones, receta y marcar fin de atención
            boolean actualizado = citaDAO.finalizarAtencion(idCita, observaciones, receta, LocalDateTime.now());
            
            if (actualizado) {
                // Crear notificación para el paciente
                String mensaje = String.format(
                    "Tu cita de %s del %s ha sido completada. El reporte médico está disponible en tu dashboard.",
                    cita.getNombreServicio(),
                    cita.getFecha().toString()
                );
                
                Notificacion notificacion = new Notificacion(cita.getIdPaciente(), mensaje);
                notificacionDAO.crear(notificacion);
                
                request.getSession().setAttribute("mensaje", "Cita finalizada correctamente");
            } else {
                request.getSession().setAttribute("error", "Error al finalizar la cita");
            }
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "ID de cita inválido");
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard-odontologo");
    }
    
    private void crearCitaDerivada(HttpServletRequest request, HttpServletResponse response, Usuario usuario) 
            throws ServletException, IOException {
        
        try {
            int idCitaOriginal = Integer.parseInt(request.getParameter("idCitaOriginal"));
            int idServicio = Integer.parseInt(request.getParameter("servicioDerivado"));
            String fechaStr = request.getParameter("fechaDerivada");
            String horaStr = request.getParameter("horaDerivada");
            String precioStr = request.getParameter("precioDerivado");
            String observacionesDerivada = request.getParameter("observacionesDerivada");
            int diasLimitePago = Integer.parseInt(request.getParameter("diasLimitePago"));
            
            // Obtener la cita original
            Cita citaOriginal = citaDAO.obtenerPorId(idCitaOriginal);
            if (citaOriginal == null) {
                request.getSession().setAttribute("error", "Cita original no encontrada");
                response.sendRedirect(request.getContextPath() + "/dashboard-odontologo");
                return;
            }
            
            // Validar datos
            LocalDate fechaDerivada = LocalDate.parse(fechaStr);
            LocalTime horaDerivada = LocalTime.parse(horaStr);
            double precioDerivado = Double.parseDouble(precioStr);
            
            // Verificar que no haya conflicto de horario
            if (citaDAO.existeCitaEnHorario(usuario.getIdUsuario(), fechaDerivada, horaDerivada)) {
                request.getSession().setAttribute("error", "Ya tienes una cita programada en ese horario");
                response.sendRedirect(request.getContextPath() + "/dashboard-odontologo");
                return;
            }
            
            // Crear la cita derivada
            Cita citaDerivada = new Cita();
            citaDerivada.setIdPaciente(citaOriginal.getIdPaciente());
            citaDerivada.setIdOdontologo(usuario.getIdUsuario());
            citaDerivada.setIdServicio(idServicio);
            citaDerivada.setFecha(fechaDerivada);
            citaDerivada.setHora(horaDerivada);
            citaDerivada.setEstado("derivada_sin_pagar");
            citaDerivada.setObservaciones(observacionesDerivada);
            
            boolean citaCreada = citaDAO.crear(citaDerivada);
            
            if (citaCreada) {
                // Obtener el ID de la cita recién creada
                int idCitaDerivada = citaDAO.obtenerUltimaIdCita(citaOriginal.getIdPaciente());
                
                // Crear el pago con fecha límite personalizada
                Pago pago = new Pago();
                pago.setIdCita(idCitaDerivada);
                pago.setMonto(precioDerivado);
                pago.setMetodoPago(null);
                pago.setEstadoPago("pendiente");
                pago.setFechaLimitePago(LocalDateTime.now().plusDays(diasLimitePago));
                
                boolean pagoCreado = pagoDAO.crear(pago);
                
                if (pagoCreado) {
                    // Crear notificación para el paciente
                    Servicio servicio = servicioDAO.obtenerPorId(idServicio);
                    String mensaje = String.format(
                        "Se ha programado una cita derivada de %s para el %s a las %s. " +
                        "Tienes %d días para completar el pago de S/ %.2f",
                        servicio.getNombre(),
                        fechaDerivada.toString(),
                        horaDerivada.toString(),
                        diasLimitePago,
                        precioDerivado
                    );
                    
                    Notificacion notificacion = new Notificacion(citaOriginal.getIdPaciente(), mensaje);
                    notificacionDAO.crear(notificacion);
                    
                    request.getSession().setAttribute("mensaje", "Cita derivada creada exitosamente");
                } else {
                    request.getSession().setAttribute("error", "Cita creada pero error al generar el pago");
                }
            } else {
                request.getSession().setAttribute("error", "Error al crear la cita derivada");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error procesando la cita derivada: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard-odontologo");
    }
}
