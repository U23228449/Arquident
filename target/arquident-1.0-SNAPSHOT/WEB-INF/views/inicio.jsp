<%@ include file="layout/header.jsp" %>

<!-- Hero Section -->
<section class="gradient-bg text-white py-20 overflow-hidden">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid lg:grid-cols-2 gap-12 items-center min-h-[70vh]">
            <div data-aos="fade-right">
                <h1 class="text-4xl md:text-6xl font-bold mb-6 leading-tight">
                    Tu Sonrisa es Nuestra 
                    <span class="text-transparent bg-clip-text bg-gradient-to-r from-yellow-400 to-orange-500">
                        Pasión
                    </span>
                </h1>
                <p class="text-xl md:text-2xl text-blue-100 mb-8 leading-relaxed">
                    En ArquiDent ofrecemos servicios dentales de calidad con tecnología de vanguardia y un equipo profesional comprometido con tu bienestar y salud bucal.
                </p>
                <div class="flex flex-col sm:flex-row gap-4">
                    <c:choose>
                        <c:when test="${sessionScope.usuario != null && sessionScope.rolUsuario == 'paciente'}">
                            <a href="${pageContext.request.contextPath}/reservar-cita" class="bg-white text-primary-600 px-8 py-4 rounded-full font-bold text-lg hover:bg-gray-100 transition-all duration-300 transform hover:scale-105 shadow-lg">
                                <i class="fas fa-calendar-plus mr-2"></i>Reservar Cita
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/login" class="bg-white text-primary-600 px-8 py-4 rounded-full font-bold text-lg hover:bg-gray-100 transition-all duration-300 transform hover:scale-105 shadow-lg">
                                <i class="fas fa-calendar-plus mr-2"></i>Reservar Cita
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/servicios" class="border-2 border-white text-white px-8 py-4 rounded-full font-bold text-lg hover:bg-white hover:text-primary-600 transition-all duration-300 transform hover:scale-105">
                        <i class="fas fa-stethoscope mr-2"></i>Ver Servicios
                    </a>
                </div>
            </div>
            <div class="text-center" data-aos="fade-left">
                <div class="relative">
                    <div class="glass-effect rounded-3xl p-12 transform rotate-3 hover:rotate-0 transition-transform duration-500">
                        <i class="fas fa-tooth text-8xl text-white mb-6 animate-bounce-slow"></i>
                        <h3 class="text-2xl font-bold text-white mb-2">Clínica Dental Moderna</h3>
                        <p class="text-blue-100">Tecnología de vanguardia</p>
                    </div>
                    <!-- Elementos decorativos -->
                    <div class="absolute -top-4 -right-4 w-8 h-8 bg-yellow-400 rounded-full animate-ping"></div>
                    <div class="absolute -bottom-4 -left-4 w-6 h-6 bg-pink-400 rounded-full animate-pulse"></div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Características Principales -->
<section class="py-20 bg-gray-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
            <h2 class="text-4xl font-bold text-gray-800 mb-4" data-aos="fade-up">¿Por qué elegir ArquiDent?</h2>
            <p class="text-xl text-gray-600 max-w-2xl mx-auto" data-aos="fade-up" data-aos-delay="200">
                Combinamos experiencia, tecnología y calidez humana para brindarte la mejor atención dental
            </p>
        </div>
        
        <div class="grid md:grid-cols-3 gap-8">
            <div class="bg-white rounded-2xl shadow-lg p-8 text-center hover:shadow-xl transition-all duration-300 transform hover:-translate-y-2" data-aos="fade-up" data-aos-delay="100">
                <div class="w-20 h-20 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full flex items-center justify-center mx-auto mb-6">
                    <i class="fas fa-user-md text-white text-3xl"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-800 mb-4">Profesionales Expertos</h3>
                <p class="text-gray-600 leading-relaxed">
                    Nuestro equipo está formado por odontólogos especializados con años de experiencia y formación continua en las mejores técnicas.
                </p>
            </div>
            
            <div class="bg-white rounded-2xl shadow-lg p-8 text-center hover:shadow-xl transition-all duration-300 transform hover:-translate-y-2" data-aos="fade-up" data-aos-delay="200">
                <div class="w-20 h-20 bg-gradient-to-r from-green-500 to-teal-500 rounded-full flex items-center justify-center mx-auto mb-6">
                    <i class="fas fa-cogs text-white text-3xl"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-800 mb-4">Tecnología Avanzada</h3>
                <p class="text-gray-600 leading-relaxed">
                    Utilizamos equipos de última generación para brindarte tratamientos precisos, cómodos y con los mejores resultados.
                </p>
            </div>
            
            <div class="bg-white rounded-2xl shadow-lg p-8 text-center hover:shadow-xl transition-all duration-300 transform hover:-translate-y-2" data-aos="fade-up" data-aos-delay="300">
                <div class="w-20 h-20 bg-gradient-to-r from-pink-500 to-red-500 rounded-full flex items-center justify-center mx-auto mb-6">
                    <i class="fas fa-heart text-white text-3xl"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-800 mb-4">Atención Personalizada</h3>
                <p class="text-gray-600 leading-relaxed">
                    Cada paciente es único, por eso adaptamos nuestros tratamientos a tus necesidades específicas con horarios flexibles.
                </p>
            </div>
        </div>
    </div>
</section>

<!-- Servicios Destacados -->
<section class="py-20 bg-white">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
            <h2 class="text-4xl font-bold text-gray-800 mb-4" data-aos="fade-up">Nuestros Servicios Destacados</h2>
            <p class="text-xl text-gray-600 max-w-2xl mx-auto" data-aos="fade-up" data-aos-delay="200">
                Ofrecemos una amplia gama de servicios dentales para cuidar tu salud bucal
            </p>
        </div>
        
        <div class="grid md:grid-cols-3 gap-8">
            <c:forEach var="servicio" items="${serviciosDestacados}" varStatus="status">
                <div class="group bg-white border-2 border-gray-100 rounded-2xl shadow-lg p-6 hover:shadow-xl hover:border-primary-200 transition-all duration-300 transform hover:-translate-y-2" data-aos="fade-up" data-aos-delay="${(status.index + 1) * 100}">
                    <div class="w-16 h-16 bg-gradient-to-r from-primary-500 to-secondary-500 rounded-full flex items-center justify-center mb-6 group-hover:scale-110 transition-transform duration-300">
                        <i class="fas fa-tooth text-white text-2xl"></i>
                    </div>
                    <h3 class="text-xl font-bold text-gray-800 mb-3 group-hover:text-primary-600 transition-colors duration-300">
                        ${servicio.nombre}
                    </h3>
                    <p class="text-gray-600 mb-6 leading-relaxed">${servicio.descripcion}</p>
                    <div class="flex justify-between items-center">
                        <span class="text-2xl font-bold text-primary-600">S/ ${servicio.precio}</span>
                        <c:if test="${sessionScope.usuario != null && sessionScope.rolUsuario == 'paciente'}">
                            <a href="${pageContext.request.contextPath}/reservar-cita?servicio=${servicio.idServicio}" 
                               class="bg-primary-500 text-white px-6 py-2 rounded-full hover:bg-primary-600 transition-all duration-300 transform hover:scale-105">
                                Reservar
                            </a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <div class="text-center mt-12" data-aos="fade-up" data-aos-delay="600">
            <a href="${pageContext.request.contextPath}/servicios" class="bg-primary-500 text-white px-8 py-4 rounded-full font-bold text-lg hover:bg-primary-600 transition-all duration-300 transform hover:scale-105 shadow-lg">
                <i class="fas fa-eye mr-2"></i>Ver Todos los Servicios
            </a>
        </div>
    </div>
</section>

<!-- Call to Action -->
<section class="gradient-bg text-white py-20">
    <div class="max-w-4xl mx-auto text-center px-4 sm:px-6 lg:px-8">
        <div data-aos="fade-up">
            <h2 class="text-4xl md:text-5xl font-bold mb-6">¿Listo para Mejorar tu Sonrisa?</h2>
            <p class="text-xl text-blue-100 mb-8 leading-relaxed">
                Agenda tu cita hoy mismo y descubre por qué somos la clínica dental de confianza en Lima.
            </p>
            <div class="flex flex-col sm:flex-row gap-4 justify-center" data-aos="fade-up" data-aos-delay="200">
                <c:choose>
                    <c:when test="${sessionScope.usuario != null && sessionScope.rolUsuario == 'paciente'}">
                        <a href="${pageContext.request.contextPath}/reservar-cita" class="bg-white text-primary-600 px-8 py-4 rounded-full font-bold text-lg hover:bg-gray-100 transition-all duration-300 transform hover:scale-105 shadow-lg">
                            <i class="fas fa-calendar-plus mr-2"></i>Reservar Cita Ahora
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login" class="bg-white text-primary-600 px-8 py-4 rounded-full font-bold text-lg hover:bg-gray-100 transition-all duration-300 transform hover:scale-105 shadow-lg">
                            <i class="fas fa-calendar-plus mr-2"></i>Reservar Cita Ahora
                        </a>
                    </c:otherwise>
                </c:choose>
                <a href="tel:+51999888777" class="border-2 border-white text-white px-8 py-4 rounded-full font-bold text-lg hover:bg-white hover:text-primary-600 transition-all duration-300 transform hover:scale-105">
                    <i class="fas fa-phone mr-2"></i>Llamar Ahora
                </a>
            </div>
        </div>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>
