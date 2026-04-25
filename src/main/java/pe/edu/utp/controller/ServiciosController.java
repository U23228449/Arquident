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

@WebServlet("/servicios")
public class ServiciosController extends HttpServlet {
    private ServicioDAO servicioDAO;
    
    @Override
    public void init() throws ServletException {
        servicioDAO = new ServicioDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Servicio> servicios = servicioDAO.listarTodos();
        request.setAttribute("servicios", servicios);
        request.getRequestDispatcher("/WEB-INF/views/servicios.jsp").forward(request, response);
    }
}
