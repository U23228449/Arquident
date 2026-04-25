package pe.edu.utp.controller;

import pe.edu.utp.dao.CitaDAO;
import pe.edu.utp.dao.NotificacionDAO;
import pe.edu.utp.daoimpl.CitaDAOImpl;
import pe.edu.utp.daoimpl.NotificacionDAOImpl;
import pe.edu.utp.entity.Cita;
import pe.edu.utp.entity.Notificacion;
import pe.edu.utp.entity.Usuario;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cancelar-cita")
public class CancelarCitaController extends HttpServlet {
    
    private CitaDAO citaDAO;
    private NotificacionDAO notificacionDAO;
    
    @Override
    public void init() throws ServletException {
        citaDAO = new CitaDAOImpl();
        notificacionDAO = new NotificacionDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> resultado = new HashMap<>();
        Gson gson = new Gson();
        
        try {
            HttpSession session = request.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            
            if (usuario == null) {
                resultado.put("success", false);
                resultado.put("message", "Sesión no válida");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            String idCitaParam = request.getParameter("idCita");
            
            if (idCitaParam == null || idCitaParam.trim().isEmpty()) {
                resultado.put("success", false);
                resultado.put("message", "ID de cita no válido");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            int idCita = Integer.parseInt(idCitaParam.trim());
            
            // Obtener la cita
            Cita cita = citaDAO.obtenerPorId(idCita);
            if (cita == null) {
                resultado.put("success", false);
                resultado.put("message", "Cita no encontrada");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            // Verificar que la cita pertenece al usuario
            if (cita.getIdPaciente() != usuario.getIdUsuario()) {
                resultado.put("success", false);
                resultado.put("message", "No tienes permisos para cancelar esta cita");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            // Verificar que la cita se puede cancelar
            if (cita.getEstado().equals("cancelada") || cita.getEstado().equals("completada")) {
                resultado.put("success", false);
                resultado.put("message", "Esta cita no se puede cancelar");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            // Cancelar la cita
            cita.setEstado("cancelada");
            boolean citaCancelada = citaDAO.actualizar(cita);
            
            if (citaCancelada) {
                // Crear notificación
                Notificacion notificacion = new Notificacion(
                    usuario.getIdUsuario(),
                    "Tu cita del " + cita.getFecha() + " a las " + cita.getHora() + " ha sido cancelada exitosamente."
                );
                notificacionDAO.crear(notificacion);
                
                resultado.put("success", true);
                resultado.put("message", "Cita cancelada exitosamente");
            } else {
                resultado.put("success", false);
                resultado.put("message", "Error al cancelar la cita");
            }
            
        } catch (NumberFormatException e) {
            resultado.put("success", false);
            resultado.put("message", "ID de cita no válido");
            e.printStackTrace();
        } catch (Exception e) {
            resultado.put("success", false);
            resultado.put("message", "Error interno del servidor");
            e.printStackTrace();
        }
        
        response.getWriter().write(gson.toJson(resultado));
    }
}
