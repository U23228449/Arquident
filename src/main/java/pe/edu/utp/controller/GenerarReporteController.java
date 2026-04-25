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

// Apache POI imports
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/generar-reporte")
public class GenerarReporteController extends HttpServlet {
    
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
        String formato = request.getParameter("formato"); // "pdf" or "excel"
        String fechaInicioParam = request.getParameter("fechaInicio");
        String fechaFinParam = request.getParameter("fechaFin");
        
        LocalDate fechaInicio = fechaInicioParam != null && !fechaInicioParam.isEmpty() ? LocalDate.parse(fechaInicioParam) : LocalDate.now().minusDays(30);
        LocalDate fechaFin = fechaFinParam != null && !fechaFinParam.isEmpty() ? LocalDate.parse(fechaFinParam) : LocalDate.now();
        
        try {
            if ("pdf".equalsIgnoreCase(formato)) {
                // Configurar respuesta para PDF (HTML)
                response.setContentType("text/html; charset=UTF-8");
                response.setHeader("Content-Disposition", "inline; filename=reporte_" + tipoReporte + "_" + fechaInicio + "_" + fechaFin + ".html");
                
                switch (tipoReporte) {
                    case "general":
                        generarReporteGeneralHTML(request, response, fechaInicio, fechaFin);
                        break;
                    case "doctores":
                        generarReporteDoctoresHTML(request, response, fechaInicio, fechaFin);
                        break;
                    case "servicios":
                        generarReporteServiciosHTML(request, response, fechaInicio, fechaFin);
                        break;
                    case "ingresos":
                        generarReporteIngresosHTML(request, response, fechaInicio, fechaFin);
                        break;
                    default:
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de reporte no válido");
                        break;
                }
            } else if ("excel".equalsIgnoreCase(formato)) {
                // Configurar respuesta para Excel
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=reporte_" + tipoReporte + "_" + fechaInicio + "_" + fechaFin + ".xlsx");
                
                switch (tipoReporte) {
                    case "general":
                        generarReporteGeneralExcel(response, fechaInicio, fechaFin);
                        break;
                    case "doctores":
                        generarReporteDoctoresExcel(response, fechaInicio, fechaFin);
                        break;
                    case "servicios":
                        generarReporteServiciosExcel(response, fechaInicio, fechaFin);
                        break;
                    case "ingresos":
                        generarReporteIngresosExcel(response, fechaInicio, fechaFin);
                        break;
                    default:
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de reporte no válido");
                        break;
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de reporte no válido");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generando el reporte: " + e.getMessage());
        }
    }
    
    // --- HTML Report Generation Methods (Existing) ---
    
    private void generarReporteGeneralHTML(HttpServletRequest request, HttpServletResponse response, 
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
    
    private void generarReporteDoctoresHTML(HttpServletRequest request, HttpServletResponse response, 
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
    
    private void generarReporteServiciosHTML(HttpServletRequest request, HttpServletResponse response, 
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
    
    private void generarReporteIngresosHTML(HttpServletRequest request, HttpServletResponse response, 
                                      LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        
        Map<String, Double> reporteIngresos = pagoDAO.obtenerReporteIngresos(fechaInicio, fechaFin);
        
        StringBuilder html = new StringBuilder();
        html.append(getHTMLHeader("Reporte de Ingresos", fechaInicio, fechaFin));
        
        html.append("<div class='section'>");
        html.append("<h2>Análisis Financiero</h2>");
        html.append("<div class='stats-grid'>");
        html.append("<div class='stat-card income-confirmed'>");
        html.append("<div class='stat-number'>S/ ").append(String.format("%.2f", reporteIngresos.getOrDefault("ingresos_confirmados", 0.0))).append("</div>");
        html.append("<div class='stat-label'>Ingresos Confirmados</div>");
        html.append("</div>");
        html.append("<div class='stat-card income-pending'>");
        html.append("<div class='stat-number'>S/ ").append(String.format("%.2f", reporteIngresos.getOrDefault("ingresos_pendientes", 0.0))).append("</div>");
        html.append("<div class='stat-label'>Ingresos Pendientes</div>");
        html.append("</div>");
        html.append("<div class='stat-card income-total'>");
        html.append("<div class='stat-number'>S/ ").append(String.format("%.2f", reporteIngresos.getOrDefault("ingresos_totales", 0.0))).append("</div>");
        html.append("<div class='stat-label'>Ingresos Totales</div>");
        html.append("</div>");
        html.append("</div>");
        
        // Calcular porcentajes
        double totalIngresos = reporteIngresos.getOrDefault("ingresos_totales", 0.0);
        double ingresosConfirmados = reporteIngresos.getOrDefault("ingresos_confirmados", 0.0);
        double ingresosPendientes = reporteIngresos.getOrDefault("ingresos_pendientes", 0.0);
        
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

    // --- Excel Report Generation Methods (New) ---

    private void generarReporteGeneralExcel(HttpServletResponse response, LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte General");

        // Header Style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Title Row
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte General de Citas e Ingresos");
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 2)); // Merge cells for title

        // Date Range Row
        Row dateRow = sheet.createRow(1);
        dateRow.createCell(0).setCellValue("Período: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 2));

        // Spacer row
        sheet.createRow(2);

        // Citas Summary
        Map<String, Object> reporteGeneral = citaDAO.obtenerReporteGeneral(fechaInicio, fechaFin);
        Row citasHeader = sheet.createRow(3);
        citasHeader.createCell(0).setCellValue("Resumen de Citas");
        citasHeader.getCell(0).setCellStyle(headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(3, 3, 0, 1));

        Row citasData1 = sheet.createRow(4);
        citasData1.createCell(0).setCellValue("Total Citas:");
        citasData1.createCell(1).setCellValue((Integer) reporteGeneral.get("total_citas"));

        Row citasData2 = sheet.createRow(5);
        citasData2.createCell(0).setCellValue("Completadas:");
        citasData2.createCell(1).setCellValue((Integer) reporteGeneral.get("citas_completadas"));

        Row citasData3 = sheet.createRow(6);
        citasData3.createCell(0).setCellValue("Canceladas:");
        citasData3.createCell(1).setCellValue((Integer) reporteGeneral.get("citas_canceladas"));

        Row citasData4 = sheet.createRow(7);
        citasData4.createCell(0).setCellValue("Pendientes Pago:");
        citasData4.createCell(1).setCellValue((Integer) reporteGeneral.get("citas_pendientes_pago"));
        
        // Spacer row
        sheet.createRow(8);

        // Ingresos Summary
        Map<String, Double> reporteIngresos = pagoDAO.obtenerReporteIngresos(fechaInicio, fechaFin);
        Row ingresosHeader = sheet.createRow(9);
        ingresosHeader.createCell(0).setCellValue("Resumen de Ingresos");
        ingresosHeader.getCell(0).setCellStyle(headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(9, 9, 0, 1));

        CellStyle currencyStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        currencyStyle.setDataFormat(format.getFormat("S/ #,##0.00"));

        Row ingresosData1 = sheet.createRow(10);
        ingresosData1.createCell(0).setCellValue("Ingresos Confirmados:");
        Cell cell1 = ingresosData1.createCell(1);
        cell1.setCellValue(reporteIngresos.getOrDefault("ingresos_confirmados", 0.0));
        cell1.setCellStyle(currencyStyle);

        Row ingresosData2 = sheet.createRow(11);
        ingresosData2.createCell(0).setCellValue("Ingresos Pendientes:");
        Cell cell2 = ingresosData2.createCell(1);
        cell2.setCellValue(reporteIngresos.getOrDefault("ingresos_pendientes", 0.0));
        cell2.setCellStyle(currencyStyle);

        Row ingresosData3 = sheet.createRow(12);
        ingresosData3.createCell(0).setCellValue("Ingresos Totales:");
        Cell cell3 = ingresosData3.createCell(1);
        cell3.setCellValue(reporteIngresos.getOrDefault("ingresos_totales", 0.0));
        cell3.setCellStyle(currencyStyle);

        // Auto-size columns
        for (int i = 0; i < 2; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void generarReporteDoctoresExcel(HttpServletResponse response, LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte por Doctor");

        // Header Style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Title Row
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte de Rendimiento por Doctor");
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 3));

        // Date Range Row
        Row dateRow = sheet.createRow(1);
        dateRow.createCell(0).setCellValue("Período: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 3));

        // Spacer row
        sheet.createRow(2);

        // Table Headers
        Row header = sheet.createRow(3);
        header.createCell(0).setCellValue("Doctor");
        header.createCell(1).setCellValue("Total Citas");
        header.createCell(2).setCellValue("Completadas");
        header.createCell(3).setCellValue("% Efectividad");

        for (int i = 0; i < 4; i++) {
            header.getCell(i).setCellStyle(headerStyle);
        }

        // Data Rows
        List<Map<String, Object>> reportePorDoctor = citaDAO.obtenerReportePorDoctor(fechaInicio, fechaFin);
        int rowNum = 4;
        for (Map<String, Object> fila : reportePorDoctor) {
            Row row = sheet.createRow(rowNum++);
            int totalCitas = (Integer) fila.get("total_citas");
            int citasCompletadas = (Integer) fila.get("citas_completadas");
            double efectividad = totalCitas > 0 ? (citasCompletadas * 100.0 / totalCitas) : 0;

            row.createCell(0).setCellValue("Dr. " + fila.get("doctor"));
            row.createCell(1).setCellValue(totalCitas);
            row.createCell(2).setCellValue(citasCompletadas);
            row.createCell(3).setCellValue(String.format("%.1f%%", efectividad));
        }

        // Auto-size columns
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void generarReporteServiciosExcel(HttpServletResponse response, LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte por Servicio");

        // Header Style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Title Row
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte de Popularidad de Servicios");
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 4));

        // Date Range Row
        Row dateRow = sheet.createRow(1);
        dateRow.createCell(0).setCellValue("Período: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 4));

        // Spacer row
        sheet.createRow(2);

        // Table Headers
        Row header = sheet.createRow(3);
        header.createCell(0).setCellValue("Servicio");
        header.createCell(1).setCellValue("Total Citas");
        header.createCell(2).setCellValue("Completadas");
        header.createCell(3).setCellValue("Precio");
        header.createCell(4).setCellValue("Ingresos Potenciales");

        for (int i = 0; i < 5; i++) {
            header.getCell(i).setCellStyle(headerStyle);
        }

        // Currency Style
        CellStyle currencyStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        currencyStyle.setDataFormat(format.getFormat("S/ #,##0.00"));

        // Data Rows
        List<Map<String, Object>> reportePorServicio = citaDAO.obtenerReportePorServicio(fechaInicio, fechaFin);
        int rowNum = 4;
        for (Map<String, Object> fila : reportePorServicio) {
            Row row = sheet.createRow(rowNum++);
            int totalCitas = (Integer) fila.get("total_citas");
            double precio = (Double) fila.get("precio_servicio");
            double ingresosPotenciales = totalCitas * precio;

            row.createCell(0).setCellValue((String) fila.get("servicio"));
            row.createCell(1).setCellValue(totalCitas);
            row.createCell(2).setCellValue((Integer) fila.get("citas_completadas"));
            
            Cell priceCell = row.createCell(3);
            priceCell.setCellValue(precio);
            priceCell.setCellStyle(currencyStyle);

            Cell potentialIncomeCell = row.createCell(4);
            potentialIncomeCell.setCellValue(ingresosPotenciales);
            potentialIncomeCell.setCellStyle(currencyStyle);
        }

        // Auto-size columns
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void generarReporteIngresosExcel(HttpServletResponse response, LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte de Ingresos");

        // Header Style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Title Row
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte de Ingresos Detallado");
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 1));

        // Date Range Row
        Row dateRow = sheet.createRow(1);
        dateRow.createCell(0).setCellValue("Período: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 1));

        // Spacer row
        sheet.createRow(2);

        // Ingresos Summary
        Map<String, Double> reporteIngresos = pagoDAO.obtenerReporteIngresos(fechaInicio, fechaFin);
        Row ingresosHeader = sheet.createRow(3);
        ingresosHeader.createCell(0).setCellValue("Análisis Financiero");
        ingresosHeader.getCell(0).setCellStyle(headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(3, 3, 0, 1));

        CellStyle currencyStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        currencyStyle.setDataFormat(format.getFormat("S/ #,##0.00"));

        Row ingresosData1 = sheet.createRow(4);
        ingresosData1.createCell(0).setCellValue("Ingresos Confirmados:");
        Cell cell1 = ingresosData1.createCell(1);
        cell1.setCellValue(reporteIngresos.getOrDefault("ingresos_confirmados", 0.0));
        cell1.setCellStyle(currencyStyle);

        Row ingresosData2 = sheet.createRow(5);
        ingresosData2.createCell(0).setCellValue("Ingresos Pendientes:");
        Cell cell2 = ingresosData2.createCell(1);
        cell2.setCellValue(reporteIngresos.getOrDefault("ingresos_pendientes", 0.0));
        cell2.setCellStyle(currencyStyle);

        Row ingresosData3 = sheet.createRow(6);
        ingresosData3.createCell(0).setCellValue("Ingresos Totales:");
        Cell cell3 = ingresosData3.createCell(1);
        cell3.setCellValue(reporteIngresos.getOrDefault("ingresos_totales", 0.0));
        cell3.setCellStyle(currencyStyle);

        // Spacer row
        sheet.createRow(7);

        // Percentage Analysis
        double totalIngresos = reporteIngresos.getOrDefault("ingresos_totales", 0.0);
        double ingresosConfirmados = reporteIngresos.getOrDefault("ingresos_confirmados", 0.0);
        double ingresosPendientes = reporteIngresos.getOrDefault("ingresos_pendientes", 0.0);
        
        double porcentajeConfirmado = totalIngresos > 0 ? (ingresosConfirmados * 100.0 / totalIngresos) : 0;
        double porcentajePendiente = totalIngresos > 0 ? (ingresosPendientes * 100.0 / totalIngresos) : 0;

        Row analysisHeader = sheet.createRow(8);
        analysisHeader.createCell(0).setCellValue("Análisis de Cobranza");
        analysisHeader.getCell(0).setCellStyle(headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(8, 8, 0, 1));

        Row analysisData1 = sheet.createRow(9);
        analysisData1.createCell(0).setCellValue("Efectividad de Cobranza:");
        analysisData1.createCell(1).setCellValue(String.format("%.1f%%", porcentajeConfirmado));

        Row analysisData2 = sheet.createRow(10);
        analysisData2.createCell(0).setCellValue("Pendiente de Cobro:");
        analysisData2.createCell(1).setCellValue(String.format("%.1f%%", porcentajePendiente));

        // Auto-size columns
        for (int i = 0; i < 2; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
