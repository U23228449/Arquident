package pe.edu.utp.controller;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.edu.utp.dao.ServicioDAO;
import pe.edu.utp.entity.Servicio;
import java.util.ArrayList;
import java.util.List;

public class ServiciosControllerTest {

    @InjectMocks
    private ServiciosController controller;

    @Mock private ServicioDAO servicioDAO;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void testListarServiciosExitoso() throws Exception {
        List<Servicio> serviciosMock = new ArrayList<>();
        serviciosMock.add(new Servicio());
        serviciosMock.add(new Servicio());
        
        when(servicioDAO.listarTodos()).thenReturn(serviciosMock);

        controller.doGet(request, response);
        verify(request).setAttribute(eq("servicios"), anyList());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testManejoListaVacia() throws Exception {
        when(servicioDAO.listarTodos()).thenReturn(new ArrayList<>());

        controller.doGet(request, response);

        verify(request).setAttribute(eq("servicios"), argThat(list -> ((List)list).isEmpty()));
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testLlamadaAlMetodoDao() throws Exception {
        controller.doGet(request, response);
        verify(servicioDAO, times(1)).listarTodos();
    }
}