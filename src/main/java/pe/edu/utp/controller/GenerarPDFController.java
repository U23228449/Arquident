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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@WebServlet("/generar-pdf")
public class GenerarPDFController extends HttpServlet {
    
    private CitaDAO citaDAO;
    private PagoDAO pagoDAO;
    
    @Override
    public void init() throws ServletException {
        citaDAO = new CitaDAOImpl();
        pagoDAO = new PagoDAOImpl();
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
        
        String tipoReporte = request.getParameter("tipo");
        String fechaInicioParam = request.getParameter("fechaInicio");
        String fechaFinParam = request.getParameter("fechaFin");
        
        LocalDate fechaInicio = fechaInicioParam != null ? LocalDate.parse(fechaInicioParam) : LocalDate.now().minusDays(30);
        LocalDate fechaFin = fechaFinParam != null ? LocalDate.parse(fechaFinParam) : LocalDate.now();
        
        // Configurar respuesta para PDF
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Content-Disposition", "inline; filename=reporte_" + tipoReporte + "_" + fechaInicio + "_" + fechaFin + ".html");
        
        try {
            switch (tipoReporte) {
                case "general":
                    generarReporteGeneral(request, response, fechaInicio, fechaFin);
                    break;
                case "doctores":
                    generarReporteDoctores(request, response, fechaInicio, fechaFin);
                    break;
                case "servicios":
                    generarReporteServicios(request, response, fechaInicio, fechaFin);
                    break;
                case "ingresos":
                    generarReporteIngresos(request, response, fechaInicio, fechaFin);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de reporte no válido");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generando el reporte");
        }
    }
    
    private void generarReporteGeneral(HttpServletRequest request, HttpServletResponse response, 
                                     LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        
        Map<String, Object> reporteGeneral = citaDAO.obtenerReporteGeneral(fechaInicio, fechaFin);
        Map<String, Double> reporteIngresos = pagoDAO.obtenerReporteIngresos(fechaInicio, fechaFin);
        
        StringBuilder html = new StringBuilder();
        html.append(getHTMLHeader("Reporte General", fechaInicio, fechaFin));
        
        html.append("<div class='section'>");
        html.append("<h2>Resumen de Citas</h2>");
        html.append("<div class='stats-grid'>");
        html.append("<div class='stat-card'>");
        html.append("<div class='stat-number'>").append(reporteGeneral.get("total_citas")).append("</div>");
        html.append("<div class='stat-label'>Total Citas</div>");
        html.append("</div>");
        html.append("<div class='stat-card'>");
        html.append("<div class='stat-number'>").append(reporteGeneral.get("citas_completadas")).append("</div>");
        html.append("<div class='stat-label'>Completadas</div>");
        html.append("</div>");
        html.append("<div class='stat-card'>");
        html.append("<div class='stat-number'>").append(reporteGeneral.get("citas_canceladas")).append("</div>");
        html.append("<div class='stat-label'>Canceladas</div>");
        html.append("</div>");
        html.append("<div class='stat-card'>");
        html.append("<div class='stat-number'>").append(reporteGeneral.get("citas_pendientes_pago")).append("</div>");
        html.append("<div class='stat-label'>Pendientes Pago</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        
        html.append("<div class='section'>");
        html.append("<h2>Resumen de Ingresos</h2>");
        html.append("<div class='stats-grid'>");
        html.append("<div class='stat-card'>");
        html.append("<div class='stat-number'>S/ ").append(String.format("%.2f", reporteIngresos.get("ingresos_confirmados"))).append("</div>");
        html.append("<div class='stat-label'>Ingresos Confirmados</div>");
        html.append("</div>");
        html.append("<div class='stat-card'>");
        html.append("<div class='stat-number'>S/ ").append(String.format("%.2f", reporteIngresos.get("ingresos_pendientes"))).append("</div>");
        html.append("<div class='stat-label'>Ingresos Pendientes</div>");
        html.append("</div>");
        html.append("<div class='stat-card'>");
        html.append("<div class='stat-number'>S/ ").append(String.format("%.2f", reporteIngresos.get("ingresos_totales"))).append("</div>");
        html.append("<div class='stat-label'>Ingresos Totales</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        
        html.append(getHTMLFooter());
        
        response.getWriter().write(html.toString());
    }
    
    private void generarReporteDoctores(HttpServletRequest request, HttpServletResponse response, 
                                      LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        
        List<Map<String, Object>> reportePorDoctor = citaDAO.obtenerReportePorDoctor(fechaInicio, fechaFin);
        
        StringBuilder html = new StringBuilder();
        html.append(getHTMLHeader("Reporte por Doctor", fechaInicio, fechaFin));
        
        html.append("<div class='section'>");
        html.append("<h2>Rendimiento por Doctor</h2>");
        html.append("<table>");
        html.append("<thead>");
        html.append("<tr>");
        html.append("<th>Doctor</th>");
        html.append("<th>Total Citas</th>");
        html.append("<th>Completadas</th>");
        html.append("<th>% Efectividad</th>");
        html.append("</tr>");
        html.append("</thead>");
        html.append("<tbody>");
        
        for (Map<String, Object> fila : reportePorDoctor) {
            int totalCitas = (Integer) fila.get("total_citas");
            int citasCompletadas = (Integer) fila.get("citas_completadas");
            double efectividad = totalCitas > 0 ? (citasCompletadas * 100.0 / totalCitas) : 0;
            
            html.append("<tr>");
            html.append("<td>Dr. ").append(fila.get("doctor")).append("</td>");
            html.append("<td>").append(totalCitas).append("</td>");
            html.append("<td>").append(citasCompletadas).append("</td>");
            html.append("<td>").append(String.format("%.1f%%", efectividad)).append("</td>");
            html.append("</tr>");
        }
        
        html.append("</tbody>");
        html.append("</table>");
        html.append("</div>");
        
        html.append(getHTMLFooter());
        
        response.getWriter().write(html.toString());
    }
    
    private void generarReporteServicios(HttpServletRequest request, HttpServletResponse response, 
                                       LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        
        List<Map<String, Object>> reportePorServicio = citaDAO.obtenerReportePorServicio(fechaInicio, fechaFin);
        
        StringBuilder html = new StringBuilder();
        html.append(getHTMLHeader("Reporte por Servicio", fechaInicio, fechaFin));
        
        html.append("<div class='section'>");
        html.append("<h2>Popularidad de Servicios</h2>");
        html.append("<table>");
        html.append("<thead>");
        html.append("<tr>");
        html.append("<th>Servicio</th>");
        html.append("<th>Total Citas</th>");
        html.append("<th>Completadas</th>");
        html.append("<th>Precio</th>");
        html.append("<th>Ingresos Potenciales</th>");
        html.append("</tr>");
        html.append("</thead>");
        html.append("<tbody>");
        
        for (Map<String, Object> fila : reportePorServicio) {
            int totalCitas = (Integer) fila.get("total_citas");
            double precio = (Double) fila.get("precio_servicio");
            double ingresosPotenciales = totalCitas * precio;
            
            html.append("<tr>");
            html.append("<td>").append(fila.get("servicio")).append("</td>");
            html.append("<td>").append(totalCitas).append("</td>");
            html.append("<td>").append(fila.get("citas_completadas")).append("</td>");
            html.append("<td>S/ ").append(String.format("%.2f", precio)).append("</td>");
            html.append("<td>S/ ").append(String.format("%.2f", ingresosPotenciales)).append("</td>");
            html.append("</tr>");
        }
        
        html.append("</tbody>");
        html.append("</table>");
        html.append("</div>");
        
        html.append(getHTMLFooter());
        
        response.getWriter().write(html.toString());
    }
    
    private void generarReporteIngresos(HttpServletRequest request, HttpServletResponse response, 
                                      LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        
        Map<String, Double> reporteIngresos = pagoDAO.obtenerReporteIngresos(fechaInicio, fechaFin);
        
        StringBuilder html = new StringBuilder();
        html.append(getHTMLHeader("Reporte de Ingresos", fechaInicio, fechaFin));
        
        html.append("<div class='section'>");
        html.append("<h2>Análisis Financiero</h2>");
        html.append("<div class='stats-grid'>");
        html.append("<div class='stat-card income-confirmed'>");
        html.append("<div class='stat-number'>S/ ").append(String.format("%.2f", reporteIngresos.get("ingresos_confirmados"))).append("</div>");
        html.append("<div class='stat-label'>Ingresos Confirmados</div>");
        html.append("</div>");
        html.append("<div class='stat-card income-pending'>");
        html.append("<div class='stat-number'>S/ ").append(String.format("%.2f", reporteIngresos.get("ingresos_pendientes"))).append("</div>");
        html.append("<div class='stat-label'>Ingresos Pendientes</div>");
        html.append("</div>");
        html.append("<div class='stat-card income-total'>");
        html.append("<div class='stat-number'>S/ ").append(String.format("%.2f", reporteIngresos.get("ingresos_totales"))).append("</div>");
        html.append("<div class='stat-label'>Ingresos Totales</div>");
        html.append("</div>");
        html.append("</div>");
        
        // Calcular porcentajes
        double totalIngresos = reporteIngresos.get("ingresos_totales");
        double ingresosConfirmados = reporteIngresos.get("ingresos_confirmados");
        double ingresosPendientes = reporteIngresos.get("ingresos_pendientes");
        
        double porcentajeConfirmado = totalIngresos > 0 ? (ingresosConfirmados * 100.0 / totalIngresos) : 0;
        double porcentajePendiente = totalIngresos > 0 ? (ingresosPendientes * 100.0 / totalIngresos) : 0;
        
        html.append("<div class='analysis'>");
        html.append("<h3>Análisis de Cobranza</h3>");
        html.append("<p><strong>Efectividad de Cobranza:</strong> ").append(String.format("%.1f%%", porcentajeConfirmado)).append("</p>");
        html.append("<p><strong>Pendiente de Cobro:</strong> ").append(String.format("%.1f%%", porcentajePendiente)).append("</p>");
        html.append("</div>");
        
        html.append("</div>");
        
        html.append(getHTMLFooter());
        
        response.getWriter().write(html.toString());
    }
    
    private String getHTMLHeader(String titulo, LocalDate fechaInicio, LocalDate fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        return "<!DOCTYPE html>" +
               "<html lang='es'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<title>" + titulo + "</title>" +
               "<style>" +
               "body { font-family: Arial, sans-serif; margin: 20px; color: #333; }" +
               ".header { text-align: center; margin-bottom: 30px; border-bottom: 2px solid #3B82F6; padding-bottom: 20px; }" +
               ".header h1 { color: #3B82F6; margin: 0; font-size: 28px; }" +
               ".header .subtitle { color: #666; margin: 10px 0; font-size: 16px; }" +
               ".header .date-range { color: #888; font-size: 14px; }" +
               ".section { margin-bottom: 30px; }" +
               ".section h2 { color: #374151; border-bottom: 1px solid #E5E7EB; padding-bottom: 10px; }" +
               ".stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin: 20px 0; }" +
               ".stat-card { background: #F9FAFB; border: 1px solid #E5E7EB; border-radius: 8px; padding: 20px; text-align: center; }" +
               ".stat-number { font-size: 24px; font-weight: bold; color: #3B82F6; }" +
               ".stat-label { color: #6B7280; font-size: 14px; margin-top: 5px; }" +
               ".income-confirmed .stat-number { color: #10B981; }" +
               ".income-pending .stat-number { color: #F59E0B; }" +
               ".income-total .stat-number { color: #3B82F6; }" +
               "table { width: 100%; border-collapse: collapse; margin: 20px 0; }" +
               "th, td { border: 1px solid #E5E7EB; padding: 12px; text-align: left; }" +
               "th { background-color: #F9FAFB; font-weight: bold; color: #374151; }" +
               "tr:nth-child(even) { background-color: #F9FAFB; }" +
               ".analysis { background: #EFF6FF; border-left: 4px solid #3B82F6; padding: 20px; margin: 20px 0; }" +
               ".analysis h3 { margin-top: 0; color: #1E40AF; }" +
               ".footer { text-align: center; margin-top: 40px; padding-top: 20px; border-top: 1px solid #E5E7EB; color: #6B7280; font-size: 12px; }" +
               "@media print { body { margin: 0; } .no-print { display: none; } }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='header'>" +
               "<h1>" + titulo + "</h1>" +
               "<div class='subtitle'>Consultorio Dental</div>" +
               "<div class='date-range'>Período: " + fechaInicio.format(formatter) + " - " + fechaFin.format(formatter) + "</div>" +
               "<div class='date-range'>Generado el: " + LocalDate.now().format(formatter) + "</div>" +
               "</div>";
    }
    
    private String getHTMLFooter() {
        return "<div class='footer'>" +
               "<p>Este reporte fue generado automáticamente por el Sistema de Gestión del Consultorio Dental</p>" +
               "<p>© " + java.time.Year.now() + " - Consultorio Dental. Todos los derechos reservados.</p>" +
               "</div>" +
               "</body>" +
               "</html>";
    }
}
