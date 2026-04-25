package pe.edu.utp.util;

public class ServicioUtil {
    
    // Duración fija para todas las citas: 45 minutos
    public static final int DURACION_CITA_MINUTOS = 45;
    
    /**
     * Obtiene la duración de cualquier servicio (siempre 45 minutos)
     */
    public static int obtenerDuracionServicio(String nombreServicio) {
        return DURACION_CITA_MINUTOS;
    }
    
    /**
     * Obtiene la duración de cualquier servicio por ID (siempre 45 minutos)
     */
    public static int obtenerDuracionServicio(int idServicio) {
        return DURACION_CITA_MINUTOS;
    }
    
    /**
     * Formatea la duración en texto legible
     */
    public static String formatearDuracion(int minutos) {
        if (minutos < 60) {
            return minutos + " min";
        } else {
            int horas = minutos / 60;
            int minutosRestantes = minutos % 60;
            if (minutosRestantes == 0) {
                return horas + " h";
            } else {
                return horas + " h " + minutosRestantes + " min";
            }
        }
    }
    
    /**
     * Obtiene la duración formateada para cualquier servicio
     */
    public static String obtenerDuracionFormateada(String nombreServicio) {
        return formatearDuracion(DURACION_CITA_MINUTOS);
    }
    
    /**
     * Obtiene la duración formateada para cualquier servicio por ID
     */
    public static String obtenerDuracionFormateada(int idServicio) {
        return formatearDuracion(DURACION_CITA_MINUTOS);
    }
}
