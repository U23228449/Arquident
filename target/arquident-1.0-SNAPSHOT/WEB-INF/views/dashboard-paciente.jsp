<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="layout/header.jsp" %>

<!-- Dashboard Header -->
<section class="gradient-bg text-white py-12">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between">
            <div data-aos="fade-right">
                <h1 class="text-3xl md:text-4xl font-bold mb-2">
                    <i class="fas fa-tachometer-alt mr-3"></i>
                    Panel del Paciente
                </h1>
                <p class="text-blue-100 text-lg">
                    Bienvenido, ${sessionScope.usuario.nombre}
                </p>
            </div>
            <div class="hidden md:block" data-aos="fade-left">
                <div class="bg-white bg-opacity-20 rounded-2xl p-6 text-center">
                    <i class="fas fa-bell text-4xl mb-2 ${contadorNotificaciones > 0 ? 'animate-bounce' : ''}"></i>
                    <p class="text-sm">Notificaciones</p>
                    <p class="font-bold text-2xl">${contadorNotificaciones}</p>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Alertas Importantes -->
<c:if test="${not empty citasPendientesPago}">
    <section class="bg-red-50 border-l-4 border-red-500 py-4">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex items-center">
                <i class="fas fa-exclamation-triangle text-red-500 text-2xl mr-4"></i>
                <div>
                    <h3 class="text-red-800 font-bold">¡Atención! Tienes citas pendientes de pago</h3>
                    <p class="text-red-700">Completa el pago antes del tiempo límite para confirmar tus citas.</p>
                </div>
            </div>
        </div>
    </section>
</c:if>

<!-- Quick Actions -->
<section class="py-12 bg-gray-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-2xl font-bold text-gray-800 mb-8 text-center" data-aos="fade-up">Acciones Rápidas</h2>

        <div class="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
            <!-- Reservar Cita -->
            <div class="bg-white rounded-2xl shadow-lg p-6 text-center hover:shadow-xl transition-all duration-300 transform hover:-translate-y-2" data-aos="fade-up" data-aos-delay="100">
                <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-calendar-plus text-blue-500 text-2xl"></i>
                </div>
                <h3 class="text-lg font-bold text-gray-800 mb-2">Reservar Cita</h3>
                <p class="text-gray-600 text-sm mb-4">Agenda una nueva cita médica</p>
                <c:choose>
                    <c:when test="${tieneReservaActiva}">
                        <button disabled class="bg-gray-300 text-gray-500 px-6 py-2 rounded-full cursor-not-allowed">
                            Ya tienes una reserva
                        </button>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/reservar-cita" class="bg-blue-500 text-white px-6 py-2 rounded-full hover:bg-blue-600 transition-colors duration-300 inline-block">
                            Reservar
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Ver Citas -->
            <div class="bg-white rounded-2xl shadow-lg p-6 text-center hover:shadow-xl transition-all duration-300 transform hover:-translate-y-2" data-aos="fade-up" data-aos-delay="200">
                <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-calendar-alt text-green-500 text-2xl"></i>
                </div>
                <h3 class="text-lg font-bold text-gray-800 mb-2">Mis Citas</h3>
                <p class="text-gray-600 text-sm mb-4">Ver citas programadas</p>
                <button onclick="scrollToSection('citas-section')" class="bg-green-500 text-white px-6 py-2 rounded-full hover:bg-green-600 transition-colors duration-300">
                    Ver Citas
                </button>
            </div>

            <!-- Historial -->
            <div class="bg-white rounded-2xl shadow-lg p-6 text-center hover:shadow-xl transition-all duration-300 transform hover:-translate-y-2" data-aos="fade-up" data-aos-delay="300">
                <div class="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-history text-purple-500 text-2xl"></i>
                </div>
                <h3 class="text-lg font-bold text-gray-800 mb-2">Historial</h3>
                <p class="text-gray-600 text-sm mb-4">Revisar citas pasadas</p>
                <button onclick="scrollToSection('historial-section')" class="bg-purple-500 text-white px-6 py-2 rounded-full hover:bg-purple-600 transition-colors duration-300">
                    Ver Historial
                </button>
            </div>

            <!-- Perfil -->
            <div class="bg-white rounded-2xl shadow-lg p-6 text-center hover:shadow-xl transition-all duration-300 transform hover:-translate-y-2" data-aos="fade-up" data-aos-delay="400">
                <div class="w-16 h-16 bg-orange-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-user-edit text-orange-500 text-2xl"></i>
                </div>
                <h3 class="text-lg font-bold text-gray-800 mb-2">Mi Perfil</h3>
                <p class="text-gray-600 text-sm mb-4">Actualizar información</p>
                <button onclick="scrollToSection('perfil-section')" class="bg-orange-500 text-white px-6 py-2 rounded-full hover:bg-orange-600 transition-colors duration-300">
                    Editar
                </button>
            </div>
        </div>
    </div>
</section>

<!-- Main Dashboard Content -->
<section class="py-12 bg-white">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid lg:grid-cols-3 gap-8">
            <!-- Left Column -->
            <div class="lg:col-span-2 space-y-8">
                <!-- Citas Pendientes de Pago -->
                <c:if test="${not empty citasPendientesPago}">
                    <div class="bg-red-50 border border-red-200 rounded-2xl shadow-lg p-6" data-aos="fade-up">
                        <div class="flex items-center justify-between mb-6">
                            <h3 class="text-xl font-bold text-red-800">
                                <i class="fas fa-exclamation-circle text-red-500 mr-2"></i>
                                Citas Pendientes de Pago
                            </h3>
                        </div>

                        <c:forEach var="cita" items="${citasPendientesPago}">
                            <div class="bg-white border border-red-200 rounded-xl p-4 mb-4">
                                <div class="flex items-center justify-between">
                                    <div class="flex items-center">
                                        <div class="w-12 h-12 bg-red-100 rounded-full flex items-center justify-center mr-4">
                                            <i class="fas fa-tooth text-red-500"></i>
                                        </div>
                                        <div>
                                            <h4 class="font-semibold text-gray-800">${cita.nombreServicio}</h4>
                                            <p class="text-gray-600 text-sm">${cita.nombreOdontologo}</p>
                                            <p class="text-gray-500 text-sm">${cita.fecha} - ${cita.hora}</p>
                                            <c:if test="${cita.estado == 'derivada_sin_pagar'}">
                                                <span class="bg-orange-100 text-orange-800 px-2 py-1 rounded-full text-xs font-medium">
                                                    Derivada por doctor
                                                </span>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <div class="text-2xl font-bold text-red-600 mb-2">S/ ${cita.precioServicio}</div>
                                        <button onclick="procesarPago(${cita.idCita}, '${fn:escapeXml(cita.nombreServicio)}', ${cita.precioServicio})" 
                                                class="bg-red-500 text-white px-4 py-2 rounded-full hover:bg-red-600 transition-colors duration-300"
                                                data-cita-id="${cita.idCita}">
                                            <i class="fas fa-credit-card mr-1"></i>Pagar Ahora
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <!-- Próximas Citas -->
                <div id="citas-section" class="bg-white rounded-2xl shadow-lg p-6" data-aos="fade-up">
                    <div class="flex items-center justify-between mb-6">
                        <h3 class="text-xl font-bold text-gray-800">
                            <i class="fas fa-calendar-check text-blue-500 mr-2"></i>
                            Próximas Citas
                        </h3>
                    </div>

                    <c:choose>
                        <c:when test="${not empty citasProximas}">
                            <c:forEach var="cita" items="${citasProximas}">
                                <div class="border border-gray-200 rounded-xl p-4 mb-4 hover:shadow-md transition-shadow duration-300">
                                    <div class="flex items-center justify-between">
                                        <div class="flex items-center">
                                            <div class="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center mr-4">
                                                <i class="fas fa-tooth text-blue-500"></i>
                                            </div>
                                            <div>
                                                <h4 class="font-semibold text-gray-800">${cita.nombreServicio}</h4>
                                                <p class="text-gray-600 text-sm">${cita.nombreOdontologo}</p>
                                                <p class="text-gray-500 text-sm">${cita.fecha} - ${cita.hora}</p>
                                            </div>
                                        </div>
                                        <div class="text-right">
                                            <c:choose>
                                                <c:when test="${cita.estado == 'confirmada'}">
                                                    <span class="bg-green-100 text-green-800 px-3 py-1 rounded-full text-sm font-medium">Confirmada</span>
                                                </c:when>
                                                <c:when test="${cita.estado == 'sin_pagar'}">
                                                    <span class="bg-yellow-100 text-yellow-800 px-3 py-1 rounded-full text-sm font-medium">Sin Pagar</span>
                                                </c:when>
                                            </c:choose>
                                            <div class="mt-2">
                                                <button onclick="cancelarCita(${cita.idCita})" class="text-red-500 hover:text-red-600 text-sm mr-2">
                                                    Cancelar
                                                </button>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-8">
                                <i class="fas fa-calendar-times text-gray-300 text-4xl mb-4"></i>
                                <p class="text-gray-500">No tienes citas programadas</p>
                                <a href="${pageContext.request.contextPath}/reservar-cita" class="mt-4 inline-block bg-blue-500 text-white px-6 py-2 rounded-full hover:bg-blue-600 transition-colors duration-300">
                                    Reservar Primera Cita
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Historial de Citas -->
                <div id="historial-section" class="bg-white rounded-2xl shadow-lg p-6" data-aos="fade-up" data-aos-delay="200">
                    <div class="flex items-center justify-between mb-6">
                        <h3 class="text-xl font-bold text-gray-800">
                            <i class="fas fa-history text-purple-500 mr-2"></i>
                            Historial de Citas
                        </h3>
                    </div>

                    <c:choose>
                        <c:when test="${not empty historialCitas}">
                            <c:forEach var="cita" items="${historialCitas}">
                                <div class="border border-gray-200 rounded-xl p-4 mb-4 hover:shadow-md transition-shadow duration-300">
                                    <div class="flex items-center justify-between">
                                        <div class="flex items-center">
                                            <div class="w-12 h-12 bg-gray-100 rounded-full flex items-center justify-center mr-4">
                                                <i class="fas fa-tooth text-gray-500"></i>
                                            </div>
                                            <div>
                                                <h4 class="font-semibold text-gray-800">${cita.nombreServicio}</h4>
                                                <p class="text-gray-600 text-sm">${cita.nombreOdontologo}</p>
                                                <p class="text-gray-500 text-sm">${cita.fecha} - ${cita.hora}</p>
                                                <c:if test="${not empty cita.observaciones}">
                                                    <p class="text-gray-600 text-sm mt-1">
                                                        <i class="fas fa-comment mr-1"></i>${cita.observaciones}
                                                    </p>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="text-right">
                                            <span class="bg-gray-100 text-gray-800 px-3 py-1 rounded-full text-sm font-medium">
                                                ${cita.estado}
                                            </span>
                                            <c:if test="${not empty cita.receta}">
                                                <div class="mt-2">
                                                    <button onclick="verReceta('${fn:escapeXml(cita.receta)}')" class="text-blue-500 hover:text-blue-600 text-sm">
                                                        <i class="fas fa-prescription mr-1"></i>Ver Receta
                                                    </button>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-8">
                                <i class="fas fa-history text-gray-300 text-4xl mb-4"></i>
                                <p class="text-gray-500">No tienes historial de citas</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Right Column -->
            <div class="space-y-8">
                <!-- Notificaciones -->
                <div class="bg-white rounded-2xl shadow-lg p-6" data-aos="fade-up" data-aos-delay="300">
                    <h3 class="text-xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-bell text-yellow-500 mr-2"></i>
                        Notificaciones
                        <c:if test="${contadorNotificaciones > 0}">
                            <span class="bg-red-500 text-white text-xs px-2 py-1 rounded-full ml-2">${contadorNotificaciones}</span>
                        </c:if>
                    </h3>

                    <c:choose>
                        <c:when test="${not empty notificaciones}">
                            <div class="space-y-4 max-h-96 overflow-y-auto">
                                <c:forEach var="notificacionVista" items="${notificaciones}">
                                    <div class="flex-1">
                                        <p class="text-sm font-medium text-gray-800">${notificacionVista.notificacion.mensaje}</p>
                                        <p class="text-xs text-gray-500 mt-1">
                                            ${notificacionVista.fechaFormateada}
                                        </p>
                                        <c:if test="${!notificacionVista.notificacion.leido}">
                                            <button onclick="marcarComoLeida(${notificacionVista.notificacion.idNotificacion})"
                                                    class="text-blue-500 hover:underline text-xs mt-1">Marcar como leída</button>
                                        </c:if>
                                    </div>
                                </c:forEach>

                            </div>
                            <button onclick="marcarTodasComoLeidas()" class="w-full mt-4 text-center text-blue-500 hover:text-blue-600 font-medium text-sm">
                                Marcar todas como leídas
                            </button>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-8">
                                <i class="fas fa-bell-slash text-gray-300 text-3xl mb-4"></i>
                                <p class="text-gray-500">No tienes notificaciones</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Mi Perfil -->
                <div id="perfil-section" class="bg-white rounded-2xl shadow-lg p-6" data-aos="fade-up" data-aos-delay="400">
                    <h3 class="text-xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-user text-orange-500 mr-2"></i>
                        Mi Perfil
                    </h3>

                    <div class="text-center mb-6">
                        <div class="w-20 h-20 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full flex items-center justify-center mx-auto mb-4">
                            <i class="fas fa-user text-white text-2xl"></i>
                        </div>
                        <h4 class="font-bold text-gray-800">${sessionScope.usuario.nombre}</h4>
                        <p class="text-gray-600 text-sm">${sessionScope.usuario.correo}</p>
                    </div>

                    <div class="space-y-3">
                        <div class="flex justify-between">
                            <span class="text-gray-600">Teléfono:</span>
                            <span class="font-medium">${sessionScope.usuario.telefono}</span>
                        </div>
                        <div class="flex justify-between">
                            <span class="text-gray-600">Rol:</span>
                            <span class="font-medium capitalize">${sessionScope.usuario.nombreRol}</span>
                        </div>
                        <div class="flex justify-between">
                            <span class="text-gray-600">Citas totales:</span>
                            <span class="font-medium">${fn:length(historialCitas) + fn:length(citasProximas)}</span>
                        </div>
                    </div>

                    <button onclick="editarPerfil()" class="w-full mt-6 bg-orange-500 text-white py-2 rounded-xl hover:bg-orange-600 transition-colors duration-300">
                        <i class="fas fa-edit mr-2"></i>Editar Perfil
                    </button>
                </div>

                <!-- Servicios Rápidos -->
                <div class="bg-white rounded-2xl shadow-lg p-6" data-aos="fade-up" data-aos-delay="500">
                    <h3 class="text-xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-stethoscope text-green-500 mr-2"></i>
                        Reserva Rápida
                    </h3>

                    <div class="space-y-3">
                        <c:forEach var="servicio" items="${serviciosDestacados}">
                            <div class="flex justify-between items-center p-3 bg-gray-50 rounded-xl hover:bg-gray-100 transition-colors duration-300">
                                <div>
                                    <p class="font-medium text-gray-800 text-sm">${servicio.nombre}</p>
                                    <p class="text-green-600 font-bold text-sm">S/ ${servicio.precio}</p>
                                </div>
                                <c:choose>
                                    <c:when test="${tieneReservaActiva}">
                                        <button disabled class="bg-gray-300 text-gray-500 px-3 py-1 rounded-full text-xs cursor-not-allowed">
                                            No disponible
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/reservar-cita?servicio=${servicio.idServicio}" 
                                           class="bg-green-500 text-white px-3 py-1 rounded-full text-xs hover:bg-green-600 transition-colors duration-300">
                                            Reservar
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </div>

                    <a href="${pageContext.request.contextPath}/servicios" class="block w-full mt-4 text-center text-green-500 hover:text-green-600 font-medium text-sm">
                        Ver todos los servicios
                    </a>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Modal para Pago -->
<div id="pagoModal" class="fixed inset-0 bg-black bg-opacity-50 hidden z-50 flex items-center justify-center">
    <div class="bg-white rounded-2xl p-8 max-w-md w-full mx-4">
        <h3 class="text-2xl font-bold text-gray-800 mb-6 text-center">
            <i class="fas fa-credit-card text-blue-500 mr-2"></i>
            Procesar Pago
        </h3>

        <div id="pagoDetalles" class="mb-6">
            <!-- Los detalles del pago se llenarán dinámicamente -->
        </div>

        <div class="space-y-4">
            <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Método de Pago</label>
                <select id="metodoPago" class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none">
                    <option value="tarjeta">Tarjeta de Crédito/Débito</option>
                    <option value="yape">Yape</option>
                    <option value="plin">Plin</option>
                    <option value="efectivo">Efectivo</option>
                </select>
            </div>

            <div class="flex space-x-4">
                <button onclick="cerrarModalPago()" class="flex-1 bg-gray-300 text-gray-700 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                    Cancelar
                </button>
                <button onclick="confirmarPago()" class="flex-1 bg-blue-500 text-white py-3 rounded-xl hover:bg-blue-600 transition-colors duration-300">
                    <i class="fas fa-check mr-2"></i>Confirmar Pago
                </button>
            </div>
        </div>
    </div>
</div>

<!-- Modal para Receta -->
<div id="recetaModal" class="fixed inset-0 bg-black bg-opacity-50 hidden z-50 flex items-center justify-center">
    <div class="bg-white rounded-2xl p-8 max-w-lg w-full mx-4">
        <h3 class="text-2xl font-bold text-gray-800 mb-6 text-center">
            <i class="fas fa-prescription text-green-500 mr-2"></i>
            Receta Médica
        </h3>

        <div id="recetaContenido" class="mb-6 p-4 bg-gray-50 rounded-xl">
            <!-- El contenido de la receta se llenará dinámicamente -->
        </div>

        <div class="flex space-x-4">
            <button onclick="cerrarModalReceta()" class="flex-1 bg-gray-300 text-gray-700 py-3 rounded-xl hover:bg-gray-400 transition-colors duration-300">
                Cerrar
            </button>
            <button onclick="imprimirReceta()" class="flex-1 bg-green-500 text-white py-3 rounded-xl hover:bg-green-600 transition-colors duration-300">
                <i class="fas fa-print mr-2"></i>Imprimir
            </button>
        </div>
    </div>
</div>

<%@ include file="layout/footer.jsp" %>

<script>
    let citaIdParaPago = null;
    let precioParaPago = 0;
    let nombreServicioParaPago = '';

    // Función para procesar pago - CORREGIDA CON MÁS VALIDACIONES
    function procesarPago(idCita, nombreServicio, precio) {
        // Validar parámetros
        if (!idCita || idCita === '' || idCita === 'undefined') {
            alert('Error: ID de cita no válido');
            console.error('ID de cita inválido:', idCita);
            return;
        }

        if (!nombreServicio || nombreServicio === '') {
            alert('Error: Nombre de servicio no válido');
            console.error('Nombre de servicio inválido:', nombreServicio);
            return;
        }

        if (!precio || precio <= 0) {
            alert('Error: Precio no válido');
            console.error('Precio inválido:', precio);
            return;
        }

        // Asignar valores globales
        citaIdParaPago = parseInt(idCita);
        precioParaPago = parseFloat(precio);
        nombreServicioParaPago = nombreServicio;

        console.log('Procesando pago para:', {
            idCita: citaIdParaPago,
            servicio: nombreServicioParaPago,
            precio: precioParaPago
        });

        // Mostrar modal con detalles
        document.getElementById('pagoDetalles').innerHTML = 
            '<div class="text-center">' +
                '<h4 class="font-bold text-lg text-gray-800 mb-2">' + nombreServicioParaPago + '</h4>' +
                '<div class="text-3xl font-bold text-blue-600 mb-4">S/ ' + precioParaPago.toFixed(2) + '</div>' +
                '<p class="text-gray-600 text-sm">Selecciona tu método de pago preferido</p>' +
                '<p class="text-xs text-gray-500 mt-2">ID Cita: ' + citaIdParaPago + '</p>' +
            '</div>';

        document.getElementById('pagoModal').classList.remove('hidden');
    }

    // Función para confirmar pago - CORREGIDA
    function confirmarPago() {
        // Validar que tenemos los datos necesarios
        if (!citaIdParaPago || citaIdParaPago <= 0) {
            alert('Error: No se ha seleccionado una cita válida');
            console.error('citaIdParaPago inválido:', citaIdParaPago);
            return;
        }

        const metodoPago = document.getElementById('metodoPago').value;
        if (!metodoPago || metodoPago.trim() === '') {
            alert('Por favor selecciona un método de pago');
            return;
        }

        console.log('Confirmando pago con datos:', {
            idCita: citaIdParaPago,
            metodoPago: metodoPago
        });

        // Mostrar loading
        const botonConfirmar = document.querySelector('#pagoModal button[onclick="confirmarPago()"]');
        const textoOriginal = botonConfirmar.innerHTML;
        botonConfirmar.innerHTML = '<i class="fas fa-spinner fa-spin mr-2"></i>Procesando...';
        botonConfirmar.disabled = true;

        // Usar URLSearchParams para envío más confiable
        const params = new URLSearchParams();
        params.append('idCita', citaIdParaPago.toString());
        params.append('metodoPago', metodoPago.trim());

        console.log('Enviando parámetros:', params.toString());

        // Procesar pago
        fetch('${pageContext.request.contextPath}/procesar-pago', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: params.toString()
        })
        .then(response => {
            console.log('Respuesta del servidor:', response.status, response.statusText);
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log('Datos recibidos:', data);
            if (data.success) {
                alert('¡Pago procesado exitosamente! Tu cita ha sido confirmada.');
                location.reload();
            } else {
                alert('Error al procesar el pago: ' + (data.message || 'Error desconocido'));
                console.error('Error del servidor:', data);
            }
        })
        .catch(error => {
            console.error('Error completo:', error);
            alert('Error de conexión: ' + error.message);
        })
        .finally(() => {
            // Restaurar botón
            botonConfirmar.innerHTML = textoOriginal;
            botonConfirmar.disabled = false;
        });
    }

    // Función para cerrar modal de pago
    function cerrarModalPago() {
        document.getElementById('pagoModal').classList.add('hidden');
        citaIdParaPago = null;
        precioParaPago = 0;
        nombreServicioParaPago = '';
    }

    // Función para ver receta
    function verReceta(receta) {
        if (!receta || receta.trim() === '') {
            receta = 'No hay receta disponible para esta cita.';
        }
        document.getElementById('recetaContenido').innerHTML = 
            '<div class="text-gray-700 leading-relaxed">' + receta + '</div>';
        document.getElementById('recetaModal').classList.remove('hidden');
    }

    // Función para cerrar modal de receta
    function cerrarModalReceta() {
        document.getElementById('recetaModal').classList.add('hidden');
    }

    // Función para imprimir receta
    function imprimirReceta() {
        const contenido = document.getElementById('recetaContenido').innerHTML;
        const fecha = new Date().toLocaleDateString();
        const ventana = window.open('', '_blank');
        ventana.document.write(
            '<html>' +
                '<head>' +
                    '<title>Receta Médica - ArquiDent</title>' +
                    '<style>' +
                        'body { font-family: Arial, sans-serif; padding: 20px; }' +
                        '.header { text-align: center; margin-bottom: 30px; }' +
                        '.content { line-height: 1.6; }' +
                    '</style>' +
                '</head>' +
                '<body>' +
                    '<div class="header">' +
                        '<h1>? ArquiDent</h1>' +
                        '<h2>Receta Médica</h2>' +
                        '<p>Paciente: ${sessionScope.usuario.nombre}</p>' +
                        '<p>Fecha: ' + fecha + '</p>' +
                    '</div>' +
                    '<div class="content">' + contenido + '</div>' +
                '</body>' +
            '</html>'
        );
        ventana.document.close();
        ventana.print();
    }

    // Función para cancelar cita
    function cancelarCita(idCita) {
        if (confirm('¿Estás seguro de que deseas cancelar esta cita?')) {
            const params = new URLSearchParams();
            params.append('idCita', idCita);

            fetch('${pageContext.request.contextPath}/cancelar-cita', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: params.toString()
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Cita cancelada exitosamente.');
                    location.reload();
                } else {
                    alert('Error al cancelar la cita: ' + (data.message || 'Error desconocido'));
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error de conexión. Inténtalo nuevamente.');
            });
        }
    }

    // Función para reprogramar cita
    function reprogramarCita(idCita) {
        alert('Funcionalidad de reprogramación en desarrollo. Por favor, contacta a la clínica.');
    }

    // Función para marcar notificación como leída
    function marcarComoLeida(idNotificacion) {
        const params = new URLSearchParams();
        params.append('idNotificacion', idNotificacion);

        fetch('${pageContext.request.contextPath}/marcar-notificacion', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: params.toString()
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                location.reload();
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }

    // Función para marcar todas las notificaciones como leídas
    function marcarTodasComoLeidas() {
        fetch('${pageContext.request.contextPath}/marcar-todas-notificaciones', {
            method: 'POST'
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                location.reload();
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }

    // Función para editar perfil
    function editarPerfil() {
        window.location.href = '${pageContext.request.contextPath}/editar-perfil';
    }

    // Función para scroll suave a secciones
    function scrollToSection(sectionId) {
        document.getElementById(sectionId).scrollIntoView({
            behavior: 'smooth'
        });
    }

    // Cerrar modales al hacer clic fuera
    window.onclick = function (event) {
        const pagoModal = document.getElementById('pagoModal');
        const recetaModal = document.getElementById('recetaModal');

        if (event.target === pagoModal) {
            cerrarModalPago();
        }
        if (event.target === recetaModal) {
            cerrarModalReceta();
        }
    }

    // Validar parámetros al cargar la página
    document.addEventListener('DOMContentLoaded', function() {
        console.log('Dashboard cargado correctamente');
        
        // Verificar que las citas tienen IDs válidos
        const botonesPago = document.querySelectorAll('button[data-cita-id]');
        botonesPago.forEach(boton => {
            const citaId = boton.getAttribute('data-cita-id');
            if (!citaId || citaId === 'null' || citaId === 'undefined') {
                console.error('Botón de pago con ID inválido:', boton);
                boton.disabled = true;
                boton.innerHTML = '<i class="fas fa-exclamation-triangle mr-1"></i>Error';
            }
        });
    });
</script>
