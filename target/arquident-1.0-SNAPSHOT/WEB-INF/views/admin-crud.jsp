<%@ include file="layout/header.jsp" %>

<!-- Header -->
<section class="gradient-bg text-white py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between">
            <div>
                <h1 class="text-3xl font-bold">
                    <i class="fas fa-database mr-3"></i>
                    Gestión de 
                    <c:choose>
                        <c:when test='${entity == "usuarios"}'>Usuarios</c:when>
                        <c:when test='${entity == "servicios"}'>Servicios</c:when>
                        <c:when test='${entity == "citas"}'>Citas</c:when>
                        <c:when test='${entity == "faqs"}'>FAQs</c:when>
                        <c:when test='${entity == "roles"}'>Roles</c:when>
                        <c:when test='${entity == "pagos"}'>Pagos</c:when>
                        <c:when test='${entity == "notificaciones"}'>Notificaciones</c:when>
                        <c:when test='${entity == "horarios"}'>Horarios</c:when>
                        <c:when test='${entity == "asignaciones"}'>Asignaciones</c:when>
                        <c:otherwise>Entidades</c:otherwise>
                    </c:choose>
                </h1>
                <p class="text-blue-100 mt-2">
                    Administrar registros del sistema
                </p>
            </div>
            <div>
                <c:if test='${entity != "asignaciones"}'>
                    <a href="${pageContext.request.contextPath}/dashboard-admin?action=new&entity=${entity}" 
                       class="bg-white text-blue-600 px-6 py-3 rounded-xl font-bold hover:bg-blue-50 transition-colors duration-300">
                        <i class="fas fa-plus mr-2"></i>
                        Crear Nuevo
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</section>

<!-- Navegación -->
<section class="bg-white border-b">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <nav class="flex space-x-8">
            <a href="${pageContext.request.contextPath}/dashboard-admin" 
               class="py-4 px-1 border-b-2 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300 font-medium text-sm transition-colors duration-300">
                <i class="fas fa-arrow-left mr-2"></i>
                Volver al Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=${entity}" 
               class="py-4 px-1 border-b-2 border-blue-500 text-blue-600 font-medium text-sm">
                <i class="fas fa-list mr-2"></i>
                Listar ${entity}
            </a>
        </nav>
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

<!-- Contenido principal -->
<section class="py-8 bg-gray-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <!-- Tabla de Usuarios -->
        <c:if test='${entity == "usuarios"}'>
            <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                <div class="p-6 border-b border-gray-200">
                    <h2 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-users text-blue-500 mr-2"></i>
                        Lista de Usuarios
                    </h2>
                </div>
                
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nombre</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Correo</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Rol</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">DNI</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Estado</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <c:forEach var="usuario" items="${usuarios}">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${usuario.idUsuario}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${usuario.nombre}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${usuario.correo}</td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <span class="px-2 py-1 text-xs font-medium rounded-full
                                            <c:choose>
                                                <c:when test='${usuario.nombreRol == "administrador"}'>bg-red-100 text-red-800</c:when>
                                                <c:when test='${usuario.nombreRol == "secretaria"}'>bg-purple-100 text-purple-800</c:when>
                                                <c:when test='${usuario.nombreRol == "odontologo"}'>bg-green-100 text-green-800</c:when>
                                                <c:otherwise>bg-blue-100 text-blue-800</c:otherwise>
                                            </c:choose>
                                        ">
                                            ${usuario.nombreRol}
                                        </span>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${usuario.dni}</td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <c:choose>
                                            <c:when test="${usuario.cuentaValidada}">
                                                <span class="px-2 py-1 text-xs font-medium bg-green-100 text-green-800 rounded-full">Validada</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="px-2 py-1 text-xs font-medium bg-yellow-100 text-yellow-800 rounded-full">Pendiente</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                        <div class="flex space-x-2">
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=edit&entity=usuarios&id=${usuario.idUsuario}" 
                                               class="text-blue-600 hover:text-blue-900">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=delete&entity=usuarios&id=${usuario.idUsuario}" 
                                               class="text-red-600 hover:text-red-900"
                                               onclick="return confirm('¿Estás seguro de eliminar este usuario?')">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        
        <!-- Tabla de Servicios -->
        <c:if test='${entity == "servicios"}'>
            <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                <div class="p-6 border-b border-gray-200">
                    <h2 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-tooth text-green-500 mr-2"></i>
                        Lista de Servicios
                    </h2>
                </div>
                
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nombre</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Descripción</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Precio</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Requiere Consulta</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <c:forEach var="servicio" items="${servicios}">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${servicio.idServicio}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${servicio.nombre}</td>
                                    <td class="px-6 py-4 text-sm text-gray-900 max-w-xs truncate">${servicio.descripcion}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">S/ ${servicio.precio}</td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <c:choose>
                                            <c:when test="${servicio.requiereConsulta}">
                                                <span class="px-2 py-1 text-xs font-medium bg-yellow-100 text-yellow-800 rounded-full">Sí</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="px-2 py-1 text-xs font-medium bg-green-100 text-green-800 rounded-full">No</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                        <div class="flex space-x-2">
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=edit&entity=servicios&id=${servicio.idServicio}" 
                                               class="text-blue-600 hover:text-blue-900">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=delete&entity=servicios&id=${servicio.idServicio}" 
                                               class="text-red-600 hover:text-red-900"
                                               onclick="return confirm('¿Estás seguro de eliminar este servicio?')">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        
        <!-- Tabla de Citas -->
        <c:if test='${entity == "citas"}'>
            <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                <div class="p-6 border-b border-gray-200">
                    <h2 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-calendar-alt text-purple-500 mr-2"></i>
                        Lista de Citas
                    </h2>
                </div>
                
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Paciente</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Odontólogo</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Servicio</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Fecha</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Hora</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Estado</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <c:forEach var="cita" items="${citas}">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${cita.idCita}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${cita.nombrePaciente}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">Dr. ${cita.nombreOdontologo}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${cita.nombreServicio}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${cita.fecha}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${cita.hora}</td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <span class="px-2 py-1 text-xs font-medium rounded-full
                                            <c:choose>
                                                <c:when test='${cita.estado == "confirmada"}'>bg-green-100 text-green-800</c:when>
                                                <c:when test='${cita.estado == "sin_pagar"}'>bg-yellow-100 text-yellow-800</c:when>
                                                <c:when test='${cita.estado == "en_atencion"}'>bg-blue-100 text-blue-800</c:when>
                                                <c:when test='${cita.estado == "finalizada"}'>bg-gray-100 text-gray-800</c:when>
                                                <c:when test='${cita.estado == "cancelada"}'>bg-red-100 text-red-800</c:when>
                                                <c:otherwise>bg-purple-100 text-purple-800</c:otherwise>
                                            </c:choose>
                                        ">
                                            ${cita.estado}
                                        </span>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                        <div class="flex space-x-2">
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=edit&entity=citas&id=${cita.idCita}" 
                                               class="text-blue-600 hover:text-blue-900">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=delete&entity=citas&id=${cita.idCita}" 
                                               class="text-red-600 hover:text-red-900"
                                               onclick="return confirm('¿Estás seguro de eliminar esta cita?')">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        
        <!-- Tabla de FAQs -->
        <c:if test='${entity == "faqs"}'>
            <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                <div class="p-6 border-b border-gray-200">
                    <h2 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-question-circle text-yellow-500 mr-2"></i>
                        Lista de FAQs
                    </h2>
                </div>
                
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Pregunta</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Respuesta</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Creado por</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Fecha</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <c:forEach var="faq" items="${faqs}">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${faq.idPregunta}</td>
                                    <td class="px-6 py-4 text-sm text-gray-900 max-w-xs truncate">${faq.pregunta}</td>
                                    <td class="px-6 py-4 text-sm text-gray-900 max-w-xs truncate">${faq.respuesta}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${faq.nombreUsuarioCreador}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                        <fmt:formatDate value="${faq.fechaCreacion}" pattern="dd/MM/yyyy" />
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                        <div class="flex space-x-2">
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=edit&entity=faqs&id=${faq.idPregunta}" 
                                               class="text-blue-600 hover:text-blue-900">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=delete&entity=faqs&id=${faq.idPregunta}" 
                                               class="text-red-600 hover:text-red-900"
                                               onclick="return confirm('¿Estás seguro de eliminar esta FAQ?')">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        
        <!-- Tabla de Roles -->
        <c:if test='${entity == "roles"}'>
            <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                <div class="p-6 border-b border-gray-200">
                    <h2 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-user-tag text-red-500 mr-2"></i>
                        Lista de Roles
                    </h2>
                </div>
                
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nombre</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Descripción</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <c:forEach var="rol" items="${rolesList}">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${rol.idRol}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${rol.nombreRol}</td>
                                    <td class="px-6 py-4 text-sm text-gray-900">${rol.descripcion}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                        <div class="flex space-x-2">
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=edit&entity=roles&id=${rol.idRol}" 
                                               class="text-blue-600 hover:text-blue-900">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <c:if test="${rol.idRol > 4}">
                                                <a href="${pageContext.request.contextPath}/dashboard-admin?action=delete&entity=roles&id=${rol.idRol}" 
                                                   class="text-red-600 hover:text-red-900"
                                                   onclick="return confirm('¿Estás seguro de eliminar este rol?')">
                                                    <i class="fas fa-trash"></i>
                                                </a>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        
        <!-- Tabla de Pagos -->
        <c:if test='${entity == "pagos"}'>
            <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                <div class="p-6 border-b border-gray-200">
                    <h2 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-credit-card text-indigo-500 mr-2"></i>
                        Lista de Pagos
                    </h2>
                </div>
                
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Cita</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Monto</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Método</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Estado</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <c:forEach var="pago" items="${pagos}">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${pago.idPago}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">#${pago.idCita}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">S/ ${pago.monto}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                        <c:choose>
                                            <c:when test="${pago.metodoPago != null}">${pago.metodoPago}</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <span class="px-2 py-1 text-xs font-medium rounded-full
                                            <c:choose>
                                                <c:when test='${pago.estadoPago == "pagado"}'>bg-green-100 text-green-800</c:when>
                                                <c:when test='${pago.estadoPago == "pendiente"}'>bg-yellow-100 text-yellow-800</c:when>
                                                <c:when test='${pago.estadoPago == "expirado"}'>bg-red-100 text-red-800</c:when>
                                                <c:otherwise>bg-gray-100 text-gray-800</c:otherwise>
                                            </c:choose>
                                        ">
                                            ${pago.estadoPago}
                                        </span>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                        <div class="flex space-x-2">
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=edit&entity=pagos&id=${pago.idPago}" 
                                               class="text-blue-600 hover:text-blue-900">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=delete&entity=pagos&id=${pago.idPago}" 
                                               class="text-red-600 hover:text-red-900"
                                               onclick="return confirm('¿Estás seguro de eliminar este pago?')">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        
        <!-- Tabla de Notificaciones -->
        <c:if test='${entity == "notificaciones"}'>
            <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                <div class="p-6 border-b border-gray-200">
                    <h2 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-bell text-pink-500 mr-2"></i>
                        Lista de Notificaciones
                    </h2>
                </div>
                
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Usuario</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Mensaje</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Estado</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Fecha</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <c:forEach var="notificacion" items="${notificaciones}">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${notificacion.idNotificacion}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${notificacion.nombreUsuario}</td>
                                    <td class="px-6 py-4 text-sm text-gray-900 max-w-xs truncate">${notificacion.mensaje}</td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <c:choose>
                                            <c:when test="${notificacion.leido}">
                                                <span class="px-2 py-1 text-xs font-medium bg-gray-100 text-gray-800 rounded-full">Leída</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="px-2 py-1 text-xs font-medium bg-blue-100 text-blue-800 rounded-full">No leída</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                        <fmt:formatDate value="${notificacion.fecha}" pattern="dd/MM/yyyy HH:mm" />
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                        <div class="flex space-x-2">
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=edit&entity=notificaciones&id=${notificacion.idNotificacion}" 
                                               class="text-blue-600 hover:text-blue-900">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=delete&entity=notificaciones&id=${notificacion.idNotificacion}" 
                                               class="text-red-600 hover:text-red-900"
                                               onclick="return confirm('¿Estás seguro de eliminar esta notificación?')">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        
        <!-- Tabla de Horarios -->
        <c:if test='${entity == "horarios"}'>
            <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                <div class="p-6 border-b border-gray-200">
                    <h2 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-clock text-teal-500 mr-2"></i>
                        Lista de Horarios de Odontólogos
                    </h2>
                </div>
                
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Odontólogo</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Día</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Hora Inicio</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Hora Fin</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <c:forEach var="horario" items="${horarios}">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${horario.idHorario}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">Dr. ${horario.nombreOdontologo}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${horario.diaSemana}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${horario.horaInicio}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${horario.horaFin}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                        <div class="flex space-x-2">
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=edit&entity=horarios&id=${horario.idHorario}" 
                                               class="text-blue-600 hover:text-blue-900">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=delete&entity=horarios&id=${horario.idHorario}" 
                                               class="text-red-600 hover:text-red-900"
                                               onclick="return confirm('¿Estás seguro de eliminar este horario?')">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        
        <!-- Tabla de Asignaciones -->
        <c:if test='${entity == "asignaciones"}'>
            <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                <div class="p-6 border-b border-gray-200">
                    <h2 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-link text-orange-500 mr-2"></i>
                        Asignaciones Odontólogo-Servicios
                    </h2>
                </div>
                
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                            <tr>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Odontólogo</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Servicio</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Precio</th>
                                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <c:forEach var="asignacion" items="${asignaciones}">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">Dr. ${asignacion.nombreOdontologo}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${asignacion.nombreServicio}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">S/ ${asignacion.precio}</td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                        <div class="flex space-x-2">
                                            <a href="${pageContext.request.contextPath}/dashboard-admin?action=delete&entity=asignaciones&idOdontologo=${asignacion.idOdontologo}&idServicio=${asignacion.idServicio}" 
                                               class="text-red-600 hover:text-red-900"
                                               onclick="return confirm('¿Estás seguro de eliminar esta asignación?')">
                                                <i class="fas fa-unlink"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <!-- Formulario para nueva asignación -->
                <div class="p-6 border-t border-gray-200 bg-gray-50">
                    <h3 class="text-lg font-bold text-gray-800 mb-4">Nueva Asignación</h3>
                    <form method="post" action="${pageContext.request.contextPath}/dashboard-admin" class="flex space-x-4">
                        <input type="hidden" name="action" value="create">
                        <input type="hidden" name="entity" value="asignaciones">
                        
                        <div class="flex-1">
                            <select name="idOdontologo" required class="w-full px-3 py-2 border border-gray-300 rounded-lg">
                                <option value="">Seleccionar odontólogo...</option>
                                <c:forEach var="odontologo" items="${odontologos}">
                                    <option value="${odontologo.idUsuario}">Dr. ${odontologo.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="flex-1">
                            <select name="idServicio" required class="w-full px-3 py-2 border border-gray-300 rounded-lg">
                                <option value="">Seleccionar servicio...</option>
                                <c:forEach var="servicio" items="${servicios}">
                                    <option value="${servicio.idServicio}">${servicio.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <button type="submit" class="bg-orange-500 text-white px-6 py-2 rounded-lg hover:bg-orange-600">
                            <i class="fas fa-plus mr-2"></i>Asignar
                        </button>
                    </form>
                </div>
            </div>
        </c:if>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>
