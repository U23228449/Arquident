package pe.edu.utp.controller;

import pe.edu.utp.dao.FAQDAO;
import pe.edu.utp.daoimpl.FAQDAOImpl;
import pe.edu.utp.entity.FAQ;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/faq")
public class FAQController extends HttpServlet {
    private FAQDAO faqDao;
    
    @Override
    public void init() throws ServletException {
        faqDao = new FAQDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<FAQ> faqs = new ArrayList<>();
        String error = null;
        
        try {
            // Obtener FAQs de la base de datos usando el DAO existente
            faqs = faqDao.listarTodas();
        
            if (faqs == null) {
                faqs = new ArrayList<>();
            }
        
        } catch (Exception e) {
            // En caso de error, mostrar mensaje de error y lista vacía
            error = "Error al cargar las preguntas frecuentes. Por favor, intenta más tarde.";
            faqs = new ArrayList<>();
        
            // Log del error para debugging
            System.err.println("Error en FAQController: " + e.getMessage());
            e.printStackTrace();
        }
    
        request.setAttribute("faqs", faqs);
        if (error != null) {
            request.setAttribute("error", error);
        }
    
        request.getRequestDispatcher("/WEB-INF/views/faq.jsp").forward(request, response);
    }
}
