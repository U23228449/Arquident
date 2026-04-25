package pe.edu.utp.controller;

import pe.edu.utp.dao.NotificacionDAO;
import pe.edu.utp.daoimpl.NotificacionDAOImpl;
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

@WebServlet("/marcar-notificacion")
public class MarcarNotificacionController extends HttpServlet {
    
    private NotificacionDAO notificacionDAO;
    
    @Override
    public void init() throws ServletException {
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
            
            String idNotificacionParam = request.getParameter("idNotificacion");
            
            if (idNotificacionParam == null || idNotificacionParam.trim().isEmpty()) {
                resultado.put("success", false);
                resultado.put("message", "ID de notificación no válido");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            int idNotificacion = Integer.parseInt(idNotificacionParam.trim());
            
            boolean marcada = notificacionDAO.marcarComoLeida(idNotificacion);
            
            if (marcada) {
                resultado.put("success", true);
                resultado.put("message", "Notificación marcada como leída");
            } else {
                resultado.put("success", false);
                resultado.put("message", "Error al marcar la notificación");
            }
            
        } catch (NumberFormatException e) {
            resultado.put("success", false);
            resultado.put("message", "ID de notificación no válido");
            e.printStackTrace();
        } catch (Exception e) {
            resultado.put("success", false);
            resultado.put("message", "Error interno del servidor");
            e.printStackTrace();
        }
        
        response.getWriter().write(gson.toJson(resultado));
    }
}
