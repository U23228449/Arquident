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
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.*;
import pe.edu.utp.dao.CitaDAO;
import pe.edu.utp.dao.PagoDAO;
import pe.edu.utp.entity.Usuario;

public class GenerarPDFControllerTest {

    @InjectMocks
    private GenerarPDFController controller;

    @Mock private CitaDAO citaDAO;
    @Mock private PagoDAO pagoDAO;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;

    private StringWriter responseWriter;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/ArquiDent");
    }

    @Test
    public void testAccesoDenegadoSiNoEsSecretaria() throws Exception {
        Usuario paciente = new Usuario();
        paciente.setNombreRol("paciente");
        when(session.getAttribute("usuario")).thenReturn(paciente);

        controller.doGet(request, response);

        verify(response).sendRedirect("/ArquiDent/login");
    }

    @Test
    public void testGenerarReporteGeneralExitoso() throws Exception {
        Usuario secretaria = new Usuario();
        secretaria.setNombreRol("secretaria");
        when(session.getAttribute("usuario")).thenReturn(secretaria);

        when(request.getParameter("tipo")).thenReturn("general");

        Map<String, Object> reporteMock = new HashMap<>();
        reporteMock.put("total_citas", 10);
        reporteMock.put("citas_completadas", 8);
        reporteMock.put("citas_canceladas", 1);
        reporteMock.put("citas_pendientes_pago", 1);
        when(citaDAO.obtenerReporteGeneral(any(), any())).thenReturn(reporteMock);

        Map<String, Double> ingresosMock = new HashMap<>();
        ingresosMock.put("ingresos_confirmados", 500.0);
        ingresosMock.put("ingresos_pendientes", 100.0);
        ingresosMock.put("ingresos_totales", 600.0);
        when(pagoDAO.obtenerReporteIngresos(any(), any())).thenReturn(ingresosMock);

        controller.doGet(request, response);

        String output = responseWriter.toString();
        assertTrue(output.contains("Reporte General"));
        assertTrue(output.contains("Total Citas"));
        assertTrue(output.contains("500.00")); 
    }

    @Test
    public void testErrorTipoReporteInvalido() throws Exception {
        Usuario secretaria = new Usuario();
        secretaria.setNombreRol("secretaria");
        when(session.getAttribute("usuario")).thenReturn(secretaria);

        when(request.getParameter("tipo")).thenReturn("inventado");

        controller.doGet(request, response);

        verify(response).sendError(eq(HttpServletResponse.SC_BAD_REQUEST), anyString());
    }
}