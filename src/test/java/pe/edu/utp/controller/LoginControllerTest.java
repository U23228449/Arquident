package pe.edu.utp.controller;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import pe.edu.utp.dao.UsuarioDAO;
import pe.edu.utp.entity.Usuario;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UsuarioDAO usuarioDAO;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginExitosoPacienteReal() throws Exception {
        when(request.getParameter("correo")).thenReturn("carlos@paciente.com");
        when(request.getParameter("contrasena")).thenReturn("pass123");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/ArquiDent");

        Usuario u = new Usuario();
        u.setNombreRol("paciente");
        u.setCuentaValidada(true);
        u.setContrasena("pass123"); 

        when(usuarioDAO.obtenerPorCorreo("carlos@paciente.com")).thenReturn(u);
        when(usuarioDAO.autenticar("carlos@paciente.com", "pass123")).thenReturn(u);

        loginController.doPost(request, response);

        verify(response).sendRedirect("/ArquiDent/dashboard-paciente");
        verify(session).setAttribute("rolUsuario", "paciente");
    }

    @Test
    public void testLoginCuentaPendienteSecretaria() throws Exception {
        when(request.getParameter("correo")).thenReturn("ana@arqui.com");
        when(request.getParameter("contrasena")).thenReturn("secret123");
        
        Usuario u = new Usuario();
        u.setContrasena("secret123");
        u.setCuentaValidada(false); 
        
        when(usuarioDAO.obtenerPorCorreo("ana@arqui.com")).thenReturn(u);
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(dispatcher);

        loginController.doPost(request, response);

        verify(request).setAttribute(eq("error"), contains("pendiente de validación"));
    }

    @Test
    public void testLoginCredencialesErroneas() throws Exception {
        when(request.getParameter("correo")).thenReturn("admin@arqui.com");
        when(request.getParameter("contrasena")).thenReturn("clave_incorrecta");
        
        when(usuarioDAO.autenticar(anyString(), anyString())).thenReturn(null);
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(dispatcher);

        loginController.doPost(request, response);

        verify(request).setAttribute("error", "Credenciales incorrectas");
    }
}