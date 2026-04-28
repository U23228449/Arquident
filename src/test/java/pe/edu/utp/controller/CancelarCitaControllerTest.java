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

import pe.edu.utp.dao.CitaDAO;
import pe.edu.utp.dao.NotificacionDAO;
import pe.edu.utp.entity.Cita;
import pe.edu.utp.entity.Usuario;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;


@ExtendWith(MockitoExtension.class)
public class CancelarCitaControllerTest {
    @InjectMocks
    private CancelarCitaController controller;

    @Mock
    private CitaDAO citaDAO;
    @Mock
    private NotificacionDAO notificacionDAO;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
    }

    @Test
    void testCancelarCita_Exitoso() throws ServletException, IOException {
        // --- ARRANGE ---
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(10);
        session.setAttribute("usuario", usuario);
        request.setSession(session);
        
        request.setParameter("idCita", "500");

        Cita cita = new Cita();
        cita.setIdCita(500);
        cita.setIdPaciente(10); // Coincide con el usuario
        cita.setEstado("pendiente");
        cita.setFecha(LocalDate.now());
        cita.setHora(LocalTime.now());

        when(citaDAO.obtenerPorId(500)).thenReturn(cita);
        when(citaDAO.actualizar(any(Cita.class))).thenReturn(true);

        // --- ACT ---
        controller.doPost(request, response);

        // --- ASSERT ---
        // Verificamos que el contenido sea JSON
        assertEquals("application/json", response.getContentType());
        
        // Verificamos que la respuesta contenga el mensaje de éxito
        String jsonResponse = response.getContentAsString();
        assertTrue(jsonResponse.contains("\"success\":true"));
        assertTrue(jsonResponse.contains("Cita cancelada exitosamente"));
        
        // Verificamos que se creó una notificación
        verify(notificacionDAO, times(1)).crear(any());
    }

    @Test
    void testCancelarCita_NoPerteneceAlUsuario() throws ServletException, IOException {
        // --- ARRANGE ---
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(10); // Usuario logueado
        session.setAttribute("usuario", usuario);
        request.setSession(session);
        
        request.setParameter("idCita", "500");

        Cita citaDeOtro = new Cita();
        citaDeOtro.setIdPaciente(99); // ID diferente al logueado

        when(citaDAO.obtenerPorId(500)).thenReturn(citaDeOtro);

        // --- ACT ---
        controller.doPost(request, response);

        // --- ASSERT ---
        String jsonResponse = response.getContentAsString();
        assertTrue(jsonResponse.contains("\"success\":false"));
        assertTrue(jsonResponse.contains("No tienes permisos"));
        
        // Importante: No debería actualizar nada ni crear notificaciones
        verify(citaDAO, never()).actualizar(any());
        verify(notificacionDAO, never()).crear(any());
    }

    @Test
    void testCancelarCita_EstadoNoPermitido() throws ServletException, IOException {
        // --- ARRANGE ---
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(10);
        session.setAttribute("usuario", usuario);
        request.setSession(session);
        
        request.setParameter("idCita", "500");

        Cita citaCompletada = new Cita();
        citaCompletada.setIdPaciente(10);
        citaCompletada.setEstado("completada"); // Ya está completada

        when(citaDAO.obtenerPorId(500)).thenReturn(citaCompletada);

        // --- ACT ---
        controller.doPost(request, response);

        // --- ASSERT ---
        String jsonResponse = response.getContentAsString();
        assertTrue(jsonResponse.contains("Esta cita no se puede cancelar"));
        verify(citaDAO, never()).actualizar(any());
    }
    
}
