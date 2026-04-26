package pe.edu.utp.controller;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import pe.edu.utp.dao.*;
import pe.edu.utp.entity.*;
import java.time.LocalDate;
import java.math.BigDecimal;

public class ReservarCitaControllerTest {

    @InjectMocks
    private ReservarCitaController controller;

    @Mock private CitaDAO citaDAO;
    @Mock private ServicioDAO servicioDAO;
    @Mock private UsuarioDAO usuarioDAO;
    @Mock private PagoDAO pagoDAO;
    @Mock private NotificacionDAO notificacionDAO;
    
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/ArquiDent");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void testRegistroCitaExitoso() throws Exception {
        Usuario paciente = new Usuario();
        paciente.setIdUsuario(10);
        when(session.getAttribute("usuario")).thenReturn(paciente);

        when(request.getParameter("servicio")).thenReturn("1");
        when(request.getParameter("odontologo")).thenReturn("2");
        when(request.getParameter("fecha")).thenReturn(LocalDate.now().plusDays(1).toString());
        when(request.getParameter("hora")).thenReturn("10:00");
      
        Servicio s = new Servicio();
        s.setPrecio(new BigDecimal("100.00"));
        when(servicioDAO.obtenerPorId(1)).thenReturn(s);
        when(citaDAO.tieneReservaActiva(10)).thenReturn(false);
        when(citaDAO.existeCitaEnHorario(anyInt(), any(), any())).thenReturn(false);
        when(citaDAO.crear(any(Cita.class))).thenReturn(true);
        when(usuarioDAO.obtenerPorId(2)).thenReturn(new Usuario());

        controller.doPost(request, response);
       
        verify(response).sendRedirect("/ArquiDent/dashboard-paciente");
        verify(session).setAttribute(eq("mensaje"), contains("Cita reservada exitosamente"));
    }

    @Test
    public void testErrorFechaPasada() throws Exception {
        Usuario paciente = new Usuario();
        paciente.setIdUsuario(10);
        when(session.getAttribute("usuario")).thenReturn(paciente);

        when(request.getParameter("servicio")).thenReturn("1");
        when(request.getParameter("odontologo")).thenReturn("2");
        when(request.getParameter("fecha")).thenReturn("2020-01-01"); 
        when(request.getParameter("hora")).thenReturn("10:00");

        controller.doPost(request, response);

        verify(request).setAttribute(eq("error"), contains("No se puede reservar en fechas pasadas"));
    }

    @Test
    public void testErrorCitaActivaExistente() throws Exception {
        Usuario paciente = new Usuario();
        paciente.setIdUsuario(10);
        when(session.getAttribute("usuario")).thenReturn(paciente);

        when(citaDAO.tieneReservaActiva(10)).thenReturn(true);

        controller.doGet(request, response);

        verify(request).setAttribute(eq("error"), contains("Ya tienes una cita activa"));
    }
}