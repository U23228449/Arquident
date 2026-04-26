package pe.edu.utp.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.*;
import pe.edu.utp.dao.*;
import pe.edu.utp.entity.*;

public class ProcesarPagoControllerTest {

    @InjectMocks
    private ProcesarPagoController controller;

    @Mock private PagoDAO pagoDAO;
    @Mock private CitaDAO citaDAO;
    @Mock private NotificacionDAO notificacionDAO;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;

    private StringWriter responseWriter;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getParameterNames()).thenReturn(java.util.Collections.enumeration(java.util.Collections.emptyList()));
    }

    @Test
    public void testPagoExitoso() throws Exception {
        when(request.getParameter("idCita")).thenReturn("10");
        when(request.getParameter("metodoPago")).thenReturn("tarjeta");

        Cita citaSimulada = new Cita();
        citaSimulada.setIdCita(10); 
        citaSimulada.setEstado("sin_pagar");
        citaSimulada.setIdPaciente(1);
        when(citaDAO.obtenerPorId(10)).thenReturn(citaSimulada);

        when(pagoDAO.procesarPago(10, "tarjeta")).thenReturn(true);
        when(citaDAO.actualizar(any(Cita.class))).thenReturn(true);
        when(notificacionDAO.crear(any(Notificacion.class))).thenReturn(true);

        controller.doPost(request, response);

        String output = responseWriter.toString();
        assertTrue(output.contains("\"success\":true"));
        assertTrue(output.contains("Pago procesado exitosamente"));
    }

   @Test
    public void testErrorIdCitaNoNumerico() throws Exception {
        when(request.getParameter("idCita")).thenReturn("abc");
        when(request.getParameter("metodoPago")).thenReturn("yape");

        controller.doPost(request, response);

        String output = responseWriter.toString();
        
        assertTrue(output.contains("\"success\":false"));
        
        assertTrue(output.contains("ID de cita no es un número válido"));
    }

    @Test
    public void testCitaYaPagada() throws Exception {
        when(request.getParameter("idCita")).thenReturn("15");
        when(request.getParameter("metodoPago")).thenReturn("efectivo");

        Cita citaPagada = new Cita();
        citaPagada.setEstado("confirmada");
        when(citaDAO.obtenerPorId(15)).thenReturn(citaPagada);

        controller.doPost(request, response);

        String output = responseWriter.toString();
        assertTrue(output.contains("\"success\":false"));
        assertTrue(output.contains("ya fue pagada"));
    }
}