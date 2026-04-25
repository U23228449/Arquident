package pe.edu.utp.controller;

import pe.edu.utp.dao.UsuarioDAO;
import pe.edu.utp.dao.NotificacionDAO;
import pe.edu.utp.daoimpl.UsuarioDAOImpl;
import pe.edu.utp.daoimpl.NotificacionDAOImpl;
import pe.edu.utp.entity.Usuario;
import pe.edu.utp.entity.Notificacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/validacion-usuarios")
public class ValidacionUsuariosController extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    private NotificacionDAO notificacionDAO;
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAOImpl();
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
            // Obtener usuarios pendientes de validación
            List<Usuario> usuariosPendientes = usuarioDAO.listarUsuariosPendientesValidacion();
            
            request.setAttribute("usuariosPendientes", usuariosPendientes);
            request.getRequestDispatcher("/WEB-INF/views/validacion-usuarios.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error cargando usuarios pendientes: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/validacion-usuarios.jsp").forward(request, response);
        }
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
        String idUsuarioStr = request.getParameter("idUsuario");
        
        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);
            
            if ("aprobar".equals(action)) {
                aprobarUsuario(idUsuario, request, response);
            } else if ("rechazar".equals(action)) {
                rechazarUsuario(idUsuario, request, response);
            }
            
        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID de usuario inválido");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error procesando la solicitud: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/validacion-usuarios");
    }
    
    private void aprobarUsuario(int idUsuario, HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        boolean aprobado = usuarioDAO.aprobarUsuario(idUsuario);
        
        if (aprobado) {
            // Obtener datos del usuario para notificación
            Usuario usuarioAprobado = usuarioDAO.obtenerPorId(idUsuario);
            if (usuarioAprobado != null) {
                String mensaje = "¡Felicidades! Tu cuenta ha sido aprobada por la secretaria. " +
                               "Ya puedes iniciar sesión y agendar tus citas.";
                
                Notificacion notificacion = new Notificacion(idUsuario, mensaje);
                notificacionDAO.crear(notificacion);
            }
            
            request.getSession().setAttribute("mensaje", "Usuario aprobado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Error al aprobar el usuario");
        }
    }
    
    private void rechazarUsuario(int idUsuario, HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        String motivoRechazo = request.getParameter("motivoRechazo");
        if (motivoRechazo == null || motivoRechazo.trim().isEmpty()) {
            motivoRechazo = "Documentos no válidos o información incorrecta";
        }
        
        // Obtener datos del usuario antes de eliminarlo
        Usuario usuarioRechazado = usuarioDAO.obtenerPorId(idUsuario);
        
        boolean rechazado = usuarioDAO.rechazarUsuario(idUsuario);
        
        if (rechazado) {
            // Si el usuario aún existe (no fue eliminado), enviar notificación
            if (usuarioRechazado != null) {
                String mensaje = "Tu solicitud de registro ha sido rechazada. " +
                               "Motivo: " + motivoRechazo + ". " +
                               "Puedes volver a registrarte con documentos válidos.";
                
                try {
                    Notificacion notificacion = new Notificacion(idUsuario, mensaje);
                    notificacionDAO.crear(notificacion);
                } catch (Exception e) {
                    // Si falla la notificación, no es crítico
                    System.err.println("Error enviando notificación de rechazo: " + e.getMessage());
                }
            }
            
            request.getSession().setAttribute("mensaje", "Usuario rechazado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Error al rechazar el usuario");
        }
    }
}
