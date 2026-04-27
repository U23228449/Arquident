/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.time.LocalDate;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class DashboardOdontologoControllerTest {

    @InjectMocks
    private DashboardOdontologoController controller;

    @Mock
    private CitaDAO citaDAO;
    @Mock
    private ServicioDAO servicioDAO;
    @Mock
    private PagoDAO pagoDAO;
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
    void testDoGet_AccesoOdontologo_CargaCitas() throws ServletException, IOException {
        // --- ARRANGE ---
        Usuario odontologo = new Usuario();
        odontologo.setIdUsuario(5);
        odontologo.setNombreRol("odontologo");
        session.setAttribute("usuario", odontologo);
        request.setSession(session);

        // Simulamos comportamiento de los DAOs
        when(citaDAO.listarCitasDelDiaPorOdontologo(eq(5), any(LocalDate.class))).thenReturn(new ArrayList<>());
        when(servicioDAO.listarTodos()).thenReturn(new ArrayList<>());

        // --- ACT ---
        controller.doGet(request, response);

        // --- ASSERT ---
        assertEquals("/WEB-INF/views/dashboard-odontologo.jsp", response.getForwardedUrl());
        assertNotNull(request.getAttribute("citasDelDia"));
        assertNotNull(request.getAttribute("fechaHoy"));
    }

    @Test
    void testDoPost_MarcarEntrada_Exitoso() throws ServletException, IOException {
        // --- ARRANGE ---
        Usuario odontologo = new Usuario();
        odontologo.setNombreRol("odontologo");
        session.setAttribute("usuario", odontologo);
        request.setSession(session);

        request.setParameter("action", "marcarEntrada");
        request.setParameter("idCita", "101");
        request.setContextPath("/DentalApp");

        // Simulamos que el DAO actualiza correctamente
        when(citaDAO.marcarInicioAtencion(eq(101), any())).thenReturn(true);

        // --- ACT ---
        controller.doPost(request, response);

        // --- ASSERT ---
        // El controlador debe redirigir de vuelta al dashboard
        assertEquals("/DentalApp/dashboard-odontologo", response.getRedirectedUrl());
        // Verificamos que se guardó el mensaje de éxito en la sesión
        assertEquals("Entrada del paciente marcada correctamente", session.getAttribute("mensaje"));
    }

    @Test
    void testDoPost_FinalizarCita_Exitoso() throws ServletException, IOException {
        // --- ARRANGE ---
        Usuario odontologo = new Usuario();
        odontologo.setNombreRol("odontologo");
        session.setAttribute("usuario", odontologo);
        request.setSession(session);

        request.setParameter("action", "finalizarCita");
        request.setParameter("idCita", "202");
        request.setParameter("observaciones", "Paciente requiere limpieza profunda");
        request.setParameter("receta", "Paracetamol 500mg");
        request.setContextPath("/DentalApp");

        // Necesitamos simular que la cita existe para construir la notificación
        Cita citaSimulada = new Cita();
        citaSimulada.setIdPaciente(15);
        citaSimulada.setNombreServicio("Consulta General");
        citaSimulada.setFecha(LocalDate.now());

        when(citaDAO.obtenerPorId(202)).thenReturn(citaSimulada);
        when(citaDAO.finalizarAtencion(eq(202), anyString(), anyString(), any())).thenReturn(true);

        // --- ACT ---
        controller.doPost(request, response);

        // --- ASSERT ---
        verify(notificacionDAO, times(1)).crear(any(Notificacion.class));
        assertEquals("Cita finalizada correctamente", session.getAttribute("mensaje"));
        assertEquals("/DentalApp/dashboard-odontologo", response.getRedirectedUrl());
    }
}
