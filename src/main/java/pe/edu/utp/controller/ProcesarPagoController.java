package pe.edu.utp.controller;

import pe.edu.utp.dao.*;
import pe.edu.utp.daoimpl.*;
import pe.edu.utp.entity.*;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;

@WebServlet("/procesar-pago")
public class ProcesarPagoController extends HttpServlet {
    private PagoDAO pagoDAO;
    private CitaDAO citaDAO;
    private NotificacionDAO notificacionDAO;
    
    @Override
    public void init() throws ServletException {
        pagoDAO = new PagoDAOImpl();
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
            // Debug: Mostrar todos los parámetros recibidos
            System.out.println("=== DEBUG PROCESAR PAGO ===");
            System.out.println("Content-Type: " + request.getContentType());
            System.out.println("Method: " + request.getMethod());
            
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                System.out.println("Parámetro: " + paramName + " = '" + paramValue + "'");
            }
            
            // Obtener parámetros
            String idCitaParam = request.getParameter("idCita");
            String metodoPago = request.getParameter("metodoPago");
            
            System.out.println("idCitaParam recibido: '" + idCitaParam + "'");
            System.out.println("metodoPago recibido: '" + metodoPago + "'");
            
            // Validar parámetros con más detalle
            if (idCitaParam == null) {
                System.out.println("ERROR: idCitaParam es null");
                resultado.put("success", false);
                resultado.put("message", "ID de cita no recibido (null)");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            if (idCitaParam.trim().isEmpty()) {
                System.out.println("ERROR: idCitaParam está vacío");
                resultado.put("success", false);
                resultado.put("message", "ID de cita vacío");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            if (metodoPago == null || metodoPago.trim().isEmpty()) {
                System.out.println("ERROR: metodoPago inválido");
                resultado.put("success", false);
                resultado.put("message", "Método de pago no válido");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            int idCita;
            try {
                idCita = Integer.parseInt(idCitaParam.trim());
                System.out.println("ID Cita parseado correctamente: " + idCita);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: No se puede parsear idCita: " + idCitaParam);
                resultado.put("success", false);
                resultado.put("message", "ID de cita no es un número válido: " + idCitaParam);
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            // Verificar que la cita existe y está pendiente de pago
            System.out.println("Buscando cita con ID: " + idCita);
            Cita cita = citaDAO.obtenerPorId(idCita);
            if (cita == null) {
                System.out.println("ERROR: Cita no encontrada con ID: " + idCita);
                resultado.put("success", false);
                resultado.put("message", "Cita no encontrada con ID: " + idCita);
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            System.out.println("Cita encontrada - Estado: " + cita.getEstado());
            
            if (!cita.getEstado().equals("sin_pagar") && !cita.getEstado().equals("derivada_sin_pagar")) {
                System.out.println("ERROR: Estado de cita no válido para pago: " + cita.getEstado());
                resultado.put("success", false);
                resultado.put("message", "Esta cita no requiere pago o ya fue pagada. Estado actual: " + cita.getEstado());
                response.getWriter().write(gson.toJson(resultado));
                return;
            }
            
            // Procesar el pago
            System.out.println("Procesando pago para cita ID: " + idCita + " con método: " + metodoPago);
            boolean pagoExitoso = pagoDAO.procesarPago(idCita, metodoPago.trim());
            System.out.println("Resultado del pago: " + pagoExitoso);
            
            if (pagoExitoso) {
                // Actualizar estado de la cita
                cita.setEstado("confirmada");
                boolean citaActualizada = citaDAO.actualizar(cita);
                System.out.println("Cita actualizada: " + citaActualizada);
                
                // Crear notificación
                Notificacion notificacion = new Notificacion(
                    cita.getIdPaciente(),
                    "Tu pago ha sido procesado exitosamente. Cita confirmada para " + 
                    cita.getFecha() + " a las " + cita.getHora()
                );
                boolean notificacionCreada = notificacionDAO.crear(notificacion);
                System.out.println("Notificación creada: " + notificacionCreada);
                
                resultado.put("success", true);
                resultado.put("message", "Pago procesado exitosamente");
            } else {
                resultado.put("success", false);
                resultado.put("message", "Error al procesar el pago en la base de datos");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("ERROR NumberFormatException: " + e.getMessage());
            e.printStackTrace();
            resultado.put("success", false);
            resultado.put("message", "ID de cita no válido (formato numérico incorrecto)");
        } catch (Exception e) {
            System.out.println("ERROR Exception: " + e.getMessage());
            e.printStackTrace();
            resultado.put("success", false);
            resultado.put("message", "Error interno del servidor: " + e.getMessage());
        }
        
        System.out.println("Enviando respuesta: " + gson.toJson(resultado));
        response.getWriter().write(gson.toJson(resultado));
    }
}
