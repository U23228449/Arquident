<%@ include file="layout/header.jsp" %>

<!-- Hero Section -->
<section class="gradient-bg text-white py-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h1 class="text-4xl md:text-6xl font-bold mb-6" data-aos="fade-up">
            Nuestros Servicios Dentales
        </h1>
        <p class="text-xl md:text-2xl text-blue-100 max-w-3xl mx-auto" data-aos="fade-up" data-aos-delay="200">
            Ofrecemos una amplia gama de servicios dentales con la más alta calidad y tecnología avanzada
        </p>
    </div>
</section>

<!-- Filtros -->
<section class="py-8 bg-white shadow-sm">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex flex-wrap justify-center gap-4" data-aos="fade-up">
            <button class="bg-primary-500 text-white px-6 py-2 rounded-full hover:bg-primary-600 transition-colors duration-300">
                Todos los Servicios
            </button>
            <button class="bg-gray-200 text-gray-700 px-6 py-2 rounded-full hover:bg-gray-300 transition-colors duration-300">
                Preventivos
            </button>
            <button class="bg-gray-200 text-gray-700 px-6 py-2 rounded-full hover:bg-gray-300 transition-colors duration-300">
                Estéticos
            </button>
            <button class="bg-gray-200 text-gray-700 px-6 py-2 rounded-full hover:bg-gray-300 transition-colors duration-300">
                Restaurativos
            </button>
        </div>
    </div>
</section>

<!-- Servicios Grid -->
<section class="py-20 bg-gray-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
            <c:forEach var="servicio" items="${servicios}" varStatus="status">
                <div class="group bg-white rounded-2xl shadow-lg overflow-hidden hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-2" data-aos="fade-up" data-aos-delay="${(status.index % 3 + 1) * 100}">
                    <!-- Header del servicio -->
                    <div class="gradient-bg p-6 text-white text-center">
                        <div class="w-16 h-16 bg-white bg-opacity-20 rounded-full flex items-center justify-center mx-auto mb-4 group-hover:scale-110 transition-transform duration-300">
                            <i class="fas fa-tooth text-2xl"></i>
                        </div>
                        <h3 class="text-xl font-bold">${servicio.nombre}</h3>
                    </div>
                    
                    <!-- Contenido del servicio -->
                    <div class="p-6">
                        <p class="text-gray-600 mb-6 leading-relaxed">${servicio.descripcion}</p>
                        
                        <!-- Precio y acción -->
                        <div class="flex justify-between items-center">
                            <div>
                                <span class="text-sm text-gray-500">Precio desde</span>
                                <div class="text-2xl font-bold text-primary-600">S/. ${servicio.precio}</div>
                            </div>
                            <c:if test="${sessionScope.usuario != null && sessionScope.rolUsuario == 'paciente'}">
                                <a href="${pageContext.request.contextPath}/reservar-cita?servicio=${servicio.idServicio}" 
                                   class="bg-primary-500 text-white px-6 py-3 rounded-full hover:bg-primary-600 transition-all duration-300 transform hover:scale-105 shadow-lg">
                                    <i class="fas fa-calendar-plus mr-1"></i>Reservar
                                </a>
                            </c:if>
                        </div>
                        
                        <!-- Características del servicio -->
                        <div class="mt-4 pt-4 border-t border-gray-100">
                            <div class="flex items-center text-sm text-gray-500 mb-2">
                                <i class="fas fa-clock mr-2 text-primary-500"></i>
                                <span>Duración: 30-60 min</span>
                            </div>
                            <div class="flex items-center text-sm text-gray-500">
                                <i class="fas fa-shield-alt mr-2 text-primary-500"></i>
                                <span>Garantía incluida</span>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <c:if test="${empty servicios}">
            <div class="text-center py-20" data-aos="fade-up">
                <i class="fas fa-stethoscope text-gray-300 text-6xl mb-6"></i>
                <h3 class="text-3xl font-bold text-gray-600 mb-4">No hay servicios disponibles</h3>
                <p class="text-xl text-gray-500 mb-8">Estamos trabajando en agregar más servicios para ti.</p>
                <a href="${pageContext.request.contextPath}/inicio" class="bg-primary-500 text-white px-8 py-4 rounded-full font-bold hover:bg-primary-600 transition-all duration-300 transform hover:scale-105">
                    Volver al Inicio
                </a>
            </div>
        </c:if>
    </div>
</section>

<!-- CTA Section -->
<section class="gradient-bg text-white py-20">
    <div class="max-w-4xl mx-auto text-center px-4 sm:px-6 lg:px-8" data-aos="fade-up">
        <h2 class="text-4xl font-bold mb-6">¿Necesitas más información?</h2>
        <p class="text-xl text-blue-100 mb-8">
            Nuestro equipo está listo para ayudarte a elegir el mejor tratamiento para ti
        </p>
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <a href="tel:+51999888777" class="bg-white text-primary-600 px-8 py-4 rounded-full font-bold text-lg hover:bg-gray-100 transition-all duration-300 transform hover:scale-105 shadow-lg">
                <i class="fas fa-phone mr-2"></i>Llamar Ahora
            </a>
            <a href="${pageContext.request.contextPath}/faq" class="border-2 border-white text-white px-8 py-4 rounded-full font-bold text-lg hover:bg-white hover:text-primary-600 transition-all duration-300 transform hover:scale-105">
                <i class="fas fa-question-circle mr-2"></i>Ver FAQ
            </a>
        </div>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>
