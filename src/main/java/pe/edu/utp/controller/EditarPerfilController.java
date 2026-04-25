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

@WebServlet("/editar-perfil")
public class EditarPerfilController extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String accion = request.getParameter("accion");
        
        try {
            if ("actualizar-datos".equals(accion)) {
                actualizarDatos(request, response, usuario, session);
            } else if ("cambiar-password".equals(accion)) {
                cambiarPassword(request, response, usuario, session);
            } else {
                request.setAttribute("error", "Acción no válida");
                request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
        }
    }
    
    private void actualizarDatos(HttpServletRequest request, HttpServletResponse response, 
                                Usuario usuario, HttpSession session) throws ServletException, IOException {
        
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        
        // Validaciones
        if (correo == null || correo.trim().isEmpty()) {
            request.setAttribute("error", "El correo es obligatorio");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        if (telefono == null || telefono.trim().isEmpty()) {
            request.setAttribute("error", "El teléfono es obligatorio");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        // Validar formato de teléfono (9 dígitos)
        if (!telefono.matches("\\d{9}")) {
            request.setAttribute("error", "El teléfono debe tener 9 dígitos");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        // Validar formato de correo
        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            request.setAttribute("error", "El formato del correo no es válido");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        // Verificar si el correo ya existe (si es diferente al actual)
        if (!correo.equals(usuario.getCorreo()) && usuarioDAO.existeCorreo(correo)) {
            request.setAttribute("error", "Este correo ya está registrado");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        // Actualizar datos
        usuario.setCorreo(correo.trim());
        usuario.setTelefono(telefono.trim());
        
        if (usuarioDAO.actualizarPerfil(usuario)) {
            // Actualizar sesión
            session.setAttribute("usuario", usuario);
            request.setAttribute("exito", "Datos actualizados correctamente");
        } else {
            request.setAttribute("error", "Error al actualizar los datos");
        }
        
        request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
    }
    
    private void cambiarPassword(HttpServletRequest request, HttpServletResponse response, 
                                Usuario usuario, HttpSession session) throws ServletException, IOException {
        
        String passwordActual = request.getParameter("password-actual");
        String passwordNueva = request.getParameter("password-nueva");
        String passwordConfirmar = request.getParameter("password-confirmar");
        
        System.out.println("=== DEBUG CAMBIAR PASSWORD ===");
        System.out.println("Password actual recibida: " + passwordActual);
        System.out.println("Password nueva recibida: " + passwordNueva);
        System.out.println("Password confirmar recibida: " + passwordConfirmar);
        System.out.println("Password actual del usuario: " + usuario.getContrasena());
        
        // Validaciones
        if (passwordActual == null || passwordActual.trim().isEmpty()) {
            request.setAttribute("error", "La contraseña actual es obligatoria");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        if (passwordNueva == null || passwordNueva.trim().isEmpty()) {
            request.setAttribute("error", "La nueva contraseña es obligatoria");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        if (passwordConfirmar == null || passwordConfirmar.trim().isEmpty()) {
            request.setAttribute("error", "Debes confirmar la nueva contraseña");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        // Verificar contraseña actual
        if (!passwordActual.equals(usuario.getContrasena())) {
            System.out.println("ERROR: Contraseña actual no coincide");
            request.setAttribute("error", "La contraseña actual no es correcta");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        // Verificar longitud mínima
        if (passwordNueva.length() < 6) {
            request.setAttribute("error", "La nueva contraseña debe tener al menos 6 caracteres");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        // Verificar que las contraseñas coincidan
        if (!passwordNueva.equals(passwordConfirmar)) {
            request.setAttribute("error", "Las contraseñas no coinciden");
            request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
            return;
        }
        
        // Cambiar contraseña
        System.out.println("Intentando cambiar contraseña para usuario ID: " + usuario.getIdUsuario());
        boolean cambioExitoso = usuarioDAO.cambiarPassword(usuario.getIdUsuario(), passwordNueva);
        System.out.println("Resultado del cambio: " + cambioExitoso);
        
        if (cambioExitoso) {
            // Actualizar usuario en sesión
            usuario.setContrasena(passwordNueva);
            session.setAttribute("usuario", usuario);
            request.setAttribute("exito", "Contraseña cambiada correctamente");
        } else {
            request.setAttribute("error", "Error al cambiar la contraseña");
        }
        
        request.getRequestDispatcher("/WEB-INF/views/editar-perfil.jsp").forward(request, response);
    }
}
