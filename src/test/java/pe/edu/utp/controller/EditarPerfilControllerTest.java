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
import pe.edu.utp.dao.UsuarioDAO;
import pe.edu.utp.entity.Usuario;

import javax.servlet.ServletException;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class EditarPerfilControllerTest {
    @InjectMocks
    private EditarPerfilController controller;

    @Mock
    private UsuarioDAO usuarioDAO;

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
    void testActualizarDatos_Exitoso() throws ServletException, IOException {
        // --- ARRANGE ---
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setCorreo("antiguo@correo.com");
        session.setAttribute("usuario", usuario);
        request.setSession(session);

        request.setParameter("accion", "actualizar-datos");
        request.setParameter("correo", "nuevo@correo.com");
        request.setParameter("telefono", "987654321"); 

        
        when(usuarioDAO.existeCorreo("nuevo@correo.com")).thenReturn(false);
        when(usuarioDAO.actualizarPerfil(any(Usuario.class))).thenReturn(true);

        // --- ACT ---
        controller.doPost(request, response);

        // --- ASSERT ---
        assertEquals("Datos actualizados correctamente", request.getAttribute("exito"));
        Usuario usuarioEnSession = (Usuario) session.getAttribute("usuario");
        assertEquals("nuevo@correo.com", usuarioEnSession.getCorreo());
        assertEquals("987654321", usuarioEnSession.getTelefono());
    }

    @Test
    void testActualizarDatos_TelefonoInvalido() throws ServletException, IOException {
        
        Usuario usuario = new Usuario();
        session.setAttribute("usuario", usuario);
        request.setSession(session);

        request.setParameter("accion", "actualizar-datos");
        request.setParameter("correo", "test@correo.com");
        request.setParameter("telefono", "123"); // Formato incorrecto (menos de 9)

        
        controller.doPost(request, response);

        
        assertEquals("El teléfono debe tener 9 dígitos", request.getAttribute("error"));
        verify(usuarioDAO, never()).actualizarPerfil(any());
    }

    @Test
    void testCambiarPassword_Exitoso() throws ServletException, IOException {
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setContrasena("123456"); // Password actual
        session.setAttribute("usuario", usuario);
        request.setSession(session);

        request.setParameter("accion", "cambiar-password");
        request.setParameter("password-actual", "123456");
        request.setParameter("password-nueva", "654321");
        request.setParameter("password-confirmar", "654321");

        when(usuarioDAO.cambiarPassword(eq(1), eq("654321"))).thenReturn(true);

        
        controller.doPost(request, response);

        
        assertEquals("Contraseña cambiada correctamente", request.getAttribute("exito"));
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
        assertEquals("654321", usuarioSesion.getContrasena());
    }

    @Test
    void testCambiarPassword_ActualIncorrecta() throws ServletException, IOException {
        
        Usuario usuario = new Usuario();
        usuario.setContrasena("original123");
        session.setAttribute("usuario", usuario);
        request.setSession(session);

        request.setParameter("accion", "cambiar-password");
        request.setParameter("password-actual", "error_pass");
        request.setParameter("password-nueva", "nueva123");
        request.setParameter("password-confirmar", "nueva123");

        
        controller.doPost(request, response);

        
        assertEquals("La contraseña actual no es correcta", request.getAttribute("error"));
        verify(usuarioDAO, never()).cambiarPassword(anyInt(), anyString());
    }

    @Test
    void testCambiarPassword_NoCoinciden() throws ServletException, IOException {
        
        Usuario usuario = new Usuario();
        usuario.setContrasena("123456");
        session.setAttribute("usuario", usuario);
        request.setSession(session);

        request.setParameter("accion", "cambiar-password");
        request.setParameter("password-actual", "123456");
        request.setParameter("password-nueva", "abc123");
        request.setParameter("password-confirmar", "distinta123");

        
        controller.doPost(request, response);

        
        assertEquals("Las contraseñas no coinciden", request.getAttribute("error"));
    }
}
