<%@ include file="layout/header.jsp" %>

<!-- Header -->
<section class="gradient-bg text-white py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between">
            <div>
                <h1 class="text-3xl font-bold">
                    <i class="fas fa-user-check mr-3"></i>
                    Validación de Usuarios
                </h1>
                <p class="text-blue-100 mt-2">
                    Revisar y aprobar solicitudes de registro
                </p>
            </div>
            <div class="text-right">
                <div class="text-2xl font-bold">${usuariosPendientes.size()}</div>
                <div class="text-blue-100">Solicitudes pendientes</div>
            </div>
        </div>
    </div>
</section>

<!-- Alertas -->
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

<!-- Navegación -->
<section class="bg-white border-b">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <nav class="flex space-x-8">
            <a href="${pageContext.request.contextPath}/dashboard-secretaria" 
               class="py-4 px-1 border-b-2 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300 font-medium text-sm transition-colors duration-300">
                <i class="fas fa-arrow-left mr-2"></i>
                Volver al Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/validacion-usuarios" 
               class="py-4 px-1 border-b-2 border-blue-500 text-blue-600 font-medium text-sm">
                <i class="fas fa-user-check mr-2"></i>
                Validar Usuarios
            </a>
        </nav>
    </div>
</section>

<!-- Contenido principal -->
<section class="py-8 bg-gray-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <c:choose>
            <c:when test="${empty usuariosPendientes}">
                <div class="bg-white rounded-2xl shadow-xl p-12 text-center">
                    <i class="fas fa-check-circle text-green-400 text-6xl mb-4"></i>
                    <h3 class="text-xl font-medium text-gray-500 mb-2">¡Excelente! No hay solicitudes pendientes</h3>
                    <p class="text-gray-400">Todas las solicitudes de registro han sido procesadas</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="space-y-6">
                    <c:forEach var="usuarioPendiente" items="${usuariosPendientes}">
                        <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                            <div class="p-8">
                                <div class="flex items-start justify-between mb-6">
                                    <div class="flex items-center">
                                        <div class="w-16 h-16 bg-yellow-100 rounded-full flex items-center justify-center mr-4">
                                            <i class="fas fa-user text-yellow-500 text-2xl"></i>
                                        </div>
                                        <div>
                                            <h3 class="text-xl font-bold text-gray-800">${usuarioPendiente.nombre}</h3>
                                            <p class="text-gray-600">DNI: ${usuarioPendiente.dni}</p>
                                            <p class="text-gray-600">
                                                <i class="fas fa-envelope mr-1"></i>
                                                ${usuarioPendiente.correo}
                                            </p>
                                            <c:if test="${not empty usuarioPendiente.telefono}">
                                                <p class="text-gray-600">
                                                    <i class="fas fa-phone mr-1"></i>
                                                    ${usuarioPendiente.telefono}
                                                </p>
                                            </c:if>
                                        </div>
                                    </div>
                                    
                                    <div class="text-right">
                                        <span class="bg-yellow-100 text-yellow-800 px-3 py-1 rounded-full text-sm font-medium">
                                            Pendiente
                                        </span>
                                        <p class="text-sm text-gray-500 mt-2">
                                            Registrado: 
                                            <fmt:formatDate value="${usuarioPendiente.fechaSolicitudRegistro}" 
                                                          pattern="dd/MM/yyyy HH:mm" />
                                        </p>
                                    </div>
                                </div>
                                
                                <!-- Información adicional -->
                                <div class="grid md:grid-cols-2 gap-6 mb-6">
                                    <div>
                                        <h4 class="font-bold text-gray-800 mb-2">Información Personal</h4>
                                        <div class="space-y-1 text-sm text-gray-600">
                                            <c:if test="${not empty usuarioPendiente.direccion}">
                                                <p><strong>Dirección:</strong> ${usuarioPendiente.direccion}</p>
                                            </c:if>
                                            <c:if test="${not empty usuarioPendiente.fechaNacimiento}">
                                                <p><strong>Fecha Nacimiento:</strong> 
                                                   <fmt:formatDate value="${usuarioPendiente.fechaNacimiento}" pattern="dd/MM/yyyy" />
                                                </p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- Documentos DNI -->
                                <div class="mb-6">
                                    <h4 class="font-bold text-gray-800 mb-4">Documentos de Identidad</h4>
                                    <div class="grid md:grid-cols-2 gap-6">
                                        <div>
                                            <h5 class="font-medium text-gray-700 mb-2">DNI - Frontal</h5>
                                            <c:choose>
                                                <c:when test="${not empty usuarioPendiente.fotoDniFrontal}">
                                                    <div class="border-2 border-gray-200 rounded-xl p-4">
                                                        <img src="${pageContext.request.contextPath}/${usuarioPendiente.fotoDniFrontal}" 
                                                             alt="DNI Frontal" 
                                                             class="w-full h-48 object-contain rounded-lg cursor-pointer"
                                                             onclick="abrirModalImagen('${pageContext.request.contextPath}/${usuarioPendiente.fotoDniFrontal}', 'DNI Frontal - ${usuarioPendiente.nombre}')">
                                                        <p class="text-center text-sm text-gray-500 mt-2">Click para ampliar</p>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="border-2 border-red-200 bg-red-50 rounded-xl p-4 text-center">
                                                        <i class="fas fa-exclamation-triangle text-red-500 text-2xl mb-2"></i>
                                                        <p class="text-red-700">No se subió imagen</p>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        
                                        <div>
                                            <h5 class="font-medium text-gray-700 mb-2">DNI - Reverso</h5>
                                            <c:choose>
                                                <c:when test="${not empty usuarioPendiente.fotoDniReverso}">
                                                    <div class="border-2 border-gray-200 rounded-xl p-4">
                                                        <img src="${pageContext.request.contextPath}/${usuarioPendiente.fotoDniReverso}" 
                                                             alt="DNI Reverso" 
                                                             class="w-full h-48 object-contain rounded-lg cursor-pointer"
                                                             onclick="abrirModalImagen('${pageContext.request.contextPath}/${usuarioPendiente.fotoDniReverso}', 'DNI Reverso - ${usuarioPendiente.nombre}')">
                                                        <p class="text-center text-sm text-gray-500 mt-2">Click para ampliar</p>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="border-2 border-red-200 bg-red-50 rounded-xl p-4 text-center">
                                                        <i class="fas fa-exclamation-triangle text-red-500 text-2xl mb-2"></i>
                                                        <p class="text-red-700">No se subió imagen</p>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- Acciones -->
                                <div class="flex justify-end space-x-4">
                                    <button onclick="abrirModalRechazo(${usuarioPendiente.idUsuario}, '${usuarioPendiente.nombre}')" 
                                            class="bg-red-500 text-white px-6 py-3 rounded-xl hover:bg-red-600 transition-colors duration-300">
                                        <i class="fas fa-times mr-2"></i>
                                        Rechazar
                                    </button>
                                    
                                    <form method="post" action="${pageContext.request.contextPath}/validacion-usuarios" class="inline">
                                        <input type="hidden" name="action" value="aprobar">
                                        <input type="hidden" name="idUsuario" value="${usuarioPendiente.idUsuario}">
                                        <button type="submit" 
                                                class="bg-green-500 text-white px-6 py-3 rounded-xl hover:bg-green-600 transition-colors duration-300"
                                                onclick="return confirm('¿Estás segura de aprobar este usuario?')">
                                            <i class="fas fa-check mr-2"></i>
                                            Aprobar
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</section>

<!-- Modal para ver imágenes -->
<div id="modalImagen" class="fixed inset-0 bg-black bg-opacity-75 hidden z-50">
    <div class="flex items-center justify-center min-h-screen p-4">
        <div class="bg-white rounded-2xl shadow-xl max-w-4xl w-full max-h-screen overflow-auto">
            <div class="p-6">
                <div class="flex items-center justify-between mb-4">
                    <h3 id="tituloImagen" class="text-xl font-bold text-gray-800"></h3>
                    <button onclick="cerrarModalImagen()" class="text-gray-400 hover:text-gray-600">
                        <i class="fas fa-times text-xl"></i>
                    </button>
                </div>
                <div class="text-center">
                    <img id="imagenModal" src="/placeholder.svg" alt="" class="max-w-full max-h-96 mx-auto rounded-lg">
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal para rechazar usuario -->
<div id="modalRechazo" class="fixed inset-0 bg-black bg-opacity-50 hidden z-50">
    <div class="flex items-center justify-center min-h-screen p-4">
        <div class="bg-white rounded-2xl shadow-xl max-w-lg w-full">
            <div class="p-6">
                <div class="flex items-center justify-between mb-6">
                    <h3 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-times-circle text-red-500 mr-2"></i>
                        Rechazar Usuario
                    </h3>
                    <button onclick="cerrarModalRechazo()" class="text-gray-400 hover:text-gray-600">
                        <i class="fas fa-times text-xl"></i>
                    </button>
                </div>
                
                <div class="mb-6">
                    <p class="text-gray-600">Usuario: <span id="nombreUsuarioRechazo" class="font-medium text-gray-800"></span></p>
                </div>
                
                <form method="post" action="${pageContext.request.contextPath}/validacion-usuarios">
                    <input type="hidden" name="action" value="rechazar">
                    <input type="hidden" name="idUsuario" id="idUsuarioRechazo">
                    
                    <div class="mb-6">
                        <label class="block text-sm font-medium text-gray-700 mb-2">Motivo del Rechazo</label>
                        <textarea name="motivoRechazo" rows="4" required
                                  class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-red-500 focus:outline-none"
                                  placeholder="Explica el motivo del rechazo (ej: DNI no legible, información incorrecta, etc.)"></textarea>
                    </div>
                    
                    <div class="flex justify-end space-x-4">
                        <button type="button" onclick="cerrarModalRechazo()" 
                                class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </button>
                        <button type="submit" 
                                class="bg-red-500 text-white px-6 py-3 rounded-xl hover:bg-red-600 transition-colors duration-300">
                            <i class="fas fa-times mr-2"></i>
                            Rechazar Usuario
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="layout/footer.jsp" %>

<script>
// Funciones para Modal de Imagen
function abrirModalImagen(src, titulo) {
    document.getElementById('imagenModal').src = src;
    document.getElementById('tituloImagen').textContent = titulo;
    document.getElementById('modalImagen').classList.remove('hidden');
}

function cerrarModalImagen() {
    document.getElementById('modalImagen').classList.add('hidden');
}

// Funciones para Modal de Rechazo
function abrirModalRechazo(idUsuario, nombreUsuario) {
    document.getElementById('idUsuarioRechazo').value = idUsuario;
    document.getElementById('nombreUsuarioRechazo').textContent = nombreUsuario;
    document.getElementById('modalRechazo').classList.remove('hidden');
}

function cerrarModalRechazo() {
    document.getElementById('modalRechazo').classList.add('hidden');
}

// Cerrar modales con ESC
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        cerrarModalImagen();
        cerrarModalRechazo();
    }
});

// Cerrar modales al hacer clic fuera
document.getElementById('modalImagen').addEventListener('click', function(e) {
    if (e.target === this) {
        cerrarModalImagen();
    }
});

document.getElementById('modalRechazo').addEventListener('click', function(e) {
    if (e.target === this) {
        cerrarModalRechazo();
    }
});
</script>
