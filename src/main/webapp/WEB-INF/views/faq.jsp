<%@ include file="layout/header.jsp" %>

<section class="gradient-bg text-white py-16 relative overflow-hidden">
    <div class="absolute top-0 left-0 w-full h-full overflow-hidden opacity-10">
        <i class="fas fa-question absolute top-10 left-10 text-6xl"></i>
        <i class="fas fa-comment-dots absolute bottom-10 right-10 text-8xl"></i>
        <i class="fas fa-life-ring absolute top-1/2 left-1/2 text-9xl transform -translate-x-1/2 -translate-y-1/2"></i>
    </div>

    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center relative z-10">
        <h1 class="text-3xl md:text-5xl font-bold mb-4 animate-fade-in-down">
            ¿Cómo podemos ayudarte hoy?
        </h1>
        <p class="text-blue-100 text-lg mb-8">
            Busca respuestas sobre citas, pagos y servicios dentales.
        </p>

        <div class="relative max-w-2xl mx-auto transform hover:scale-105 transition-transform duration-300">
            <input type="text" disabled 
                   placeholder="Escribe tu pregunta aquí..." 
                   class="w-full px-6 py-4 rounded-full text-gray-700 focus:outline-none shadow-lg bg-white bg-opacity-95 cursor-not-allowed">
            <button class="absolute right-2 top-2 bg-blue-600 text-white w-10 h-10 rounded-full flex items-center justify-center cursor-not-allowed">
                <i class="fas fa-search"></i>
            </button>
        </div>
    </div>
</section>

<section class="py-8 bg-gray-50 border-b">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex flex-wrap justify-center gap-4 opacity-60 grayscale">
            <div class="bg-white px-6 py-2 rounded-full shadow-sm border border-gray-200 text-gray-500 cursor-not-allowed flex items-center">
                <i class="fas fa-calendar-alt mr-2"></i> Citas
            </div>
            <div class="bg-white px-6 py-2 rounded-full shadow-sm border border-gray-200 text-gray-500 cursor-not-allowed flex items-center">
                <i class="fas fa-credit-card mr-2"></i> Pagos
            </div>
            <div class="bg-white px-6 py-2 rounded-full shadow-sm border border-gray-200 text-gray-500 cursor-not-allowed flex items-center">
                <i class="fas fa-tooth mr-2"></i> Tratamientos
            </div>
            <div class="bg-white px-6 py-2 rounded-full shadow-sm border border-gray-200 text-gray-500 cursor-not-allowed flex items-center">
                <i class="fas fa-user-md mr-2"></i> Doctores
            </div>
        </div>
    </div>
</section>

<section class="py-16 bg-white">
    <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <div class="flex flex-col md:flex-row items-center bg-blue-50 rounded-3xl p-8 md:p-12 shadow-inner">
            <div class="md:w-1/2 text-center mb-8 md:mb-0">
                <div class="relative inline-block">
                    <div class="w-40 h-40 bg-white rounded-full flex items-center justify-center shadow-xl mx-auto relative z-10">
                        <i class="fas fa-book-reader text-6xl text-blue-500 animate-pulse"></i>
                    </div>
                    <div class="absolute top-0 -right-4 w-12 h-12 bg-yellow-400 rounded-full opacity-50 animate-bounce" style="animation-delay: 0.5s"></div>
                    <div class="absolute bottom-0 -left-4 w-8 h-8 bg-purple-400 rounded-full opacity-50 animate-bounce"></div>
                </div>
            </div>

            <div class="md:w-1/2 md:pl-10 text-center md:text-left">
                <span class="bg-blue-100 text-blue-600 px-3 py-1 rounded-full text-xs font-bold tracking-wide uppercase mb-4 inline-block">
                    En Redacción
                </span>
                <h2 class="text-3xl font-bold text-gray-800 mb-4">
                    Estamos construyendo nuestra base de conocimiento
                </h2>
                <p class="text-gray-600 leading-relaxed mb-6">
                    Nuestro equipo médico está recopilando las preguntas más frecuentes para ofrecerte respuestas precisas y detalladas. Muy pronto podrás resolver tus dudas sobre presupuestos, duración de tratamientos y seguros en esta sección.
                </p>
                <div class="p-4 bg-white rounded-xl border-l-4 border-yellow-400 shadow-sm text-left">
                    <p class="text-sm text-gray-700">
                        <i class="fas fa-lightbulb text-yellow-500 mr-2"></i>
                        <strong>¿Sabías que?</strong> Mientras tanto, puedes gestionar tus citas directamente desde tu <a href="${pageContext.request.contextPath}/dashboard-paciente" class="text-blue-600 hover:underline font-medium">Panel de Control</a>.
                    </p>
                </div>
            </div>
        </div>

    </div>
</section>

<section class="py-12 bg-gray-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-10">
            <h2 class="text-2xl font-bold text-gray-800">¿Tienes una duda urgente?</h2>
            <p class="text-gray-500">Elige uno de nuestros canales de atención directa</p>
        </div>

        <div class="grid md:grid-cols-3 gap-6 max-w-4xl mx-auto">
            <a href="https://wa.me/51999999999" target="_blank" class="group bg-white p-6 rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 border border-transparent hover:border-green-400">
                <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mb-4 group-hover:bg-green-500 transition-colors duration-300">
                    <i class="fab fa-whatsapp text-2xl text-green-600 group-hover:text-white"></i>
                </div>
                <h3 class="font-bold text-gray-800 mb-1">Chat por WhatsApp</h3>
                <p class="text-sm text-gray-500">Respuesta promedio: 5 min</p>
            </a>

            <a href="tel:012345678" class="group bg-white p-6 rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 border border-transparent hover:border-blue-400">
                <div class="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center mb-4 group-hover:bg-blue-500 transition-colors duration-300">
                    <i class="fas fa-phone-alt text-2xl text-blue-600 group-hover:text-white"></i>
                </div>
                <h3 class="font-bold text-gray-800 mb-1">Llámanos</h3>
                <p class="text-sm text-gray-500">Lun-Sab 8:00am - 8:00pm</p>
            </a>

            <a href="mailto:contacto@arquident.com" class="group bg-white p-6 rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 border border-transparent hover:border-purple-400">
                <div class="w-12 h-12 bg-purple-100 rounded-full flex items-center justify-center mb-4 group-hover:bg-purple-500 transition-colors duration-300">
                    <i class="fas fa-envelope text-2xl text-purple-600 group-hover:text-white"></i>
                </div>
                <h3 class="font-bold text-gray-800 mb-1">Correo Electrónico</h3>
                <p class="text-sm text-gray-500">Para consultas detalladas</p>
            </a>
        </div>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>