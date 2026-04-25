package pe.edu.utp.controller;

import pe.edu.utp.dao.ServicioDAO;
import pe.edu.utp.daoimpl.ServicioDAOImpl;
import pe.edu.utp.entity.Servicio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/inicio")
public class InicioController extends HttpServlet {
    private ServicioDAO servicioDAO;
    
    @Override
    public void init() throws ServletException {
        servicioDAO = new ServicioDAOImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener servicios destacados para mostrar en inicio
        List<Servicio> serviciosDestacados = servicioDAO.listarTodos();
        if (serviciosDestacados.size() > 3) {
            serviciosDestacados = serviciosDestacados.subList(0, 3);
        }
        
        request.setAttribute("serviciosDestacados", serviciosDestacados);
        request.getRequestDispatcher("/WEB-INF/views/inicio.jsp").forward(request, response);
    }
}
