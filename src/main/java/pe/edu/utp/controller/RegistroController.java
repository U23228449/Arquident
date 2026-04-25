package pe.edu.utp.controller;

import pe.edu.utp.dao.UsuarioDAO;
import pe.edu.utp.daoimpl.UsuarioDAOImpl;
import pe.edu.utp.entity.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@WebServlet("/registro")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class RegistroController extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    private static final String UPLOAD_DIR = "uploads/dni";
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Obtener datos del formulario
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String contrasena = request.getParameter("contrasena");
            String confirmarContrasena = request.getParameter("confirmarContrasena");
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");
            String dni = request.getParameter("dni");
            
            // Validaciones básicas
            if (nombre == null || nombre.trim().isEmpty() ||
                correo == null || correo.trim().isEmpty() ||
                contrasena == null || contrasena.trim().isEmpty() ||
                dni == null || dni.trim().isEmpty()) {
                
                request.setAttribute("error", "Todos los campos obligatorios deben ser completados");
                request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
                return;
            }
            
            // Validar que las contraseñas coincidan
            if (!contrasena.equals(confirmarContrasena)) {
                request.setAttribute("error", "Las contraseñas no coinciden");
                request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
                return;
            }
            
            // Validar DNI (8 dígitos)
            if (!dni.matches("\\d{8}")) {
                request.setAttribute("error", "El DNI debe tener exactamente 8 dígitos");
                request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
                return;
            }
            
            // Verificar si el correo ya existe
            if (usuarioDAO.existeCorreo(correo)) {
                request.setAttribute("error", "Ya existe una cuenta con este correo electrónico");
                request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
                return;
            }
            
            // Verificar si el DNI ya existe
            if (usuarioDAO.existeDni(dni)) {
                request.setAttribute("error", "Ya existe una cuenta con este DNI");
                request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
                return;
            }
            
            // Procesar archivos de DNI
            String fotoDniFrontal = procesarArchivo(request, "fotoDniFrontal", dni + "_frontal");
            String fotoDniReverso = procesarArchivo(request, "fotoDniReverso", dni + "_reverso");
            
            if (fotoDniFrontal == null || fotoDniReverso == null) {
                request.setAttribute("error", "Debe subir ambas fotos del DNI (frontal y reverso)");
                request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
                return;
            }
            
            // Crear usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre.trim());
            usuario.setCorreo(correo.trim().toLowerCase());
            usuario.setContrasena(contrasena);
            usuario.setTelefono(telefono != null ? telefono.trim() : null);
            usuario.setDireccion(direccion != null ? direccion.trim() : null);
            usuario.setDni(dni);
            usuario.setFotoDniFrontal(fotoDniFrontal);
            usuario.setFotoDniReverso(fotoDniReverso);
            usuario.setRolId(1); // Rol paciente
            usuario.setCuentaValidada(false); // Pendiente de validación
            usuario.setFechaSolicitudRegistro(LocalDateTime.now());
            
            // Registrar usuario
            boolean registrado = usuarioDAO.registrarConValidacion(usuario);
            
            if (registrado) {
                request.setAttribute("mensaje", 
                    "Registro exitoso. Tu cuenta está pendiente de validación por parte de la secretaria. " +
                    "Recibirás una notificación cuando sea aprobada.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Error al registrar el usuario. Inténtalo nuevamente.");
                request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(request, response);
        }
    }
    
    private String procesarArchivo(HttpServletRequest request, String nombreCampo, String prefijo) 
            throws IOException, ServletException {
        
        Part filePart = request.getPart(nombreCampo);
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }
        
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        
        // Validar extensión
        if (!fileExtension.toLowerCase().matches("\\.(jpg|jpeg|png)")) {
            return null;
        }
        
        // Crear directorio si no existe
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // Generar nombre único
        String nuevoNombre = prefijo + "_" + System.currentTimeMillis() + fileExtension;
        String rutaCompleta = uploadPath + File.separator + nuevoNombre;
        
        // Guardar archivo
        filePart.write(rutaCompleta);
        
        return UPLOAD_DIR + "/" + nuevoNombre;
    }
}
