package pe.edu.utp.controller;

import pe.edu.utp.dao.UsuarioDAO;
import pe.edu.utp.daoimpl.UsuarioDAOImpl;
import pe.edu.utp.entity.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        
        // Primero verificar si el usuario existe pero no está validado
        Usuario usuarioSinValidar = usuarioDAO.obtenerPorCorreo(correo);
        if (usuarioSinValidar != null && usuarioSinValidar.getContrasena().equals(contrasena) && !usuarioSinValidar.isCuentaValidada()) {
            request.setAttribute("error", "Tu cuenta está pendiente de validación por parte de la secretaria. Por favor espera la aprobación.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        // Autenticar usuario validado
        Usuario usuario = usuarioDAO.autenticar(correo, contrasena);
        
        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("rolUsuario", usuario.getNombreRol());
            
            // Redirigir según el rol
            switch (usuario.getNombreRol()) {
                case "paciente":
                    response.sendRedirect(request.getContextPath() + "/dashboard-paciente");
                    break;
                case "odontologo":
                    response.sendRedirect(request.getContextPath() + "/dashboard-odontologo");
                    break;
                case "secretaria":
                    response.sendRedirect(request.getContextPath() + "/dashboard-secretaria");
                    break;
                case "administrador":
                    response.sendRedirect(request.getContextPath() + "/dashboard-admin");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/inicio");
            }
        } else {
            request.setAttribute("error", "Credenciales incorrectas");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
