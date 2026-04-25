<%@ include file="layout/header.jsp" %>

<section class="gradient-bg text-white py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between">
            <div>
                <h1 class="text-3xl font-bold">
                    <i class="fas fa-user-md mr-3"></i>
                    Dashboard Odontólogo
                </h1>
                <p class="text-blue-100 mt-2">
                    Bienvenido, Dr. ${usuario.nombre} - ${fechaHoy}
                </p>
            </div>
            <div class="text-right">
                <div class="text-2xl font-bold">${citasDelDia.size()}</div>
                <div class="text-blue-100">Citas del día</div>
            </div>
        </div>
    </div>
</section>

<c:if test="${not empty mensaje}">
    <section class="bg-green-50 border-l-4 border-green-500 py-4">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex items-center">
                <i class="fas fa-check-circle text-green-500 text-xl mr-3"></i>
                <p class="text-green-700">${mensaje}</p>
            </div>
        </div>
    </section>
</c:if>

<c:if test="${not empty error}">
    <section class="bg-red-50 border-l-4 border-red-500 py-4">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex items-center">
                <i class="fas fa-exclamation-triangle text-red-500 text-xl mr-3"></i>
                <p class="text-red-700">${error}</p>
            </div>
        </div>
    </section>
</c:if>

<section class="py-8 bg-gray-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="bg-white rounded-2xl shadow-xl p-8">
            
            <div class="flex flex-col md:flex-row md:items-center md:justify-between mb-6">
                <h2 class="text-2xl font-bold text-gray-800 mb-4 md:mb-0">
                    <i class="fas fa-calendar-day text-blue-500 mr-2"></i>
                    Mis Citas
                </h2>
                
                <div class="flex items-center space-x-4">
                    <div class="flex items-center space-x-2">
                        <label class="text-sm font-medium text-gray-700">Ver citas del:</label>
                        <input type="date" id="selectorFecha" 
                               value="${fechaSeleccionada}"
                               min="<%= java.time.LocalDate.now().minusMonths(1) %>"
                               max="<%= java.time.LocalDate.now().plusMonths(3) %>"
                               class="px-3 py-2 border border-gray-300 rounded-lg focus:border-blue-500 focus:outline-none"
                               onchange="cambiarFecha(this.value)">
                    </div>
                    
                    <div class="flex space-x-2">
                        <button onclick="cambiarFecha('${fechaHoy}')" 
                                class="px-3 py-2 text-sm rounded-lg transition-colors duration-300
                                       <c:choose>
                                           <c:when test='${fechaSeleccionada.equals(fechaHoy)}'>bg-blue-500 text-white</c:when>
                                           <c:otherwise>bg-gray-200 text-gray-700 hover:bg-gray-300</c:otherwise>
                                       </c:choose>">
                            Hoy
                        </button>
                        <button onclick="cambiarFecha('${fechaHoy.plusDays(1)}')" 
                                class="px-3 py-2 text-sm bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition-colors duration-300">
                            Mañana
                        </button>
                    </div>
                </div>
            </div>
            
            <div class="mb-6 p-4 bg-blue-50 rounded-xl border border-blue-200">
                <div class="flex items-center justify-between">
                    <div>
                        <h3 class="font-medium text-blue-800">
                            Citas del 
                            <c:choose>
                                <c:when test='${fechaSeleccionada.equals(fechaHoy)}'>
                                    <span class="font-bold">Hoy</span> (${fechaSeleccionada})
                                </c:when>
                                <c:when test='${fechaSeleccionada.equals(fechaHoy.plusDays(1))}'>
                                    <span class="font-bold">Mañana</span> (${fechaSeleccionada})
                                </c:when>
                                <c:when test='${fechaSeleccionada.equals(fechaHoy.minusDays(1))}'>
                                    <span class="font-bold">Ayer</span> (${fechaSeleccionada})
                                </c:when>
                                <c:otherwise>
                                    <span class="font-bold">${fechaSeleccionada}</span>
                                </c:otherwise>
                            </c:choose>
                        </h3>
                        <p class="text-blue-600 text-sm">
                            <c:choose>
                                <c:when test='${fechaSeleccionada.isBefore(fechaHoy)}'>
                                    <i class="fas fa-history mr-1"></i>
                                    Citas pasadas - Solo visualización
                                </c:when>
                                <c:when test='${fechaSeleccionada.equals(fechaHoy)}'>
                                    <i class="fas fa-clock mr-1"></i>
                                    Citas de hoy - Gestión clínica
                                </c:when>
                                <c:otherwise>
                                    <i class="fas fa-calendar-plus mr-1"></i>
                                    Citas futuras - Solo visualización
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                    <div class="text-right">
                        <div class="text-2xl font-bold text-blue-800">${citasDelDia.size()}</div>
                        <div class="text-blue-600 text-sm">Citas</div>
                    </div>
                </div>
            </div>
            
            <c:choose>
                <c:when test="${empty citasDelDia}">
                    <div class="text-center py-12">
                        <i class="fas fa-calendar-times text-gray-400 text-6xl mb-4"></i>
                        <h3 class="text-xl font-medium text-gray-500 mb-2">
                            No tienes citas programadas para 
                            <c:choose>
                                <c:when test='${fechaSeleccionada.equals(fechaHoy)}'>hoy</c:when>
                                <c:when test='${fechaSeleccionada.equals(fechaHoy.plusDays(1))}'>mañana</c:when>
                                <c:otherwise>esta fecha</c:otherwise>
                            </c:choose>
                        </h3>
                        <p class="text-gray-400">
                            <c:if test='${fechaSeleccionada.equals(fechaHoy)}'>
                                Disfruta tu día libre
                            </c:if>
                        </p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="space-y-6">
                        <c:forEach var="cita" items="${citasDelDia}">
                            <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300
                                        <c:if test='${cita.estado == "finalizada"}'>bg-gray-50</c:if>">
                                <div class="flex items-start justify-between">
                                    <div class="flex-1">
                                        <div class="flex items-center mb-3">
                                            <div class="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center mr-4">
                                                <i class="fas fa-user text-blue-500 text-xl"></i>
                                            </div>
                                            <div>
                                                <h3 class="text-lg font-bold text-gray-800">${cita.nombrePaciente}</h3>
                                                <p class="text-gray-600">
                                                    <i class="fas fa-phone mr-1"></i>
                                                    ${cita.telefonoPaciente != null ? cita.telefonoPaciente : 'Sin teléfono'}
                                                </p>
                                            </div>
                                        </div>
                                        
                                        <div class="grid md:grid-cols-2 gap-4 mb-4">
                                            <div>
                                                <p class="text-sm text-gray-600 mb-1">Servicio</p>
                                                <p class="font-medium text-gray-800">${cita.nombreServicio}</p>
                                            </div>
                                            <div>
                                                <p class="text-sm text-gray-600 mb-1">Hora Programada</p>
                                                <p class="font-medium text-gray-800">
                                                    <i class="fas fa-clock mr-1"></i>
                                                    ${cita.hora}
                                                </p>
                                            </div>
                                            <div>
                                                <p class="text-sm text-gray-600 mb-1">Estado</p>
                                                <span class="px-3 py-1 rounded-full text-xs font-medium
                                                    <c:choose>
                                                        <c:when test='${cita.estado == "confirmada"}'>bg-green-100 text-green-800</c:when>
                                                        <c:when test='${cita.estado == "sin_pagar"}'>bg-yellow-100 text-yellow-800</c:when>
                                                        <c:when test='${cita.estado == "en_atencion"}'>bg-blue-100 text-blue-800</c:when>
                                                        <c:when test='${cita.estado == "finalizada"}'>bg-gray-100 text-gray-800</c:when>
                                                        <c:otherwise>bg-gray-100 text-gray-800</c:otherwise>
                                                    </c:choose>
                                                ">
                                                    <c:choose>
                                                        <c:when test='${cita.estado == "confirmada"}'>Confirmada</c:when>
                                                        <c:when test='${cita.estado == "sin_pagar"}'>Sin Pagar</c:when>
                                                        <c:when test='${cita.estado == "en_atencion"}'>En Atención</c:when>
                                                        <c:when test='${cita.estado == "finalizada"}'>Finalizada</c:when>
                                                        <c:otherwise>${cita.estado}</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>
                                            <div>
                                                <p class="text-sm text-gray-600 mb-1">Precio</p>
                                                <p class="font-bold text-green-600">S/ ${cita.precioServicio}</p>
                                            </div>
                                        </div>
                                        
                                        <c:if test="${not empty cita.observaciones}">
                                            <div class="mb-4">
                                                <p class="text-sm text-gray-600 mb-1">Observaciones</p>
                                                <p class="text-gray-800 bg-gray-50 p-3 rounded-lg">${cita.observaciones}</p>
                                            </div>
                                        </c:if>
                                        
                                        <c:if test="${not empty cita.receta}">
                                            <div class="mb-4">
                                                <p class="text-sm text-gray-600 mb-1">Receta</p>
                                                <p class="text-gray-800 bg-green-50 p-3 rounded-lg border border-green-200">${cita.receta}</p>
                                            </div>
                                        </c:if>
                                        
                                        <c:if test="${cita.horaInicioReal != null}">
                                            <div class="mb-2">
                                                <p class="text-sm text-gray-600 mb-1">Hora de Inicio Real</p>
                                                <p class="text-gray-800">
                                                    <i class="fas fa-play text-green-500 mr-1"></i>
                                                    ${cita.horaInicioReal}
                                                </p>
                                            </div>
                                        </c:if>
                                        
                                        <c:if test="${cita.horaFinReal != null}">
                                            <div class="mb-2">
                                                <p class="text-sm text-gray-600 mb-1">Hora de Fin Real</p>
                                                <p class="text-gray-800">
                                                    <i class="fas fa-stop text-red-500 mr-1"></i>
                                                    ${cita.horaFinReal}
                                                </p>
                                            </div>
                                        </c:if>
                                    </div>
                                    
                                    <c:if test='${fechaSeleccionada.equals(fechaHoy)}'>
                                        <div class="ml-6 flex flex-col space-y-2">
                                            <c:choose>
                                                <c:when test='${cita.estado == "confirmada" || cita.estado == "sin_pagar"}'>
                                                    <button type="button" 
                                                            onclick="accionEnProceso('El registro de entrada')"
                                                            class="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition-colors duration-300 text-sm">
                                                        <i class="fas fa-play mr-1"></i>
                                                        Marcar Entrada
                                                    </button>
                                                </c:when>
                                                <c:when test='${cita.estado == "en_atencion"}'>
                                                    <button type="button" 
                                                            onclick="accionEnProceso('La finalización de consulta')" 
                                                            class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition-colors duration-300 text-sm">
                                                        <i class="fas fa-check mr-1"></i>
                                                        Finalizar Cita
                                                    </button>
                                                    
                                                    <button type="button" 
                                                            onclick="accionEnProceso('La derivación de pacientes')" 
                                                            class="bg-purple-500 text-white px-4 py-2 rounded-lg hover:bg-purple-600 transition-colors duration-300 text-sm">
                                                        <i class="fas fa-plus mr-1"></i>
                                                        Cita Derivada
                                                    </button>
                                                </c:when>
                                                <c:when test='${cita.estado == "finalizada"}'>
                                                    <div class="text-center">
                                                        <i class="fas fa-check-circle text-green-500 text-2xl"></i>
                                                        <p class="text-green-600 text-xs mt-1">Completada</p>
                                                    </div>
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </c:if>
                                    
                                    <c:if test='${!fechaSeleccionada.equals(fechaHoy)}'>
                                        <div class="ml-6 flex flex-col items-center">
                                            <c:choose>
                                                <c:when test='${fechaSeleccionada.isBefore(fechaHoy)}'>
                                                    <i class="fas fa-history text-gray-400 text-2xl"></i>
                                                    <p class="text-gray-500 text-xs mt-1">Pasada</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-calendar-plus text-blue-400 text-2xl"></i>
                                                    <p class="text-blue-500 text-xs mt-1">Futura</p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>

<div id="modalFinalizar" class="fixed inset-0 bg-black bg-opacity-50 hidden z-50">
    <div class="flex items-center justify-center min-h-screen p-4">
        <div class="bg-white rounded-2xl shadow-xl max-w-2xl w-full max-h-screen overflow-y-auto">
            <div class="p-6 text-center">
                 <h3 class="text-xl font-bold text-gray-800">En Desarrollo</h3>
                 <button onclick="cerrarModalFinalizar()" class="mt-4 bg-gray-500 text-white px-4 py-2 rounded">Cerrar</button>
            </div>
        </div>
    </div>
</div>

<div id="modalDerivada" class="fixed inset-0 bg-black bg-opacity-50 hidden z-50">
    <div class="flex items-center justify-center min-h-screen p-4">
        <div class="bg-white rounded-2xl shadow-xl max-w-3xl w-full">
             <div class="p-6 text-center">
                 <h3 class="text-xl font-bold text-gray-800">En Desarrollo</h3>
                 <button onclick="cerrarModalDerivada()" class="mt-4 bg-gray-500 text-white px-4 py-2 rounded">Cerrar</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="layout/footer.jsp" %>

<script>
// Función General para Alertas de Desarrollo
function accionEnProceso(accion) {
    alert("Funcionalidad en proceso: " + accion + " estará disponible próximamente para el registro clínico.");
}

// Funciones de Modales (Simplificadas para no hacer nada o cerrar)
function abrirModalFinalizar(idCita, nombrePaciente, nombreServicio) {
    accionEnProceso('La finalización de consulta');
}

function cerrarModalFinalizar() {
    document.getElementById('modalFinalizar').classList.add('hidden');
}

function abrirModalDerivada(idCitaOriginal, idPaciente, nombrePaciente) {
    accionEnProceso('La derivación de pacientes');
}

function cerrarModalDerivada() {
    document.getElementById('modalDerivada').classList.add('hidden');
}

function actualizarPrecioServicio(select) {
    // No hace nada en este modo
}

// Función para cambiar fecha
function cambiarFecha(fecha) {
    const url = new URL(window.location);
    url.searchParams.set('fecha', fecha);
    window.location.href = url.toString();
}

// Actualizar selector de fecha cuando se cambia
document.getElementById('selectorFecha').addEventListener('change', function() {
    cambiarFecha(this.value);
});
</script>