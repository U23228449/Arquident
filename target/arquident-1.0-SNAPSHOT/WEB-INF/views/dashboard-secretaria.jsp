<%@ include file="layout/header.jsp" %>

<!-- Header -->
<section class="gradient-bg text-white py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between">
            <div>
                <h1 class="text-3xl font-bold">
                    <i class="fas fa-user-tie mr-3"></i>
                    Dashboard Secretaria
                </h1>
                <p class="text-blue-100 mt-2">
                    Bienvenida, ${usuario.nombre} - Panel de Control
                </p>
            </div>
            <div class="text-right">
                <div class="text-2xl font-bold">
                    <c:choose>
                        <c:when test="${vista == 'citas'}">${citasDelDia.size()}</c:when>
                        <c:when test="${vista == 'pagos'}">${pagosPendientes.size()}</c:when>
                        <c:otherwise>0</c:otherwise>
                    </c:choose>
                </div>
                <div class="text-blue-100">
                    <c:choose>
                        <c:when test="${vista == 'citas'}">Citas del día</c:when>
                        <c:when test="${vista == 'pagos'}">Pagos pendientes</c:when>
                        <c:otherwise>Elementos</c:otherwise>
                    </c:choose>
                </div>
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

<!-- Navegación de pestañas -->
<section class="bg-white border-b">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <nav class="flex space-x-8">
            <a href="${pageContext.request.contextPath}/dashboard-secretaria?vista=citas" 
               class="py-4 px-1 border-b-2 font-medium text-sm transition-colors duration-300
                      <c:choose>
                          <c:when test='${vista == "citas"}'>border-blue-500 text-blue-600</c:when>
                          <c:otherwise>border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300</c:otherwise>
                      </c:choose>">
                <i class="fas fa-calendar-day mr-2"></i>
                Citas del Día
            </a>
            <a href="${pageContext.request.contextPath}/dashboard-secretaria?vista=reportes" 
               class="py-4 px-1 border-b-2 font-medium text-sm transition-colors duration-300
                      <c:choose>
                          <c:when test='${vista == "reportes"}'>border-blue-500 text-blue-600</c:when>
                          <c:otherwise>border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300</c:otherwise>
                      </c:choose>">
                <i class="fas fa-chart-bar mr-2"></i>
                Reportes
            </a>
            <a href="${pageContext.request.contextPath}/dashboard-secretaria?vista=pagos" 
               class="py-4 px-1 border-b-2 font-medium text-sm transition-colors duration-300
                      <c:choose>
                          <c:when test='${vista == "pagos"}'>border-blue-500 text-blue-600</c:when>
                          <c:otherwise>border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300</c:otherwise>
                      </c:choose>">
                <i class="fas fa-credit-card mr-2"></i>
                Pagos Pendientes
            </a>
            <a href="${pageContext.request.contextPath}/validacion-usuarios" 
               class="py-4 px-1 border-b-2 font-medium text-sm transition-colors duration-300 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300 relative">
                <i class="fas fa-user-check mr-2"></i>
                Validar Usuarios
                <c:if test="${not empty usuariosPendientes && usuariosPendientes.size() > 0}">
                    <span class="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">
                        ${usuariosPendientes.size()}
                    </span>
                </c:if>
            </a>
        </nav>
    </div>
</section>

<!-- Contenido principal -->
<section class="py-8 bg-gray-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <!-- Vista de Citas -->
        <c:if test='${vista == "citas"}'>
            <div class="bg-white rounded-2xl shadow-xl p-8">
                <!-- Header con filtros -->
                <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between mb-6">
                    <h2 class="text-2xl font-bold text-gray-800 mb-4 lg:mb-0">
                        <i class="fas fa-calendar-day text-blue-500 mr-2"></i>
                        Citas del Día
                    </h2>
                    
                    <div class="flex flex-col sm:flex-row space-y-4 sm:space-y-0 sm:space-x-4">
                        <!-- Selector de fecha -->
                        <div class="flex items-center space-x-2">
                            <label class="text-sm font-medium text-gray-700">Fecha:</label>
                            <input type="date" id="selectorFecha" 
                                   value="${fechaSeleccionada}"
                                   min="<%= java.time.LocalDate.now().minusMonths(1) %>"
                                   max="<%= java.time.LocalDate.now().plusMonths(3) %>"
                                   class="px-3 py-2 border border-gray-300 rounded-lg focus:border-blue-500 focus:outline-none"
                                   onchange="filtrarCitas()">
                        </div>
                        
                        <!-- Selector de doctor -->
                        <div class="flex items-center space-x-2">
                            <label class="text-sm font-medium text-gray-700">Doctor:</label>
                            <select id="selectorDoctor" 
                                    class="px-3 py-2 border border-gray-300 rounded-lg focus:border-blue-500 focus:outline-none"
                                    onchange="filtrarCitas()">
                                <option value="todos" <c:if test="${doctorSeleccionado == null}">selected</c:if>>Todos los doctores</option>
                                <c:forEach var="doctor" items="${doctores}">
                                    <option value="${doctor.idUsuario}" 
                                            <c:if test="${doctorSeleccionado != null && doctorSeleccionado == doctor.idUsuario}">selected</c:if>>
                                        Dr. ${doctor.nombre}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <!-- Botón de promoción -->
                        <button onclick="abrirModalPromocion()" 
                                class="bg-purple-500 text-white px-4 py-2 rounded-lg hover:bg-purple-600 transition-colors duration-300">
                            <i class="fas fa-bullhorn mr-2"></i>
                            Enviar Promoción
                        </button>
                    </div>
                </div>
                
                <!-- Estadísticas rápidas -->
                <c:if test="${not empty estadisticasDelDia}">
                    <div class="grid grid-cols-2 md:grid-cols-5 gap-4 mb-6">
                        <div class="bg-blue-50 p-4 rounded-xl">
                            <div class="text-2xl font-bold text-blue-600">${estadisticasDelDia['confirmada'] + estadisticasDelDia['sin_pagar'] + estadisticasDelDia['en_atencion'] + estadisticasDelDia['finalizada'] + estadisticasDelDia['derivada_sin_pagar']}</div>
                            <div class="text-blue-800 text-sm">Total Citas</div>
                        </div>
                        <div class="bg-green-50 p-4 rounded-xl">
                            <div class="text-2xl font-bold text-green-600">${estadisticasDelDia['finalizada']}</div>
                            <div class="text-green-800 text-sm">Completadas</div>
                        </div>
                        <div class="bg-yellow-50 p-4 rounded-xl">
                            <div class="text-2xl font-bold text-yellow-600">${estadisticasDelDia['sin_pagar']}</div>
                            <div class="text-yellow-800 text-sm">Sin Pagar</div>
                        </div>
                        <div class="bg-purple-50 p-4 rounded-xl">
                            <div class="text-2xl font-bold text-purple-600">${estadisticasDelDia['en_atencion']}</div>
                            <div class="text-purple-800 text-sm">En Atención</div>
                        </div>
                        <div class="bg-orange-50 p-4 rounded-xl">
                            <div class="text-2xl font-bold text-orange-600">${estadisticasDelDia['derivada_sin_pagar']}</div>
                            <div class="text-orange-800 text-sm">Derivadas S/P</div>
                        </div>
                    </div>
                </c:if>
                
                <!-- Lista de citas -->
                <c:choose>
                    <c:when test="${empty citasDelDia}">
                        <div class="text-center py-12">
                            <i class="fas fa-calendar-times text-gray-400 text-6xl mb-4"></i>
                            <h3 class="text-xl font-medium text-gray-500 mb-2">No hay citas para esta fecha</h3>
                            <p class="text-gray-400">Selecciona otra fecha o doctor</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="space-y-4">
                            <c:forEach var="cita" items="${citasDelDia}">
                                <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
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
                                            
                                            <div class="grid md:grid-cols-3 gap-4">
                                                <div>
                                                    <p class="text-sm text-gray-600 mb-1">Doctor</p>
                                                    <p class="font-medium text-gray-800">Dr. ${cita.nombreOdontologo}</p>
                                                </div>
                                                <div>
                                                    <p class="text-sm text-gray-600 mb-1">Servicio</p>
                                                    <p class="font-medium text-gray-800">${cita.nombreServicio}</p>
                                                </div>
                                                <div>
                                                    <p class="text-sm text-gray-600 mb-1">Hora</p>
                                                    <p class="font-medium text-gray-800">
                                                        <i class="fas fa-clock mr-1"></i>
                                                        ${cita.hora}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="ml-6 flex flex-col items-center space-y-2">
                                            <!-- Estado -->
                                            <span class="px-3 py-1 rounded-full text-xs font-medium
                                                <c:choose>
                                                    <c:when test='${cita.estado == "confirmada"}'>bg-green-100 text-green-800</c:when>
                                                    <c:when test='${cita.estado == "sin_pagar"}'>bg-yellow-100 text-yellow-800</c:when>
                                                    <c:when test='${cita.estado == "en_atencion"}'>bg-blue-100 text-blue-800</c:when>
                                                    <c:when test='${cita.estado == "finalizada"}'>bg-gray-100 text-gray-800</c:when>
                                                    <c:when test='${cita.estado == "derivada_sin_pagar"}'>bg-purple-100 text-purple-800</c:when>
                                                    <c:otherwise>bg-gray-100 text-gray-800</c:otherwise>
                                                </c:choose>
                                            ">
                                                <c:choose>
                                                    <c:when test='${cita.estado == "confirmada"}'>Confirmada</c:when>
                                                    <c:when test='${cita.estado == "sin_pagar"}'>Sin Pagar</c:when>
                                                    <c:when test='${cita.estado == "en_atencion"}'>En Atención</c:when>
                                                    <c:when test='${cita.estado == "finalizada"}'>Finalizada</c:when>
                                                    <c:when test='${cita.estado == "derivada_sin_pagar"}'>Derivada Sin Pagar</c:when>
                                                    <c:otherwise>${cita.estado}</c:otherwise>
                                                </c:choose>
                                            </span>
                                            
                                            <!-- Acción de confirmar -->
                                            <c:if test='${cita.estado == "sin_pagar"}'>
                                                <form method="post" action="${pageContext.request.contextPath}/dashboard-secretaria" class="inline">
                                                    <input type="hidden" name="action" value="confirmarCita">
                                                    <input type="hidden" name="idCita" value="${cita.idCita}">
                                                    <button type="submit" 
                                                            class="bg-green-500 text-white px-3 py-1 rounded-lg hover:bg-green-600 transition-colors duration-300 text-sm">
                                                        <i class="fas fa-check mr-1"></i>
                                                        Confirmar
                                                    </button>
                                                </form>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
        
        <!-- Vista de Reportes -->
        <c:if test='${vista == "reportes"}'>
            <div class="space-y-8">
                <!-- Filtros de fecha -->
                <div class="bg-white rounded-2xl shadow-xl p-8">
                    <h2 class="text-2xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-chart-bar text-blue-500 mr-2"></i>
                        Reportes de la Empresa
                    </h2>
                    
                    <form method="get" action="${pageContext.request.contextPath}/dashboard-secretaria" class="flex flex-wrap items-end gap-4 mb-6">
                        <input type="hidden" name="vista" value="reportes">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Fecha Inicio</label>
                            <input type="date" name="fechaInicio" value="${fechaInicio}" 
                                   class="px-3 py-2 border border-gray-300 rounded-lg focus:border-blue-500 focus:outline-none">
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Fecha Fin</label>
                            <input type="date" name="fechaFin" value="${fechaFin}" 
                                   class="px-3 py-2 border border-gray-300 rounded-lg focus:border-blue-500 focus:outline-none">
                        </div>
                        <button type="submit" 
                                class="bg-blue-500 text-white px-6 py-2 rounded-lg hover:bg-blue-600 transition-colors duration-300">
                            <i class="fas fa-search mr-2"></i>
                            Generar Reporte
                        </button>

                        <!-- Botones de Reporte (PDF y Excel) -->
                        <div class="flex space-x-2 ml-4">
                            <a href="${pageContext.request.contextPath}/generar-reporte?tipo=general&formato=pdf&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}" 
                               target="_blank"
                               class="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition-colors duration-300 text-sm">
                                <i class="fas fa-file-pdf mr-1"></i>
                                PDF General
                            </a>
                            <a href="${pageContext.request.contextPath}/generar-reporte?tipo=doctores&formato=pdf&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}" 
                               target="_blank"
                               class="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition-colors duration-300 text-sm">
                                <i class="fas fa-file-pdf mr-1"></i>
                                PDF Doctores
                            </a>
                            <a href="${pageContext.request.contextPath}/generar-reporte?tipo=doctores&formato=excel&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}" 
                               target="_blank"
                               class="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition-colors duration-300 text-sm">
                                <i class="fas fa-file-excel mr-1"></i>
                                Excel Doctores
                            </a>
                            <a href="${pageContext.request.contextPath}/generar-reporte?tipo=servicios&formato=pdf&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}" 
                               target="_blank"
                               class="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition-colors duration-300 text-sm">
                                <i class="fas fa-file-pdf mr-1"></i>
                                PDF Servicios
                            </a>
                            <a href="${pageContext.request.contextPath}/generar-reporte?tipo=servicios&formato=excel&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}" 
                               target="_blank"
                               class="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition-colors duration-300 text-sm">
                                <i class="fas fa-file-excel mr-1"></i>
                                Excel Servicios
                            </a>
                            <a href="${pageContext.request.contextPath}/generar-reporte?tipo=ingresos&formato=pdf&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}" 
                               target="_blank"
                               class="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition-colors duration-300 text-sm">
                                <i class="fas fa-file-pdf mr-1"></i>
                                PDF Ingresos
                            </a>
                            <a href="${pageContext.request.contextPath}/generar-reporte?tipo=ingresos&formato=excel&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}" 
                               target="_blank"
                               class="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition-colors duration-300 text-sm">
                                <i class="fas fa-file-excel mr-1"></i>
                                Excel Ingresos
                            </a>
                        </div>
                    </form>
                    
                    <!-- Reporte General -->
                    <c:if test="${not empty reporteGeneral}">
                        <div class="grid grid-cols-2 md:grid-cols-5 gap-4 mb-8">
                            <div class="bg-blue-50 p-4 rounded-xl text-center">
                                <div class="text-2xl font-bold text-blue-600">${reporteGeneral['total_citas']}</div>
                                <div class="text-blue-800 text-sm">Total Citas</div>
                            </div>
                            <div class="bg-green-50 p-4 rounded-xl text-center">
                                <div class="text-2xl font-bold text-green-600">${reporteGeneral['citas_completadas']}</div>
                                <div class="text-green-800 text-sm">Completadas</div>
                            </div>
                            <div class="bg-red-50 p-4 rounded-xl text-center">
                                <div class="text-2xl font-bold text-red-600">${reporteGeneral['citas_canceladas']}</div>
                                <div class="text-red-800 text-sm">Canceladas</div>
                            </div>
                            <div class="bg-yellow-50 p-4 rounded-xl text-center">
                                <div class="text-2xl font-bold text-yellow-600">${reporteGeneral['citas_pendientes_pago']}</div>
                                <div class="text-yellow-800 text-sm">Pendientes Pago</div>
                            </div>
                            <div class="bg-purple-50 p-4 rounded-xl text-center">
                                <div class="text-2xl font-bold text-purple-600">${reporteGeneral['citas_confirmadas']}</div>
                                <div class="text-purple-800 text-sm">Confirmadas</div>
                            </div>
                        </div>
                    </c:if>
                    
                    <!-- Reporte de Ingresos -->
                    <c:if test="${not empty reporteIngresos}">
                        <div class="bg-gray-50 p-6 rounded-xl mb-8">
                            <h3 class="text-lg font-bold text-gray-800 mb-4">Reporte de Ingresos</h3>
                            <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                                <div class="text-center">
                                    <div class="text-2xl font-bold text-green-600">S/ ${reporteIngresos['ingresos_confirmados']}</div>
                                    <div class="text-green-800 text-sm">Ingresos Confirmados</div>
                                </div>
                                <div class="text-center">
                                    <div class="text-2xl font-bold text-yellow-600">S/ ${reporteIngresos['ingresos_pendientes']}</div>
                                    <div class="text-yellow-800 text-sm">Ingresos Pendientes</div>
                                </div>
                                <div class="text-center">
                                    <div class="text-2xl font-bold text-blue-600">S/ ${reporteIngresos['ingresos_totales']}</div>
                                    <div class="text-blue-800 text-sm">Ingresos Totales</div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
                
                <!-- Reporte por Doctor -->
                <c:if test="${not empty reportePorDoctor}">
                    <div class="bg-white rounded-2xl shadow-xl p-8">
                        <h3 class="text-xl font-bold text-gray-800 mb-6">Reporte por Doctor</h3>
                        <div class="overflow-x-auto">
                            <table class="min-w-full divide-y divide-gray-200">
                                <thead class="bg-gray-50">
                                    <tr>
                                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Doctor</th>
                                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total Citas</th>
                                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Completadas</th>
                                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">% Efectividad</th>
                                    </tr>
                                </thead>
                                <tbody class="bg-white divide-y divide-gray-200">
                                    <c:forEach var="fila" items="${reportePorDoctor}">
                                        <c:set var="totalCitas" value="${fila['total_citas']}" />
                                        <c:set var="citasCompletadas" value="${fila['citas_completadas']}" />
                                        <c:set var="efectividad" value="${totalCitas > 0 ? (citasCompletadas * 100.0 / totalCitas) : 0}" />
                                        <tr>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">Dr. ${fila['doctor']}</td>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${totalCitas}</td>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${citasCompletadas}</td>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                                <fmt:formatNumber value="${efectividad}" maxFractionDigits="1" />%
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
                
                <!-- Reporte por Servicio -->
                <c:if test="${not empty reportePorServicio}">
                    <div class="bg-white rounded-2xl shadow-xl p-8">
                        <h3 class="text-xl font-bold text-gray-800 mb-6">Reporte por Servicio</h3>
                        <div class="overflow-x-auto">
                            <table class="min-w-full divide-y divide-gray-200">
                                <thead class="bg-gray-50">
                                    <tr>
                                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Servicio</th>
                                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total Citas</th>
                                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Completadas</th>
                                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Precio</th>
                                    </tr>
                                </thead>
                                <tbody class="bg-white divide-y divide-gray-200">
                                    <c:forEach var="fila" items="${reportePorServicio}">
                                        <tr>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${fila['servicio']}</td>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${fila['total_citas']}</td>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${fila['citas_completadas']}</td>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">S/ ${fila['precio_servicio']}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
        </c:if>
        
        <!-- Vista de Pagos -->
        <c:if test='${vista == "pagos"}'>
            <div class="space-y-8">
                <!-- Pagos Pendientes -->
                <div class="bg-white rounded-2xl shadow-xl p-8">
                    <h2 class="text-2xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-phone text-blue-500 mr-2"></i>
                        Citas Derivadas Pendientes de Pago
                    </h2>

                    <!-- Agregar descripción explicativa -->
                    <div class="mb-6 p-4 bg-blue-50 rounded-xl border border-blue-200">
                        <div class="flex items-start">
                            <i class="fas fa-info-circle text-blue-500 mt-1 mr-3"></i>
                            <div>
                                <h5 class="font-bold text-blue-800 mb-1">Seguimiento Telefónico</h5>
                                <p class="text-blue-700 text-sm">
                                    Estas son citas derivadas que requieren pago. Es recomendable llamar a los pacientes 
                                    para recordarles sobre su cita y el pago pendiente.
                                </p>
                            </div>
                        </div>
                    </div>
                    
                    <c:choose>
                        <c:when test="${empty pagosPendientes}">
                            <div class="text-center py-12">
                                <i class="fas fa-check-circle text-green-400 text-6xl mb-4"></i>
                                <h3 class="text-xl font-medium text-gray-500 mb-2">¡Excelente! No hay pagos pendientes</h3>
                                <p class="text-gray-400">Todos los pagos están al día</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="space-y-4">
                                <c:forEach var="pago" items="${pagosPendientes}">
                                    <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
                                        <div class="flex items-start justify-between">
                                            <div class="flex-1">
                                                <div class="flex items-center mb-3">
                                                    <div class="w-12 h-12 bg-yellow-100 rounded-full flex items-center justify-center mr-4">
                                                        <i class="fas fa-exclamation text-yellow-500 text-xl"></i>
                                                    </div>
                                                    <div>
                                                        <h3 class="text-lg font-bold text-gray-800">${pago['nombre_paciente']}</h3>
                                                        <p class="text-gray-600">
                                                            <i class="fas fa-phone mr-1"></i>
                                                            ${pago['telefono'] != null ? pago['telefono'] : 'Sin teléfono'}
                                                        </p>
                                                    </div>
                                                </div>
                                                
                                                <div class="grid md:grid-cols-3 gap-4">
                                                    <div>
                                                        <p class="text-sm text-gray-600 mb-1">Servicio</p>
                                                        <p class="font-medium text-gray-800">${pago['nombre_servicio']}</p>
                                                    </div>
                                                    <div>
                                                        <p class="text-sm text-gray-600 mb-1">Doctor</p>
                                                        <p class="font-medium text-gray-800">Dr. ${pago['nombre_doctor']}</p>
                                                    </div>
                                                    <div>
                                                        <p class="text-sm text-gray-600 mb-1">Fecha Cita</p>
                                                        <p class="font-medium text-gray-800">${pago['fecha_cita']} ${pago['hora_cita']}</p>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                            <div class="ml-6 text-right">
                                                <div class="text-2xl font-bold text-red-600 mb-2">S/ ${pago['monto']}</div>
                                                <div class="text-sm text-gray-500 mb-3">
                                                    Vence: ${pago['fecha_limite_pago']}
                                                </div>
                                                <button onclick="abrirModalPago(${pago['id_pago']}, '${pago['nombre_paciente']}', ${pago['monto']})" 
                                                        class="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition-colors duration-300 text-sm">
                                                    <i class="fas fa-check mr-1"></i>
                                                    Marcar Pagado
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <!-- Pagos próximos a vencer -->
                <c:if test="${not empty pagosProximosVencer}">
                    <div class="bg-white rounded-2xl shadow-xl p-8">
                        <h3 class="text-xl font-bold text-gray-800 mb-6">
                            <i class="fas fa-clock text-orange-500 mr-2"></i>
                            Pagos Próximos a Vencer (3 días)
                        </h3>
                        
                        <div class="space-y-4">
                            <c:forEach var="pago" items="${pagosProximosVencer}">
                                <div class="border border-orange-200 bg-orange-50 rounded-xl p-4">
                                    <div class="flex items-center justify-between">
                                        <div>
                                            <h4 class="font-bold text-gray-800">${pago['nombre_paciente']}</h4>
                                            <p class="text-gray-600">${pago['nombre_servicio']}</p>
                                            <p class="text-sm text-gray-500">${pago['telefono']}</p>
                                        </div>
                                        <div class="text-right">
                                            <div class="text-lg font-bold text-orange-600">S/ ${pago['monto']}</div>
                                            <div class="text-sm text-orange-800">Vence: ${pago['fecha_limite_pago']}</div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </div>
        </c:if>
    </div>
</section>

<!-- Modal Promoción -->
<div id="modalPromocion" class="fixed inset-0 bg-black bg-opacity-50 hidden z-50">
    <div class="flex items-center justify-center min-h-screen p-4">
        <div class="bg-white rounded-2xl shadow-xl max-w-2xl w-full">
            <div class="p-6">
                <div class="flex items-center justify-between mb-6">
                    <h3 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-bullhorn text-purple-500 mr-2"></i>
                        Enviar Promoción a Todos los Pacientes
                    </h3>
                    <button onclick="cerrarModalPromocion()" class="text-gray-400 hover:text-gray-600">
                        <i class="fas fa-times text-xl"></i>
                    </button>
                </div>
                
                <form method="post" action="${pageContext.request.contextPath}/dashboard-secretaria">
                    <input type="hidden" name="action" value="enviarPromocion">
                    
                    <div class="mb-6">
                        <label class="block text-sm font-medium text-gray-700 mb-2">Mensaje de Promoción</label>
                        <textarea name="mensajePromocion" rows="4" required
                                  class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none"
                                  placeholder="Ej: ¡Descuento del 20% en limpiezas dentales durante todo el mes! Agenda tu cita ahora."></textarea>
                    </div>
                    
                    <div class="bg-yellow-50 border border-yellow-200 rounded-xl p-4 mb-6">
                        <div class="flex items-start">
                            <i class="fas fa-info-circle text-yellow-500 mt-1 mr-3"></i>
                            <div>
                                <h5 class="font-bold text-yellow-800 mb-1">Información</h5>
                                <p class="text-yellow-700 text-sm">
                                    Esta promoción se enviará como notificación a todos los pacientes registrados en el sistema.
                                    Aparecerá en su dashboard cuando inicien sesión.
                                </p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="flex justify-end space-x-4">
                        <button type="button" onclick="cerrarModalPromocion()" 
                                class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </button>
                        <button type="submit" 
                                class="bg-purple-500 text-white px-6 py-3 rounded-xl hover:bg-purple-600 transition-colors duration-300">
                            <i class="fas fa-paper-plane mr-2"></i>
                            Enviar Promoción
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal Marcar Pago -->
<div id="modalPago" class="fixed inset-0 bg-black bg-opacity-50 hidden z-50">
    <div class="flex items-center justify-center min-h-screen p-4">
        <div class="bg-white rounded-2xl shadow-xl max-w-lg w-full">
            <div class="p-6">
                <div class="flex items-center justify-between mb-6">
                    <h3 class="text-xl font-bold text-gray-800">
                        <i class="fas fa-check-circle text-green-500 mr-2"></i>
                        Confirmar Pago
                    </h3>
                    <button onclick="cerrarModalPago()" class="text-gray-400 hover:text-gray-600">
                        <i class="fas fa-times text-xl"></i>
                    </button>
                </div>
                
                <div class="mb-6">
                    <p class="text-gray-600">Paciente: <span id="pacienteNombrePago" class="font-medium text-gray-800"></span></p>
                    <p class="text-gray-600">Monto: <span id="montoPago" class="font-bold text-green-600"></span></p>
                </div>
                
                <form method="post" action="${pageContext.request.contextPath}/dashboard-secretaria">
                    <input type="hidden" name="action" value="marcarPagado">
                    <input type="hidden" name="idPago" id="idPagoConfirmar">
                    
                    <div class="mb-6">
                        <label class="block text-sm font-medium text-gray-700 mb-2">Método de Pago</label>
                        <select name="metodoPago" required
                                class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                            <option value="">Seleccionar método...</option>
                            <option value="efectivo">Efectivo</option>
                            <option value="tarjeta">Tarjeta</option>
                            <option value="yape">Yape</option>
                            <option value="plin">Plin</option>
                            <option value="otros">Otros</option>
                        </select>
                    </div>
                    
                    <div class="flex justify-end space-x-4">
                        <button type="button" onclick="cerrarModalPago()" 
                                class="bg-gray-300 text-gray-700 px-6 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                            Cancelar
                        </button>
                        <button type="submit" 
                                class="bg-green-500 text-white px-6 py-3 rounded-xl hover:bg-green-600 transition-colors duration-300">
                            <i class="fas fa-check mr-2"></i>
                            Confirmar Pago
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="layout/footer.jsp" %>

<script>
// Funciones para filtrar citas
function filtrarCitas() {
    const fecha = document.getElementById('selectorFecha').value;
    const doctor = document.getElementById('selectorDoctor').value;
    
    const url = new URL(window.location);
    url.searchParams.set('vista', 'citas');
    url.searchParams.set('fecha', fecha);
    if (doctor !== 'todos') {
        url.searchParams.set('doctor', doctor);
    } else {
        url.searchParams.delete('doctor');
    }
    
    window.location.href = url.toString();
}

// Funciones para Modal Promoción
function abrirModalPromocion() {
    document.getElementById('modalPromocion').classList.remove('hidden');
}

function cerrarModalPromocion() {
    document.getElementById('modalPromocion').classList.add('hidden');
}

// Funciones para Modal Pago
function abrirModalPago(idPago, nombrePaciente, monto) {
    document.getElementById('idPagoConfirmar').value = idPago;
    document.getElementById('pacienteNombrePago').textContent = nombrePaciente;
    document.getElementById('montoPago').textContent = 'S/ ' + monto;
    document.getElementById('modalPago').classList.remove('hidden');
}

function cerrarModalPago() {
    document.getElementById('modalPago').classList.add('hidden');
}

// Cerrar modales con ESC
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        cerrarModalPromocion();
        cerrarModalPago();
    }
});

// Cerrar modales al hacer clic fuera
document.getElementById('modalPromocion').addEventListener('click', function(e) {
    if (e.target === this) {
        cerrarModalPromocion();
    }
});

document.getElementById('modalPago').addEventListener('click', function(e) {
    if (e.target === this) {
        cerrarModalPago();
    }
});
</script>
