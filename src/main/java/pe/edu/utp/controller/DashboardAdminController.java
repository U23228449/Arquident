package pe.edu.utp.controller;

import pe.edu.utp.dao.*;
import pe.edu.utp.daoimpl.*;
import pe.edu.utp.entity.*;
import pe.edu.utp.dto.NotificacionDTO;

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

@WebServlet("/dashboard-admin")
public class DashboardAdminController extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    private ServicioDAO servicioDAO;
    private CitaDAO citaDAO;
    private PagoDAO pagoDAO;
    private FAQDAO faqDAO;
    private NotificacionDAO notificacionDAO;
    private RolDAO rolDAO;
    private HorarioOdontologoDAO horarioDAO;
    private OdontologoServicioDAO odontologoServicioDAO;
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAOImpl();
        servicioDAO = new ServicioDAOImpl();
        citaDAO = new CitaDAOImpl();
        pagoDAO = new PagoDAOImpl();
        faqDAO = new FAQDAOImpl();
        notificacionDAO = new NotificacionDAOImpl();
        rolDAO = new RolDAOImpl();
        horarioDAO = new HorarioOdontologoDAOImpl();
        odontologoServicioDAO = new OdontologoServicioDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !"administrador".equals(usuario.getNombreRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        String entity = request.getParameter("entity");
        
        try {
            if (action == null) {
                cargarDashboardPrincipal(request, response);
            } else {
                switch (action) {
                    case "list":
                        listarEntidad(request, response, entity);
                        break;
                    case "new":
                        mostrarFormularioNuevo(request, response, entity);
                        break;
                    case "edit":
                        mostrarFormularioEditar(request, response, entity);
                        break;
                    case "delete":
                        eliminarEntidad(request, response, entity);
                        break;
                    default:
                        cargarDashboardPrincipal(request, response);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error procesando la solicitud: " + e.getMessage());
            cargarDashboardPrincipal(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !"administrador".equals(usuario.getNombreRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        String entity = request.getParameter("entity");
        
        try {
            switch (action) {
                case "create":
                    crearEntidad(request, response, entity, usuario);
                    break;
                case "update":
                    actualizarEntidad(request, response, entity, usuario);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/dashboard-admin");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error procesando la solicitud: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/dashboard-admin?action=list&entity=" + entity);
        }
    }
    
    private void cargarDashboardPrincipal(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Estadísticas generales
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        List<Servicio> servicios = servicioDAO.listarTodos();
        List<Cita> citas = citaDAO.listarTodas();
        List<FAQ> faqs = faqDAO.listarTodas();
        List<Pago> pagos = pagoDAO.listarTodos(); // Obtener todos los pagos
        List<Notificacion> notificaciones = notificacionDAO.listarTodas();
        List<HorarioOdontologo> horarios = horarioDAO.listarTodos();
        List<OdontologoServicio> asignaciones = odontologoServicioDAO.listarTodos();
        
        request.setAttribute("totalUsuarios", usuarios.size());
        request.setAttribute("totalServicios", servicios.size());
        request.setAttribute("totalCitas", citas.size());
        request.setAttribute("totalFaqs", faqs.size());
        request.setAttribute("totalPagos", pagos.size());
        request.setAttribute("totalNotificaciones", notificaciones.size());
        request.setAttribute("totalHorarios", horarios.size());
        request.setAttribute("totalAsignaciones", asignaciones.size());
        
        request.getRequestDispatcher("/WEB-INF/views/dashboard-admin.jsp").forward(request, response);
    }
    
    private void listarEntidad(HttpServletRequest request, HttpServletResponse response, String entity) 
            throws ServletException, IOException {
        
        switch (entity) {
            case "usuarios":
                List<Usuario> usuarios = usuarioDAO.listarTodos();
                List<Rol> roles = rolDAO.listarTodos();
                request.setAttribute("usuarios", usuarios);
                request.setAttribute("roles", roles);
                break;
            case "servicios":
                List<Servicio> servicios = servicioDAO.listarTodos();
                request.setAttribute("servicios", servicios);
                break;
            case "citas":
                List<Cita> citas = citaDAO.listarTodas();
                List<Usuario> pacientes = usuarioDAO.listarPorRol("paciente");
                List<Usuario> odontologos = usuarioDAO.listarPorRol("odontologo");
                List<Servicio> serviciosList = servicioDAO.listarTodos();
                request.setAttribute("citas", citas);
                request.setAttribute("pacientes", pacientes);
                request.setAttribute("odontologos", odontologos);
                request.setAttribute("serviciosList", serviciosList);
                break;
            case "faqs":
                List<FAQ> faqs = faqDAO.listarTodas();
                request.setAttribute("faqs", faqs);
                break;
            case "roles":
                List<Rol> rolesList = rolDAO.listarTodos();
                request.setAttribute("rolesList", rolesList);
                break;
            case "pagos":
                List<Pago> pagos = pagoDAO.listarTodos(); // Todos los pagos
                request.setAttribute("pagos", pagos);
                break;
            case "notificaciones":
                List<NotificacionDTO> notificaciones = notificacionDAO.listarTodasConUsuario();
                request.setAttribute("notificaciones", notificaciones);
                break;
            case "horarios":
                List<HorarioOdontologo> horarios = horarioDAO.listarTodos();
                request.setAttribute("horarios", horarios);
                break;
            case "asignaciones":
                List<OdontologoServicio> asignaciones = odontologoServicioDAO.listarTodos();
                List<Usuario> odontologosList = usuarioDAO.listarPorRol("odontologo");
                List<Servicio> serviciosAsignacion = servicioDAO.listarTodos();
                request.setAttribute("asignaciones", asignaciones);
                request.setAttribute("odontologos", odontologosList);
                request.setAttribute("servicios", serviciosAsignacion);
                break;
        }
        
        request.setAttribute("entity", entity);
        request.getRequestDispatcher("/WEB-INF/views/admin-crud.jsp").forward(request, response);
    }
    
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response, String entity) 
            throws ServletException, IOException {
        
        // Cargar datos necesarios para los formularios
        if ("usuarios".equals(entity)) {
            List<Rol> roles = rolDAO.listarTodos();
            request.setAttribute("roles", roles);
        } else if ("citas".equals(entity)) {
            List<Usuario> pacientes = usuarioDAO.listarPorRol("paciente");
            List<Usuario> odontologos = usuarioDAO.listarPorRol("odontologo");
            List<Servicio> servicios = servicioDAO.listarTodos();
            request.setAttribute("pacientes", pacientes);
            request.setAttribute("odontologos", odontologos);
            request.setAttribute("servicios", servicios);
        } else if ("pagos".equals(entity)) {
            List<Cita> citas = citaDAO.listarTodas();
            request.setAttribute("citas", citas);
        } else if ("notificaciones".equals(entity)) {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            request.setAttribute("usuarios", usuarios);
        } else if ("horarios".equals(entity)) {
            List<Usuario> odontologos = usuarioDAO.listarPorRol("odontologo");
            request.setAttribute("odontologos", odontologos);
        }
        
        request.setAttribute("entity", entity);
        request.setAttribute("action", "new");
        request.getRequestDispatcher("/WEB-INF/views/admin-form.jsp").forward(request, response);
    }
    
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response, String entity) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        switch (entity) {
            case "usuarios":
                Usuario usuario = usuarioDAO.obtenerPorId(id);
                List<Rol> roles = rolDAO.listarTodos();
                request.setAttribute("usuario", usuario);
                request.setAttribute("roles", roles);
                break;
            case "servicios":
                Servicio servicio = servicioDAO.obtenerPorId(id);
                request.setAttribute("servicio", servicio);
                break;
            case "citas":
                Cita cita = citaDAO.obtenerPorId(id);
                List<Usuario> pacientes = usuarioDAO.listarPorRol("paciente");
                List<Usuario> odontologos = usuarioDAO.listarPorRol("odontologo");
                List<Servicio> servicios = servicioDAO.listarTodos();
                request.setAttribute("cita", cita);
                request.setAttribute("pacientes", pacientes);
                request.setAttribute("odontologos", odontologos);
                request.setAttribute("servicios", servicios);
                break;
            case "faqs":
                FAQ faq = faqDAO.obtenerPorId(id);
                request.setAttribute("faq", faq);
                break;
            case "roles":
                Rol rol = rolDAO.obtenerPorId(id);
                request.setAttribute("rol", rol);
                break;
            case "pagos":
                Pago pago = pagoDAO.obtenerPorCita(id);
                List<Cita> citas = citaDAO.listarTodas();
                request.setAttribute("pago", pago);
                request.setAttribute("citas", citas);
                break;
            case "notificaciones":
                Notificacion notificacion = notificacionDAO.obtenerPorId(id);
                List<Usuario> usuarios = usuarioDAO.listarTodos();
                request.setAttribute("notificacion", notificacion);
                request.setAttribute("usuarios", usuarios);
                break;
            case "horarios":
                HorarioOdontologo horario = horarioDAO.obtenerPorId(id);
                List<Usuario> odontologosList = usuarioDAO.listarPorRol("odontologo");
                request.setAttribute("horario", horario);
                request.setAttribute("odontologos", odontologosList);
                break;
        }
        
        request.setAttribute("entity", entity);
        request.setAttribute("action", "edit");
        request.getRequestDispatcher("/WEB-INF/views/admin-form.jsp").forward(request, response);
    }
    
    private void crearEntidad(HttpServletRequest request, HttpServletResponse response, String entity, Usuario usuarioActual) 
            throws ServletException, IOException {
        
        boolean exito = false;
        String mensaje = "";
        
        switch (entity) {
            case "usuarios":
                exito = crearUsuario(request);
                mensaje = exito ? "Usuario creado exitosamente" : "Error al crear usuario";
                break;
            case "servicios":
                exito = crearServicio(request);
                mensaje = exito ? "Servicio creado exitosamente" : "Error al crear servicio";
                break;
            case "citas":
                exito = crearCita(request);
                mensaje = exito ? "Cita creada exitosamente" : "Error al crear cita";
                break;
            case "faqs":
                exito = crearFAQ(request, usuarioActual);
                mensaje = exito ? "FAQ creada exitosamente" : "Error al crear FAQ";
                break;
            case "roles":
                exito = crearRol(request);
                mensaje = exito ? "Rol creado exitosamente" : "Error al crear rol";
                break;
            case "pagos":
                exito = crearPago(request);
                mensaje = exito ? "Pago creado exitosamente" : "Error al crear pago";
                break;
            case "notificaciones":
                exito = crearNotificacion(request);
                mensaje = exito ? "Notificación creada exitosamente" : "Error al crear notificación";
                break;
            case "horarios":
                exito = crearHorario(request);
                mensaje = exito ? "Horario creado exitosamente" : "Error al crear horario";
                break;
            case "asignaciones":
                exito = crearAsignacion(request);
                mensaje = exito ? "Asignación creada exitosamente" : "Error al crear asignación";
                break;
        }
        
        HttpSession session = request.getSession();
        if (exito) {
            session.setAttribute("mensaje", mensaje);
        } else {
            session.setAttribute("error", mensaje);
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard-admin?action=list&entity=" + entity);
    }
    
    private void actualizarEntidad(HttpServletRequest request, HttpServletResponse response, String entity, Usuario usuarioActual) 
            throws ServletException, IOException {
        
        boolean exito = false;
        String mensaje = "";
        
        switch (entity) {
            case "usuarios":
                exito = actualizarUsuario(request);
                mensaje = exito ? "Usuario actualizado exitosamente" : "Error al actualizar usuario";
                break;
            case "servicios":
                exito = actualizarServicio(request);
                mensaje = exito ? "Servicio actualizado exitosamente" : "Error al actualizar servicio";
                break;
            case "citas":
                exito = actualizarCita(request);
                mensaje = exito ? "Cita actualizada exitosamente" : "Error al actualizar cita";
                break;
            case "faqs":
                exito = actualizarFAQ(request);
                mensaje = exito ? "FAQ actualizada exitosamente" : "Error al actualizar FAQ";
                break;
            case "roles":
                exito = actualizarRol(request);
                mensaje = exito ? "Rol actualizado exitosamente" : "Error al actualizar rol";
                break;
            case "pagos":
                exito = actualizarPago(request);
                mensaje = exito ? "Pago actualizado exitosamente" : "Error al actualizar pago";
                break;
            case "notificaciones":
                exito = actualizarNotificacion(request);
                mensaje = exito ? "Notificación actualizada exitosamente" : "Error al actualizar notificación";
                break;
            case "horarios":
                exito = actualizarHorario(request);
                mensaje = exito ? "Horario actualizado exitosamente" : "Error al actualizar horario";
                break;
        }
        
        HttpSession session = request.getSession();
        if (exito) {
            session.setAttribute("mensaje", mensaje);
        } else {
            session.setAttribute("error", mensaje);
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard-admin?action=list&entity=" + entity);
    }
    
    private void eliminarEntidad(HttpServletRequest request, HttpServletResponse response, String entity) 
            throws ServletException, IOException {
        
        boolean exito = false;
        String mensaje = "";
        
        switch (entity) {
            case "usuarios":
                int id = Integer.parseInt(request.getParameter("id"));
                exito = usuarioDAO.eliminar(id);
                mensaje = exito ? "Usuario eliminado exitosamente" : "Error al eliminar usuario";
                break;
            case "servicios":
                int idServicio = Integer.parseInt(request.getParameter("id"));
                exito = servicioDAO.eliminar(idServicio);
                mensaje = exito ? "Servicio eliminado exitosamente" : "Error al eliminar servicio";
                break;
            case "citas":
                int idCita = Integer.parseInt(request.getParameter("id"));
                exito = citaDAO.eliminar(idCita);
                mensaje = exito ? "Cita eliminada exitosamente" : "Error al eliminar cita";
                break;
            case "faqs":
                int idFaq = Integer.parseInt(request.getParameter("id"));
                exito = faqDAO.eliminar(idFaq);
                mensaje = exito ? "FAQ eliminada exitosamente" : "Error al eliminar FAQ";
                break;
            case "roles":
                int idRol = Integer.parseInt(request.getParameter("id"));
                exito = rolDAO.eliminar(idRol);
                mensaje = exito ? "Rol eliminado exitosamente" : "Error al eliminar rol";
                break;
            case "pagos":
                int idPago = Integer.parseInt(request.getParameter("id"));
                exito = pagoDAO.eliminar(idPago);
                mensaje = exito ? "Pago eliminado exitosamente" : "Error al eliminar pago";
                break;
            case "notificaciones":
                int idNotificacion = Integer.parseInt(request.getParameter("id"));
                exito = notificacionDAO.eliminar(idNotificacion);
                mensaje = exito ? "Notificación eliminada exitosamente" : "Error al eliminar notificación";
                break;
            case "horarios":
                int idHorario = Integer.parseInt(request.getParameter("id"));
                exito = horarioDAO.eliminar(idHorario);
                mensaje = exito ? "Horario eliminado exitosamente" : "Error al eliminar horario";
                break;
            case "asignaciones":
                int idOdontologo = Integer.parseInt(request.getParameter("idOdontologo"));
                int idServicioAsignacion = Integer.parseInt(request.getParameter("idServicio"));
                exito = odontologoServicioDAO.eliminar(idOdontologo, idServicioAsignacion);
                mensaje = exito ? "Asignación eliminada exitosamente" : "Error al eliminar asignación";
                break;
        }
        
        HttpSession session = request.getSession();
        if (exito) {
            session.setAttribute("mensaje", mensaje);
        } else {
            session.setAttribute("error", mensaje);
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard-admin?action=list&entity=" + entity);
    }
    
    // Métodos auxiliares para crear entidades (los existentes más los nuevos)
    private boolean crearUsuario(HttpServletRequest request) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setCorreo(request.getParameter("correo"));
            usuario.setContrasena(request.getParameter("contrasena"));
            usuario.setTelefono(request.getParameter("telefono"));
            usuario.setDireccion(request.getParameter("direccion"));
            usuario.setRolId(Integer.parseInt(request.getParameter("rolId")));
            usuario.setDni(request.getParameter("dni"));
            usuario.setCuentaValidada(true);
            
            if (request.getParameter("fechaNacimiento") != null && !request.getParameter("fechaNacimiento").isEmpty()) {
                usuario.setFechaNacimiento(LocalDate.parse(request.getParameter("fechaNacimiento")));
            }
            
            return usuarioDAO.registrar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean crearServicio(HttpServletRequest request) {
        try {
            Servicio servicio = new Servicio();
            servicio.setNombre(request.getParameter("nombre"));
            servicio.setDescripcion(request.getParameter("descripcion"));
            servicio.setPrecio(new BigDecimal(request.getParameter("precio")));
            servicio.setRequiereConsulta("true".equals(request.getParameter("requiereConsulta")));
            
            return servicioDAO.crear(servicio);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean crearCita(HttpServletRequest request) {
        try {
            Cita cita = new Cita();
            cita.setIdPaciente(Integer.parseInt(request.getParameter("idPaciente")));
            cita.setIdOdontologo(Integer.parseInt(request.getParameter("idOdontologo")));
            cita.setIdServicio(Integer.parseInt(request.getParameter("idServicio")));
            cita.setFecha(LocalDate.parse(request.getParameter("fecha")));
            cita.setHora(LocalTime.parse(request.getParameter("hora")));
            cita.setEstado(request.getParameter("estado"));
            cita.setObservaciones(request.getParameter("observaciones"));
            
            return citaDAO.crear(cita);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean crearFAQ(HttpServletRequest request, Usuario usuarioActual) {
        try {
            FAQ faq = new FAQ();
            faq.setPregunta(request.getParameter("pregunta"));
            faq.setRespuesta(request.getParameter("respuesta"));
            faq.setIdUsuarioCreador(usuarioActual.getIdUsuario());
            
            return faqDAO.crear(faq);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean crearRol(HttpServletRequest request) {
        try {
            Rol rol = new Rol();
            rol.setNombreRol(request.getParameter("nombreRol"));
            rol.setDescripcion(request.getParameter("descripcion"));
            return rolDAO.crear(rol);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean crearPago(HttpServletRequest request) {
        try {
            Pago pago = new Pago();
            pago.setIdCita(Integer.parseInt(request.getParameter("idCita")));
            pago.setMonto(Double.parseDouble(request.getParameter("monto")));
            pago.setMetodoPago(request.getParameter("metodoPago"));
            pago.setEstadoPago(request.getParameter("estadoPago"));
            
            if (request.getParameter("fechaLimitePago") != null && !request.getParameter("fechaLimitePago").isEmpty()) {
                pago.setFechaLimitePago(LocalDateTime.parse(request.getParameter("fechaLimitePago")));
            }
            
            return pagoDAO.crear(pago);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean crearNotificacion(HttpServletRequest request) {
        try {
            String mensaje = request.getParameter("mensaje");
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            
            if (idUsuario == 0) {
                // Enviar a todos los usuarios
                List<Usuario> usuarios = usuarioDAO.listarTodos();
                boolean exito = true;
                for (Usuario usuario : usuarios) {
                    Notificacion notificacion = new Notificacion();
                    notificacion.setIdUsuario(usuario.getIdUsuario());
                    notificacion.setMensaje(mensaje);
                    notificacion.setLeido(false);
                    notificacion.setFecha(LocalDateTime.now());
                    
                    if (!notificacionDAO.crear(notificacion)) {
                        exito = false;
                    }
                }
                return exito;
            } else {
                // Enviar a un usuario específico
                Notificacion notificacion = new Notificacion();
                notificacion.setIdUsuario(idUsuario);
                notificacion.setMensaje(mensaje);
                notificacion.setLeido(false);
                notificacion.setFecha(LocalDateTime.now());
                
                return notificacionDAO.crear(notificacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean crearHorario(HttpServletRequest request) {
        try {
            HorarioOdontologo horario = new HorarioOdontologo();
            horario.setIdOdontologo(Integer.parseInt(request.getParameter("idOdontologo")));
            horario.setDiaSemana(request.getParameter("diaSemana"));
            horario.setHoraInicio(LocalTime.parse(request.getParameter("horaInicio")));
            horario.setHoraFin(LocalTime.parse(request.getParameter("horaFin")));
            
            return horarioDAO.crear(horario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean crearAsignacion(HttpServletRequest request) {
        try {
            OdontologoServicio asignacion = new OdontologoServicio();
            asignacion.setIdOdontologo(Integer.parseInt(request.getParameter("idOdontologo")));
            asignacion.setIdServicio(Integer.parseInt(request.getParameter("idServicio")));
            
            return odontologoServicioDAO.crear(asignacion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Métodos auxiliares para actualizar entidades (los existentes más los nuevos)
    private boolean actualizarUsuario(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Usuario usuario = usuarioDAO.obtenerPorId(id);
            
            if (usuario != null) {
                usuario.setNombre(request.getParameter("nombre"));
                usuario.setCorreo(request.getParameter("correo"));
                usuario.setTelefono(request.getParameter("telefono"));
                usuario.setDireccion(request.getParameter("direccion"));
                usuario.setRolId(Integer.parseInt(request.getParameter("rolId")));
                usuario.setDni(request.getParameter("dni"));
                
                if (request.getParameter("fechaNacimiento") != null && !request.getParameter("fechaNacimiento").isEmpty()) {
                    usuario.setFechaNacimiento(LocalDate.parse(request.getParameter("fechaNacimiento")));
                }
                
                String nuevaContrasena = request.getParameter("contrasena");
                if (nuevaContrasena != null && !nuevaContrasena.trim().isEmpty()) {
                    usuario.setContrasena(nuevaContrasena);
                }
                
                return usuarioDAO.actualizar(usuario);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean actualizarServicio(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Servicio servicio = servicioDAO.obtenerPorId(id);
            
            if (servicio != null) {
                servicio.setNombre(request.getParameter("nombre"));
                servicio.setDescripcion(request.getParameter("descripcion"));
                servicio.setPrecio(new BigDecimal(request.getParameter("precio")));
                servicio.setRequiereConsulta("true".equals(request.getParameter("requiereConsulta")));
                
                return servicioDAO.actualizar(servicio);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean actualizarCita(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Cita cita = citaDAO.obtenerPorId(id);
            
            if (cita != null) {
                cita.setIdOdontologo(Integer.parseInt(request.getParameter("idOdontologo")));
                cita.setIdServicio(Integer.parseInt(request.getParameter("idServicio")));
                cita.setFecha(LocalDate.parse(request.getParameter("fecha")));
                cita.setHora(LocalTime.parse(request.getParameter("hora")));
                cita.setEstado(request.getParameter("estado"));
                cita.setObservaciones(request.getParameter("observaciones"));
                cita.setReceta(request.getParameter("receta"));
                
                return citaDAO.actualizar(cita);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean actualizarFAQ(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            FAQ faq = faqDAO.obtenerPorId(id);
            
            if (faq != null) {
                faq.setPregunta(request.getParameter("pregunta"));
                faq.setRespuesta(request.getParameter("respuesta"));
                
                return faqDAO.actualizar(faq);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean actualizarRol(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Rol rol = rolDAO.obtenerPorId(id);
            
            if (rol != null) {
                rol.setNombreRol(request.getParameter("nombreRol"));
                rol.setDescripcion(request.getParameter("descripcion"));
                return rolDAO.actualizar(rol);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean actualizarPago(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Pago pago = pagoDAO.obtenerPorCita(id);
            
            if (pago != null) {
                pago.setMonto(Double.parseDouble(request.getParameter("monto")));
                pago.setMetodoPago(request.getParameter("metodoPago"));
                pago.setEstadoPago(request.getParameter("estadoPago"));
                
                if (request.getParameter("fechaLimitePago") != null && !request.getParameter("fechaLimitePago").isEmpty()) {
                    pago.setFechaLimitePago(LocalDateTime.parse(request.getParameter("fechaLimitePago")));
                }
                
                return pagoDAO.actualizar(pago);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean actualizarNotificacion(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Notificacion notificacion = notificacionDAO.obtenerPorId(id);
            
            if (notificacion != null) {
                notificacion.setMensaje(request.getParameter("mensaje"));
                notificacion.setLeido("true".equals(request.getParameter("leido")));
                
                return notificacionDAO.actualizar(notificacion);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean actualizarHorario(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            HorarioOdontologo horario = horarioDAO.obtenerPorId(id);
            
            if (horario != null) {
                horario.setDiaSemana(request.getParameter("diaSemana"));
                horario.setHoraInicio(LocalTime.parse(request.getParameter("horaInicio")));
                horario.setHoraFin(LocalTime.parse(request.getParameter("horaFin")));
                
                return horarioDAO.actualizar(horario);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
