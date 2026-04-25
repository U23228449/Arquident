<%@ include file="layout/header.jsp" %>

<!-- Header -->
<section class="gradient-bg text-white py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between">
            <div>
                <h1 class="text-3xl font-bold">
                    <i class="fas fa-edit mr-3"></i>
                    <c:choose>
                        <c:when test='${action == "new"}'>Crear</c:when>
                        <c:otherwise>Editar</c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test='${entity == "usuarios"}'>Usuario</c:when>
                        <c:when test='${entity == "servicios"}'>Servicio</c:when>
                        <c:when test='${entity == "citas"}'>Cita</c:when>
                        <c:when test='${entity == "faqs"}'>FAQ</c:when>
                        <c:when test='${entity == "roles"}'>Rol</c:when>
                        <c:when test='${entity == "pagos"}'>Pago</c:when>
                        <c:when test='${entity == "notificaciones"}'>Notificación</c:when>
                        <c:when test='${entity == "horarios"}'>Horario</c:when>
                        <c:otherwise>Entidad</c:otherwise>
                    </c:choose>
                </h1>
                <p class="text-blue-100 mt-2">
                    <c:choose>
                        <c:when test='${action == "new"}'>Crear nuevo registro</c:when>
                        <c:otherwise>Modificar registro existente</c:otherwise>
                    </c:choose>
                </p>
            </div>
        </div>
    </div>
</section>

<!-- Navegación -->
<section class="bg-white border-b">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <nav class="flex space-x-8">
            <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=${entity}" 
               class="py-4 px-1 border-b-2 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300 font-medium text-sm transition-colors duration-300">
                <i class="fas fa-arrow-left mr-2"></i>
                Volver a la lista
            </a>
            <a href="${pageContext.request.contextPath}/dashboard-admin?action=${action}&entity=${entity}" 
               class="py-4 px-1 border-b-2 border-blue-500 text-blue-600 font-medium text-sm">
                <i class="fas fa-edit mr-2"></i>
                <c:choose>
                    <c:when test='${action == "new"}'>Crear</c:when>
                    <c:otherwise>Editar</c:otherwise>
                </c:choose>
            </a>
        </nav>
    </div>
</section>

<!-- Contenido principal -->
<section class="py-8 bg-gray-50">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <!-- Formulario de Usuario -->
        <c:if test='${entity == "usuarios"}'>
            <div class="bg-white rounded-2xl shadow-xl p-8">
                <h2 class="text-2xl font-bold text-gray-800 mb-6">
                    <i class="fas fa-user text-blue-500 mr-2"></i>
                    <c:choose>
                        <c:when test='${action == "new"}'>Crear Usuario</c:when>
                        <c:otherwise>Editar Usuario</c:otherwise>
                    </c:choose>
                </h2>
                
                <form method="post" action="${pageContext.request.contextPath}/dashboard-admin" class="space-y-6">
                    <input type="hidden" name="action" value="${action == 'new' ? 'create' : 'update'}">
                    <input type="hidden" name="entity" value="usuarios">
                    <c:if test='${action != "new"}'>
                        <input type="hidden" name="id" value="${usuario.idUsuario}">
                    </c:if>
                    
                    <div class="grid md:grid-cols-2 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Nombre Completo *</label>
                            <input type="text" name="nombre" required
                                   value="${usuario != null ? usuario.nombre : ''}"
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Correo Electrónico *</label>
                            <input type="email" name="correo" required
                                   value="${usuario != null ? usuario.correo : ''}"
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                    </div>
                    
                    <div class="grid md:grid-cols-2 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">DNI</label>
                            <input type="text" name="dni" maxlength="8" pattern="[0-9]{8}"
                                   value="${usuario != null ? usuario.dni : ''}"
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Teléfono</label>
                            <input type="tel" name="telefono"
                                   value="${usuario != null ? usuario.telefono : ''}"
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                    </div>
                    
                    <div class="grid md:grid-cols-2 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Contraseña ${action == 'new' ? '*' : '(dejar vacío para no cambiar)'}</label>
                            <input type="password" name="contrasena" ${action == 'new' ? 'required' : ''}
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Rol *</label>
                            <select name="rolId" required
                                    class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                                <option value="">Seleccionar rol...</option>
                                <c:forEach var="rol" items="${roles}">
                                    <option value="${rol.idRol}" 
                                            ${usuario != null && usuario.rolId == rol.idRol ? 'selected' : ''}>
                                        ${rol.nombreRol}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Dirección</label>
                        <textarea name="direccion" rows="3"
                                  class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">${usuario != null ? usuario.direccion : ''}</textarea>
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Fecha de Nacimiento</label>
                        <input type="date" name="fechaNacimiento"
                               value="${usuario != null && usuario.fechaNacimiento != null ? usuario.fechaNacimiento : ''}"
                               class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                    </div>
                    
                    <div class="flex justify-end space-x-4">
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=usuarios" 
                           class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </a>
                        <button type="submit" 
                                class="bg-blue-500 text-white px-6 py-3 rounded-xl hover:bg-blue-600 transition-colors duration-300">
                            <i class="fas fa-save mr-2"></i>
                            ${action == 'new' ? 'Crear' : 'Actualizar'}
                        </button>
                    </div>
                </form>
            </div>
        </c:if>
        
        <!-- Formulario de Servicio -->
        <c:if test='${entity == "servicios"}'>
            <div class="bg-white rounded-2xl shadow-xl p-8">
                <h2 class="text-2xl font-bold text-gray-800 mb-6">
                    <i class="fas fa-tooth text-green-500 mr-2"></i>
                    <c:choose>
                        <c:when test='${action == "new"}'>Crear Servicio</c:when>
                        <c:otherwise>Editar Servicio</c:otherwise>
                    </c:choose>
                </h2>
                
                <form method="post" action="${pageContext.request.contextPath}/dashboard-admin" class="space-y-6">
                    <input type="hidden" name="action" value="${action == 'new' ? 'create' : 'update'}">
                    <input type="hidden" name="entity" value="servicios">
                    <c:if test='${action != "new"}'>
                        <input type="hidden" name="id" value="${servicio.idServicio}">
                    </c:if>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Nombre del Servicio *</label>
                        <input type="text" name="nombre" required
                               value="${servicio != null ? servicio.nombre : ''}"
                               class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Descripción</label>
                        <textarea name="descripcion" rows="4"
                                  class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">${servicio != null ? servicio.descripcion : ''}</textarea>
                    </div>
                    
                    <div class="grid md:grid-cols-2 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Precio (S/) *</label>
                            <input type="number" name="precio" step="0.01" min="0" required
                                   value="${servicio != null ? servicio.precio : ''}"
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                        
                        <div class="flex items-center">
                            <input type="checkbox" name="requiereConsulta" value="true" id="requiereConsulta"
                                   ${servicio != null && servicio.requiereConsulta ? 'checked' : ''}
                                   class="w-4 h-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500">
                            <label for="requiereConsulta" class="ml-2 text-sm text-gray-700">
                                Requiere consulta previa
                            </label>
                        </div>
                    </div>
                    
                    <div class="flex justify-end space-x-4">
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=servicios" 
                           class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </a>
                        <button type="submit" 
                                class="bg-green-500 text-white px-6 py-3 rounded-xl hover:bg-green-600 transition-colors duration-300">
                            <i class="fas fa-save mr-2"></i>
                            ${action == 'new' ? 'Crear' : 'Actualizar'}
                        </button>
                    </div>
                </form>
            </div>
        </c:if>
        
        <!-- Formulario de Cita -->
        <c:if test='${entity == "citas"}'>
            <div class="bg-white rounded-2xl shadow-xl p-8">
                <h2 class="text-2xl font-bold text-gray-800 mb-6">
                    <i class="fas fa-calendar-alt text-purple-500 mr-2"></i>
                    <c:choose>
                        <c:when test='${action == "new"}'>Crear Cita</c:when>
                        <c:otherwise>Editar Cita</c:otherwise>
                    </c:choose>
                </h2>
                
                <form method="post" action="${pageContext.request.contextPath}/dashboard-admin" class="space-y-6">
                    <input type="hidden" name="action" value="${action == 'new' ? 'create' : 'update'}">
                    <input type="hidden" name="entity" value="citas">
                    <c:if test='${action != "new"}'>
                        <input type="hidden" name="id" value="${cita.idCita}">
                    </c:if>
                    
                    <div class="grid md:grid-cols-2 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Paciente *</label>
                            <select name="idPaciente" required ${action != 'new' ? 'disabled' : ''}
                                    class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                                <option value="">Seleccionar paciente...</option>
                                <c:forEach var="paciente" items="${pacientes}">
                                    <option value="${paciente.idUsuario}" 
                                            ${cita != null && cita.idPaciente == paciente.idUsuario ? 'selected' : ''}>
                                        ${paciente.nombre} - ${paciente.correo}
                                    </option>
                                </c:forEach>
                            </select>
                            <c:if test='${action != "new"}'>
                                <input type="hidden" name="idPaciente" value="${cita.idPaciente}">
                            </c:if>
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Odontólogo *</label>
                            <select name="idOdontologo" required
                                    class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                                <option value="">Seleccionar odontólogo...</option>
                                <c:forEach var="odontologo" items="${odontologos}">
                                    <option value="${odontologo.idUsuario}" 
                                            ${cita != null && cita.idOdontologo == odontologo.idUsuario ? 'selected' : ''}>
                                        Dr. ${odontologo.nombre}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Servicio *</label>
                        <select name="idServicio" required
                                class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                            <option value="">Seleccionar servicio...</option>
                            <c:forEach var="servicio" items="${servicios}">
                                <option value="${servicio.idServicio}" 
                                        ${cita != null && cita.idServicio == servicio.idServicio ? 'selected' : ''}>
                                    ${servicio.nombre} - S/ ${servicio.precio}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="grid md:grid-cols-3 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Fecha *</label>
                            <input type="date" name="fecha" required
                                   value="${cita != null ? cita.fecha : ''}"
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Hora *</label>
                            <input type="time" name="hora" required
                                   value="${cita != null ? cita.hora : ''}"
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Estado *</label>
                            <select name="estado" required
                                    class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                                <option value="sin_pagar" ${cita != null && cita.estado == 'sin_pagar' ? 'selected' : ''}>Sin Pagar</option>
                                <option value="confirmada" ${cita != null && cita.estado == 'confirmada' ? 'selected' : ''}>Confirmada</option>
                                <option value="en_atencion" ${cita != null && cita.estado == 'en_atencion' ? 'selected' : ''}>En Atención</option>
                                <option value="finalizada" ${cita != null && cita.estado == 'finalizada' ? 'selected' : ''}>Finalizada</option>
                                <option value="cancelada" ${cita != null && cita.estado == 'cancelada' ? 'selected' : ''}>Cancelada</option>
                                <option value="derivada_sin_pagar" ${cita != null && cita.estado == 'derivada_sin_pagar' ? 'selected' : ''}>Derivada Sin Pagar</option>
                            </select>
                        </div>
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Observaciones</label>
                        <textarea name="observaciones" rows="3"
                                  class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">${cita != null ? cita.observaciones : ''}</textarea>
                    </div>
                    
                    <c:if test='${action != "new"}'>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Receta</label>
                            <textarea name="receta" rows="3"
                                      class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">${cita != null ? cita.receta : ''}</textarea>
                        </div>
                    </c:if>
                    
                    <div class="flex justify-end space-x-4">
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=citas" 
                           class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </a>
                        <button type="submit" 
                                class="bg-purple-500 text-white px-6 py-3 rounded-xl hover:bg-purple-600 transition-colors duration-300">
                            <i class="fas fa-save mr-2"></i>
                            ${action == 'new' ? 'Crear' : 'Actualizar'}
                        </button>
                    </div>
                </form>
            </div>
        </c:if>
        
        <!-- Formulario de FAQ -->
        <c:if test='${entity == "faqs"}'>
            <div class="bg-white rounded-2xl shadow-xl p-8">
                <h2 class="text-2xl font-bold text-gray-800 mb-6">
                    <i class="fas fa-question-circle text-yellow-500 mr-2"></i>
                    <c:choose>
                        <c:when test='${action == "new"}'>Crear FAQ</c:when>
                        <c:otherwise>Editar FAQ</c:otherwise>
                    </c:choose>
                </h2>
                
                <form method="post" action="${pageContext.request.contextPath}/dashboard-admin" class="space-y-6">
                    <input type="hidden" name="action" value="${action == 'new' ? 'create' : 'update'}">
                    <input type="hidden" name="entity" value="faqs">
                    <c:if test='${action != "new"}'>
                        <input type="hidden" name="id" value="${faq.idPregunta}">
                    </c:if>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Pregunta *</label>
                        <textarea name="pregunta" rows="2" required
                                  class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">${faq != null ? faq.pregunta : ''}</textarea>
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Respuesta *</label>
                        <textarea name="respuesta" rows="6" required
                                  class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">${faq != null ? faq.respuesta : ''}</textarea>
                    </div>
                    
                    <div class="flex justify-end space-x-4">
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=faqs" 
                           class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </a>
                        <button type="submit" 
                                class="bg-yellow-500 text-white px-6 py-3 rounded-xl hover:bg-yellow-600 transition-colors duration-300">
                            <i class="fas fa-save mr-2"></i>
                            ${action == 'new' ? 'Crear' : 'Actualizar'}
                        </button>
                    </div>
                </form>
            </div>
        </c:if>
        
        <!-- Formulario de Rol -->
        <c:if test='${entity == "roles"}'>
            <div class="bg-white rounded-2xl shadow-xl p-8">
                <h2 class="text-2xl font-bold text-gray-800 mb-6">
                    <i class="fas fa-user-tag text-red-500 mr-2"></i>
                    <c:choose>
                        <c:when test='${action == "new"}'>Crear Rol</c:when>
                        <c:otherwise>Editar Rol</c:otherwise>
                    </c:choose>
                </h2>
                
                <form method="post" action="${pageContext.request.contextPath}/dashboard-admin" class="space-y-6">
                    <input type="hidden" name="action" value="${action == 'new' ? 'create' : 'update'}">
                    <input type="hidden" name="entity" value="roles">
                    <c:if test='${action != "new"}'>
                        <input type="hidden" name="id" value="${rol.idRol}">
                    </c:if>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Nombre del Rol *</label>
                        <input type="text" name="nombreRol" required
                               value="${rol != null ? rol.nombreRol : ''}"
                               class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Descripción</label>
                        <textarea name="descripcion" rows="3"
                                  class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">${rol != null ? rol.descripcion : ''}</textarea>
                    </div>
                    
                    <div class="flex justify-end space-x-4">
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=roles" 
                           class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </a>
                        <button type="submit" 
                                class="bg-red-500 text-white px-6 py-3 rounded-xl hover:bg-red-600 transition-colors duration-300">
                            <i class="fas fa-save mr-2"></i>
                            ${action == 'new' ? 'Crear' : 'Actualizar'}
                        </button>
                    </div>
                </form>
            </div>
        </c:if>
        
        <!-- Formulario de Pago -->
        <c:if test='${entity == "pagos"}'>
            <div class="bg-white rounded-2xl shadow-xl p-8">
                <h2 class="text-2xl font-bold text-gray-800 mb-6">
                    <i class="fas fa-credit-card text-indigo-500 mr-2"></i>
                    <c:choose>
                        <c:when test='${action == "new"}'>Crear Pago</c:when>
                        <c:otherwise>Editar Pago</c:otherwise>
                    </c:choose>
                </h2>
                
                <form method="post" action="${pageContext.request.contextPath}/dashboard-admin" class="space-y-6">
                    <input type="hidden" name="action" value="${action == 'new' ? 'create' : 'update'}">
                    <input type="hidden" name="entity" value="pagos">
                    <c:if test='${action != "new"}'>
                        <input type="hidden" name="id" value="${pago.idPago}">
                    </c:if>
                    
                    <div class="grid md:grid-cols-2 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Cita *</label>
                            <select name="idCita" required ${action != 'new' ? 'disabled' : ''}
                                    class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                                <option value="">Seleccionar cita...</option>
                                <c:forEach var="cita" items="${citas}">
                                    <option value="${cita.idCita}" 
                                            ${pago != null && pago.idCita == cita.idCita ? 'selected' : ''}>
                                        #${cita.idCita} - ${cita.nombrePaciente} - ${cita.fecha}
                                    </option>
                                </c:forEach>
                            </select>
                            <c:if test='${action != "new"}'>
                                <input type="hidden" name="idCita" value="${pago.idCita}">
                            </c:if>
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Monto (S/) *</label>
                            <input type="number" name="monto" step="0.01" min="0" required
                                   value="${pago != null ? pago.monto : ''}"
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                    </div>
                    
                    <div class="grid md:grid-cols-2 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Método de Pago</label>
                            <select name="metodoPago"
                                    class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                                <option value="">Seleccionar método...</option>
                                <option value="tarjeta" ${pago != null && pago.metodoPago == 'tarjeta' ? 'selected' : ''}>Tarjeta</option>
                                <option value="efectivo" ${pago != null && pago.metodoPago == 'efectivo' ? 'selected' : ''}>Efectivo</option>
                                <option value="yape" ${pago != null && pago.metodoPago == 'yape' ? 'selected' : ''}>Yape</option>
                                <option value="plin" ${pago != null && pago.metodoPago == 'plin' ? 'selected' : ''}>Plin</option>
                                <option value="otros" ${pago != null && pago.metodoPago == 'otros' ? 'selected' : ''}>Otros</option>
                            </select>
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Estado *</label>
                            <select name="estadoPago" required
                                    class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                                <option value="pendiente" ${pago != null && pago.estadoPago == 'pendiente' ? 'selected' : ''}>Pendiente</option>
                                <option value="pagado" ${pago != null && pago.estadoPago == 'pagado' ? 'selected' : ''}>Pagado</option>
                                <option value="fallido" ${pago != null && pago.estadoPago == 'fallido' ? 'selected' : ''}>Fallido</option>
                                <option value="expirado" ${pago != null && pago.estadoPago == 'expirado' ? 'selected' : ''}>Expirado</option>
                            </select>
                        </div>
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Fecha Límite de Pago</label>
                        <input type="datetime-local" name="fechaLimitePago"
                               value="${pago != null && pago.fechaLimitePago != null ? pago.fechaLimitePago.toString().substring(0,16) : ''}"
                               class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                    </div>
                    
                    <div class="flex justify-end space-x-4">
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=pagos" 
                           class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </a>
                        <button type="submit" 
                                class="bg-indigo-500 text-white px-6 py-3 rounded-xl hover:bg-indigo-600 transition-colors duration-300">
                            <i class="fas fa-save mr-2"></i>
                            ${action == 'new' ? 'Crear' : 'Actualizar'}
                        </button>
                    </div>
                </form>
            </div>
        </c:if>
        
        <!-- Formulario de Notificación -->
        <c:if test='${entity == "notificaciones"}'>
            <div class="bg-white rounded-2xl shadow-xl p-8">
                <h2 class="text-2xl font-bold text-gray-800 mb-6">
                    <i class="fas fa-bell text-pink-500 mr-2"></i>
                    <c:choose>
                        <c:when test='${action == "new"}'>Crear Notificación</c:when>
                        <c:otherwise>Editar Notificación</c:otherwise>
                    </c:choose>
                </h2>
                
                <form method="post" action="${pageContext.request.contextPath}/dashboard-admin" class="space-y-6">
                    <input type="hidden" name="action" value="${action == 'new' ? 'create' : 'update'}">
                    <input type="hidden" name="entity" value="notificaciones">
                    <c:if test='${action != "new"}'>
                        <input type="hidden" name="id" value="${notificacion.idNotificacion}">
                    </c:if>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Usuario *</label>
                        <select name="idUsuario" required ${action != 'new' ? 'disabled' : ''}
                                class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                            <option value="">Seleccionar usuario...</option>
                            <option value="0">Todos los usuarios</option>
                            <c:forEach var="usuario" items="${usuarios}">
                                <option value="${usuario.idUsuario}" 
                                        ${notificacion != null && notificacion.idUsuario == usuario.idUsuario ? 'selected' : ''}>
                                    ${usuario.nombre} - ${usuario.correo}
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test='${action != "new"}'>
                            <input type="hidden" name="idUsuario" value="${notificacion.idUsuario}">
                        </c:if>
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Mensaje *</label>
                        <textarea name="mensaje" rows="4" required
                                  class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">${notificacion != null ? notificacion.mensaje : ''}</textarea>
                    </div>
                    
                    <c:if test='${action != "new"}'>
                        <div>
                            <div class="flex items-center">
                                <input type="checkbox" name="leido" value="true" id="leido"
                                       ${notificacion != null && notificacion.leido ? 'checked' : ''}
                                       class="w-4 h-4 text-pink-600 border-gray-300 rounded focus:ring-pink-500">
                                <label for="leido" class="ml-2 text-sm text-gray-700">
                                    Marcar como leída
                                </label>
                            </div>
                        </div>
                    </c:if>
                    
                    <div class="flex justify-end space-x-4">
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=notificaciones" 
                           class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </a>
                        <button type="submit" 
                                class="bg-pink-500 text-white px-6 py-3 rounded-xl hover:bg-pink-600 transition-colors duration-300">
                            <i class="fas fa-save mr-2"></i>
                            ${action == 'new' ? 'Crear' : 'Actualizar'}
                        </button>
                    </div>
                </form>
            </div>
        </c:if>
        
        <!-- Formulario de Horario -->
        <c:if test='${entity == "horarios"}'>
            <div class="bg-white rounded-2xl shadow-xl p-8">
                <h2 class="text-2xl font-bold text-gray-800 mb-6">
                    <i class="fas fa-clock text-teal-500 mr-2"></i>
                    <c:choose>
                        <c:when test='${action == "new"}'>Crear Horario</c:when>
                        <c:otherwise>Editar Horario</c:otherwise>
                    </c:choose>
                </h2>
                
                <form method="post" action="${pageContext.request.contextPath}/dashboard-admin" class="space-y-6">
                    <input type="hidden" name="action" value="${action == 'new' ? 'create' : 'update'}">
                    <input type="hidden" name="entity" value="horarios">
                    <c:if test='${action != "new"}'>
                        <input type="hidden" name="id" value="${horario.idHorario}">
                    </c:if>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Odontólogo *</label>
                        <select name="idOdontologo" required ${action != 'new' ? 'disabled' : ''}
                                class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                            <option value="">Seleccionar odontólogo...</option>
                            <c:forEach var="odontologo" items="${odontologos}">
                                <option value="${odontologo.idUsuario}" 
                                        ${horario != null && horario.idOdontologo == odontologo.idUsuario ? 'selected' : ''}>
                                    Dr. ${odontologo.nombre}
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test='${action != "new"}'>
                            <input type="hidden" name="idOdontologo" value="${horario.idOdontologo}">
                        </c:if>
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Día de la Semana *</label>
                        <select name="diaSemana" required
                                class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                            <option value="">Seleccionar día...</option>
                            <option value="lunes" ${horario != null && horario.diaSemana == 'lunes' ? 'selected' : ''}>Lunes</option>
                            <option value="martes" ${horario != null && horario.diaSemana == 'martes' ? 'selected' : ''}>Martes</option>
                            <option value="miércoles" ${horario != null && horario.diaSemana == 'miércoles' ? 'selected' : ''}>Miércoles</option>
                            <option value="jueves" ${horario != null && horario.diaSemana == 'jueves' ? 'selected' : ''}>Jueves</option>
                            <option value="viernes" ${horario != null && horario.diaSemana == 'viernes' ? 'selected' : ''}>Viernes</option>
                            <option value="sábado" ${horario != null && horario.diaSemana == 'sábado' ? 'selected' : ''}>Sábado</option>
                            <option value="domingo" ${horario != null && horario.diaSemana == 'domingo' ? 'selected' : ''}>Domingo</option>
                        </select>
                    </div>
                    
                    <div class="grid md:grid-cols-2 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Hora de Inicio *</label>
                            <input type="time" name="horaInicio" required
                                   value="${horario != null ? horario.horaInicio : ''}"
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Hora de Fin *</label>
                            <input type="time" name="horaFin" required
                                   value="${horario != null ? horario.horaFin : ''}"
                                   class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                        </div>
                    </div>
                    
                    <div class="flex justify-end space-x-4">
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=horarios" 
                           class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </a>
                        <button type="submit" 
                                class="bg-teal-500 text-white px-6 py-3 rounded-xl hover:bg-teal-600 transition-colors duration-300">
                            <i class="fas fa-save mr-2"></i>
                            ${action == 'new' ? 'Crear' : 'Actualizar'}
                        </button>
                    </div>
                </form>
            </div>
        </c:if>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>
