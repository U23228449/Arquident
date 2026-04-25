<%@ include file="layout/header.jsp" %>

<!-- Header -->
<section class="gradient-bg text-white py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between">
            <div>
                <h1 class="text-3xl font-bold">
                    <i class="fas fa-cogs mr-3"></i>
                    Panel de Administración
                </h1>
                <p class="text-blue-100 mt-2">
                    Bienvenido, ${usuario.nombre} - Control Total del Sistema
                </p>
            </div>
            <div class="text-right">
                <div class="text-2xl font-bold">${totalUsuarios + totalServicios + totalCitas + totalFaqs + totalPagos + totalNotificaciones}</div>
                <div class="text-blue-100">Total de registros</div>
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

<!-- Contenido principal -->
<section class="py-8 bg-gray-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <!-- Estadísticas generales -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            <div class="bg-white rounded-2xl shadow-xl p-6">
                <div class="flex items-center">
                    <div class="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center mr-4">
                        <i class="fas fa-users text-blue-500 text-xl"></i>
                    </div>
                    <div>
                        <div class="text-2xl font-bold text-gray-800">${totalUsuarios}</div>
                        <div class="text-gray-600">Usuarios</div>
                    </div>
                </div>
            </div>
            
            <div class="bg-white rounded-2xl shadow-xl p-6">
                <div class="flex items-center">
                    <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mr-4">
                        <i class="fas fa-tooth text-green-500 text-xl"></i>
                    </div>
                    <div>
                        <div class="text-2xl font-bold text-gray-800">${totalServicios}</div>
                        <div class="text-gray-600">Servicios</div>
                    </div>
                </div>
            </div>
            
            <div class="bg-white rounded-2xl shadow-xl p-6">
                <div class="flex items-center">
                    <div class="w-12 h-12 bg-purple-100 rounded-full flex items-center justify-center mr-4">
                        <i class="fas fa-calendar-alt text-purple-500 text-xl"></i>
                    </div>
                    <div>
                        <div class="text-2xl font-bold text-gray-800">${totalCitas}</div>
                        <div class="text-gray-600">Citas</div>
                    </div>
                </div>
            </div>
            
            <div class="bg-white rounded-2xl shadow-xl p-6">
                <div class="flex items-center">
                    <div class="w-12 h-12 bg-yellow-100 rounded-full flex items-center justify-center mr-4">
                        <i class="fas fa-question-circle text-yellow-500 text-xl"></i>
                    </div>
                    <div>
                        <div class="text-2xl font-bold text-gray-800">${totalFaqs}</div>
                        <div class="text-gray-600">FAQs</div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Estadísticas adicionales -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            <div class="bg-white rounded-2xl shadow-xl p-6">
                <div class="flex items-center">
                    <div class="w-12 h-12 bg-indigo-100 rounded-full flex items-center justify-center mr-4">
                        <i class="fas fa-credit-card text-indigo-500 text-xl"></i>
                    </div>
                    <div>
                        <div class="text-2xl font-bold text-gray-800">${totalPagos}</div>
                        <div class="text-gray-600">Pagos</div>
                    </div>
                </div>
            </div>
            
            <div class="bg-white rounded-2xl shadow-xl p-6">
                <div class="flex items-center">
                    <div class="w-12 h-12 bg-pink-100 rounded-full flex items-center justify-center mr-4">
                        <i class="fas fa-bell text-pink-500 text-xl"></i>
                    </div>
                    <div>
                        <div class="text-2xl font-bold text-gray-800">${totalNotificaciones}</div>
                        <div class="text-gray-600">Notificaciones</div>
                    </div>
                </div>
            </div>
            
            <div class="bg-white rounded-2xl shadow-xl p-6">
                <div class="flex items-center">
                    <div class="w-12 h-12 bg-teal-100 rounded-full flex items-center justify-center mr-4">
                        <i class="fas fa-clock text-teal-500 text-xl"></i>
                    </div>
                    <div>
                        <div class="text-2xl font-bold text-gray-800">${totalHorarios}</div>
                        <div class="text-gray-600">Horarios</div>
                    </div>
                </div>
            </div>
            
            <div class="bg-white rounded-2xl shadow-xl p-6">
                <div class="flex items-center">
                    <div class="w-12 h-12 bg-orange-100 rounded-full flex items-center justify-center mr-4">
                        <i class="fas fa-link text-orange-500 text-xl"></i>
                    </div>
                    <div>
                        <div class="text-2xl font-bold text-gray-800">${totalAsignaciones}</div>
                        <div class="text-gray-600">Asignaciones</div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Menú de gestión -->
        <div class="bg-white rounded-2xl shadow-xl p-8">
            <h2 class="text-2xl font-bold text-gray-800 mb-8 text-center">
                <i class="fas fa-database mr-2 text-blue-500"></i>
                Gestión de Entidades
            </h2>
            
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                <!-- Gestión de Usuarios -->
                <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
                    <div class="text-center">
                        <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
                            <i class="fas fa-users text-blue-500 text-2xl"></i>
                        </div>
                        <h3 class="text-lg font-bold text-gray-800 mb-2">Usuarios</h3>
                        <p class="text-gray-600 text-sm mb-4">Gestionar pacientes, doctores, secretarias y administradores</p>
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=usuarios" 
                           class="bg-blue-500 text-white px-6 py-2 rounded-lg hover:bg-blue-600 transition-colors duration-300 inline-block">
                            <i class="fas fa-cog mr-2"></i>Gestionar
                        </a>
                    </div>
                </div>
                
                <!-- Gestión de Servicios -->
                <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
                    <div class="text-center">
                        <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
                            <i class="fas fa-tooth text-green-500 text-2xl"></i>
                        </div>
                        <h3 class="text-lg font-bold text-gray-800 mb-2">Servicios</h3>
                        <p class="text-gray-600 text-sm mb-4">Administrar servicios odontológicos y precios</p>
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=servicios" 
                           class="bg-green-500 text-white px-6 py-2 rounded-lg hover:bg-green-600 transition-colors duration-300 inline-block">
                            <i class="fas fa-cog mr-2"></i>Gestionar
                        </a>
                    </div>
                </div>
                
                <!-- Gestión de Citas -->
                <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
                    <div class="text-center">
                        <div class="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-4">
                            <i class="fas fa-calendar-alt text-purple-500 text-2xl"></i>
                        </div>
                        <h3 class="text-lg font-bold text-gray-800 mb-2">Citas</h3>
                        <p class="text-gray-600 text-sm mb-4">Administrar todas las citas médicas</p>
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=citas" 
                           class="bg-purple-500 text-white px-6 py-2 rounded-lg hover:bg-purple-600 transition-colors duration-300 inline-block">
                            <i class="fas fa-cog mr-2"></i>Gestionar
                        </a>
                    </div>
                </div>
                
                <!-- Gestión de FAQs -->
                <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
                    <div class="text-center">
                        <div class="w-16 h-16 bg-yellow-100 rounded-full flex items-center justify-center mx-auto mb-4">
                            <i class="fas fa-question-circle text-yellow-500 text-2xl"></i>
                        </div>
                        <h3 class="text-lg font-bold text-gray-800 mb-2">FAQs</h3>
                        <p class="text-gray-600 text-sm mb-4">Gestionar preguntas frecuentes</p>
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=faqs" 
                           class="bg-yellow-500 text-white px-6 py-2 rounded-lg hover:bg-yellow-600 transition-colors duration-300 inline-block">
                            <i class="fas fa-cog mr-2"></i>Gestionar
                        </a>
                    </div>
                </div>
                
                <!-- Gestión de Roles -->
                <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
                    <div class="text-center">
                        <div class="w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-4">
                            <i class="fas fa-user-tag text-red-500 text-2xl"></i>
                        </div>
                        <h3 class="text-lg font-bold text-gray-800 mb-2">Roles</h3>
                        <p class="text-gray-600 text-sm mb-4">Administrar roles y permisos del sistema</p>
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=roles" 
                           class="bg-red-500 text-white px-6 py-2 rounded-lg hover:bg-red-600 transition-colors duration-300 inline-block">
                            <i class="fas fa-cog mr-2"></i>Gestionar
                        </a>
                    </div>
                </div>
                
                <!-- Gestión de Pagos -->
                <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
                    <div class="text-center">
                        <div class="w-16 h-16 bg-indigo-100 rounded-full flex items-center justify-center mx-auto mb-4">
                            <i class="fas fa-credit-card text-indigo-500 text-2xl"></i>
                        </div>
                        <h3 class="text-lg font-bold text-gray-800 mb-2">Pagos</h3>
                        <p class="text-gray-600 text-sm mb-4">Administrar pagos y transacciones</p>
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=pagos" 
                           class="bg-indigo-500 text-white px-6 py-2 rounded-lg hover:bg-indigo-600 transition-colors duration-300 inline-block">
                            <i class="fas fa-cog mr-2"></i>Gestionar
                        </a>
                    </div>
                </div>
                
                <!-- Gestión de Notificaciones -->
                <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
                    <div class="text-center">
                        <div class="w-16 h-16 bg-pink-100 rounded-full flex items-center justify-center mx-auto mb-4">
                            <i class="fas fa-bell text-pink-500 text-2xl"></i>
                        </div>
                        <h3 class="text-lg font-bold text-gray-800 mb-2">Notificaciones</h3>
                        <p class="text-gray-600 text-sm mb-4">Gestionar notificaciones del sistema</p>
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=notificaciones" 
                           class="bg-pink-500 text-white px-6 py-2 rounded-lg hover:bg-pink-600 transition-colors duration-300 inline-block">
                            <i class="fas fa-cog mr-2"></i>Gestionar
                        </a>
                    </div>
                </div>
                
                <!-- Gestión de Horarios -->
                <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
                    <div class="text-center">
                        <div class="w-16 h-16 bg-teal-100 rounded-full flex items-center justify-center mx-auto mb-4">
                            <i class="fas fa-clock text-teal-500 text-2xl"></i>
                        </div>
                        <h3 class="text-lg font-bold text-gray-800 mb-2">Horarios</h3>
                        <p class="text-gray-600 text-sm mb-4">Gestionar horarios de odontólogos</p>
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=horarios" 
                           class="bg-teal-500 text-white px-6 py-2 rounded-lg hover:bg-teal-600 transition-colors duration-300 inline-block">
                            <i class="fas fa-cog mr-2"></i>Gestionar
                        </a>
                    </div>
                </div>
                
                <!-- Gestión de Asignaciones -->
                <div class="border border-gray-200 rounded-xl p-6 hover:shadow-lg transition-shadow duration-300">
                    <div class="text-center">
                        <div class="w-16 h-16 bg-orange-100 rounded-full flex items-center justify-center mx-auto mb-4">
                            <i class="fas fa-link text-orange-500 text-2xl"></i>
                        </div>
                        <h3 class="text-lg font-bold text-gray-800 mb-2">Asignaciones</h3>
                        <p class="text-gray-600 text-sm mb-4">Asignar servicios a odontólogos</p>
                        <a href="${pageContext.request.contextPath}/dashboard-admin?action=list&entity=asignaciones" 
                           class="bg-orange-500 text-white px-6 py-2 rounded-lg hover:bg-orange-600 transition-colors duration-300 inline-block">
                            <i class="fas fa-cog mr-2"></i>Gestionar
                        </a>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Acciones rápidas -->
        <div class="mt-8 bg-white rounded-2xl shadow-xl p-8">
            <h3 class="text-xl font-bold text-gray-800 mb-6">
                <i class="fas fa-bolt mr-2 text-orange-500"></i>
                Acciones Rápidas
            </h3>
            
            <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
                <a href="${pageContext.request.contextPath}/dashboard-admin?action=new&entity=usuarios" 
                   class="bg-blue-50 border border-blue-200 rounded-xl p-4 hover:bg-blue-100 transition-colors duration-300 text-center">
                    <i class="fas fa-user-plus text-blue-500 text-xl mb-2"></i>
                    <div class="font-medium text-blue-700">Crear Usuario</div>
                </a>
                
                <a href="${pageContext.request.contextPath}/dashboard-admin?action=new&entity=servicios" 
                   class="bg-green-50 border border-green-200 rounded-xl p-4 hover:bg-green-100 transition-colors duration-300 text-center">
                    <i class="fas fa-plus text-green-500 text-xl mb-2"></i>
                    <div class="font-medium text-green-700">Crear Servicio</div>
                </a>
                
                <a href="${pageContext.request.contextPath}/dashboard-admin?action=new&entity=faqs" 
                   class="bg-yellow-50 border border-yellow-200 rounded-xl p-4 hover:bg-yellow-100 transition-colors duration-300 text-center">
                    <i class="fas fa-question text-yellow-500 text-xl mb-2"></i>
                    <div class="font-medium text-yellow-700">Crear FAQ</div>
                </a>
                
                <a href="${pageContext.request.contextPath}/dashboard-admin?action=new&entity=notificaciones" 
                   class="bg-pink-50 border border-pink-200 rounded-xl p-4 hover:bg-pink-100 transition-colors duration-300 text-center">
                    <i class="fas fa-bell text-pink-500 text-xl mb-2"></i>
                    <div class="font-medium text-pink-700">Crear Notificación</div>
                </a>
            </div>
        </div>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>
