package pe.edu.utp.controller;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.http.*;
import pe.edu.utp.dao.UsuarioDAO;
import pe.edu.utp.dao.NotificacionDAO;
import pe.edu.utp.entity.Usuario;
import pe.edu.utp.entity.Notificacion;

public class ValidacionUsuariosControllerTest {

    @InjectMocks
    private ValidacionUsuariosController controller;

    @Mock private UsuarioDAO usuarioDAO;
    @Mock private NotificacionDAO notificacionDAO;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/ArquiDent");
    }

    @Test
    public void testAccesoDenegadoParaPacientes() throws Exception {
        Usuario usuarioPaciente = new Usuario();
        usuarioPaciente.setNombreRol("paciente");
        when(session.getAttribute("usuario")).thenReturn(usuarioPaciente);

        controller.doGet(request, response);

        verify(response).sendRedirect("/ArquiDent/login");
    }

    @Test
    public void testAprobarUsuarioExitoso() throws Exception {
        Usuario secretaria = new Usuario();
        secretaria.setNombreRol("secretaria");
        when(session.getAttribute("usuario")).thenReturn(secretaria);

        when(request.getParameter("action")).thenReturn("aprobar");
        when(request.getParameter("idUsuario")).thenReturn("50");

        when(usuarioDAO.aprobarUsuario(50)).thenReturn(true);
        Usuario usuarioAprobado = new Usuario();
        usuarioAprobado.setIdUsuario(50);
        when(usuarioDAO.obtenerPorId(50)).thenReturn(usuarioAprobado);

        controller.doPost(request, response);

        verify(usuarioDAO).aprobarUsuario(50);
        verify(notificacionDAO).crear(any(Notificacion.class));
        verify(session).setAttribute("mensaje", "Usuario aprobado exitosamente");
    }

    @Test
    public void testRechazarUsuarioConMotivo() throws Exception {
        Usuario secretaria = new Usuario();
        secretaria.setNombreRol("secretaria");
        when(session.getAttribute("usuario")).thenReturn(secretaria);

        when(request.getParameter("action")).thenReturn("rechazar");
        when(request.getParameter("idUsuario")).thenReturn("60");
        when(request.getParameter("motivoRechazo")).thenReturn("DNI borroso");

        when(usuarioDAO.rechazarUsuario(60)).thenReturn(true);

        controller.doPost(request, response);

        verify(usuarioDAO).rechazarUsuario(60);
        verify(session).setAttribute("mensaje", "Usuario rechazado exitosamente");
    }
}