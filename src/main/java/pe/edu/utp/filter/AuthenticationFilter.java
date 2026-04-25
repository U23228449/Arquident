package pe.edu.utp.filter;

import pe.edu.utp.entity.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({
    "/dashboard-paciente", 
    "/dashboard-odontologo", 
    "/dashboard-secretaria", 
    "/dashboard-admin",
    "/reservar-cita", 
    "/mis-citas",
    "/validacion-usuarios"
})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ArquiDent: Filtro de autenticación inicializado");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        // Verificar si el usuario está autenticado
        Usuario usuario = null;
        if (session != null) {
            usuario = (Usuario) session.getAttribute("usuario");
        }
        
        if (usuario == null) {
            // Usuario no autenticado, redirigir al login
            System.out.println("ArquiDent: Usuario no autenticado, redirigiendo al login");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        
        // Verificar que la cuenta esté validada
        if (!usuario.isCuentaValidada()) {
            System.out.println("ArquiDent: Usuario con cuenta no validada");
            session.invalidate();
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        
        // Verificar permisos específicos para rutas admin
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.contains("/dashboard-admin") && !usuario.getNombreRol().equals("administrador")) {
            System.out.println("ArquiDent: Usuario sin permisos de administrador");
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
            return;
        }
        
        // Verificar permisos para validación de usuarios (solo secretaria)
        if (requestURI.contains("/validacion-usuarios") && !usuario.getNombreRol().equals("secretaria")) {
            System.out.println("ArquiDent: Usuario sin permisos de secretaria");
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
            return;
        }
        
        // Usuario autenticado y validado, continuar con la cadena de filtros
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("ArquiDent: Filtro de autenticación destruido");
    }
}
