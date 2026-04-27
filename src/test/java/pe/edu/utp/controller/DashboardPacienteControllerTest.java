
package pe.edu.utp.controller;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;

import pe.edu.utp.dao.*;
import pe.edu.utp.entity.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class) // Activa Mockito 5
public class DashboardPacienteControllerTest {

    @InjectMocks
    private DashboardPacienteController controller;

    @Mock
    private CitaDAO citaDAO;
    @Mock
    private PagoDAO pagoDAO;
    @Mock
    private NotificacionDAO notificacionDAO;
    @Mock
    private ServicioDAO servicioDAO;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        // Usamos los Mocks de Spring que son 100% compatibles con JDK 25
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
    }

    @Test
    void testDoGet_AccesoExitosoPaciente() throws ServletException, IOException {
        
        Usuario paciente = new Usuario();
        paciente.setIdUsuario(1);
        paciente.setNombreRol("paciente");

        session.setAttribute("usuario", paciente);
        request.setSession(session);

        
        when(citaDAO.listarProximasPorPaciente(anyInt())).thenReturn(new ArrayList<>());
        when(citaDAO.listarPendientesPagoPorPaciente(anyInt())).thenReturn(new ArrayList<>());
        when(citaDAO.listarHistorialPorPaciente(anyInt())).thenReturn(new ArrayList<>());
        when(notificacionDAO.listarNoLeidasPorUsuario(anyInt())).thenReturn(new ArrayList<>());
        when(servicioDAO.listarTodos()).thenReturn(new ArrayList<>());

        
        controller.doGet(request, response);

        
        // Verificamos que se llamó a la limpieza de pagos
        verify(pagoDAO, times(1)).limpiarPagosExpirados();
        
        // Verificamos que redirigió a la vista correcta
        assertEquals("/WEB-INF/views/dashboard-paciente.jsp", response.getForwardedUrl());
        
        // Verificamos que los atributos se setearon en el request
        assertNotNull(request.getAttribute("citasProximas"));
        assertNotNull(request.getAttribute("notificaciones"));
    }

    @Test
    void testDoGet_RedirigeSiNoEsPaciente() throws ServletException, IOException {
        
        Usuario admin = new Usuario();
        admin.setNombreRol("admin"); // Rol no autorizado aquí

        session.setAttribute("usuario", admin);
        request.setSession(session);
        request.setContextPath("/SistemaDental");

        
        controller.doGet(request, response);

        
        assertEquals("/SistemaDental/login", response.getRedirectedUrl());
    }
}
