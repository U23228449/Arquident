<%@ include file="layout/header.jsp" %>

<section class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 py-12">
    <div class="max-w-md mx-auto px-4 sm:px-6 lg:px-8">
        <div class="bg-white rounded-3xl shadow-2xl overflow-hidden" data-aos="fade-up">
            <!-- Header del formulario -->
            <div class="gradient-bg text-white text-center py-8">
                <div class="w-20 h-20 bg-white bg-opacity-20 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-lock text-3xl"></i>
                </div>
                <h2 class="text-3xl font-bold">Iniciar Sesión</h2>
                <p class="text-blue-100 mt-2">Accede a tu cuenta de ArquiDent</p>
            </div>

            <!-- Formulario -->
            <div class="p-8">
                <c:if test="${not empty error}">
                    <div class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-xl mb-6 animate-slide-up" role="alert">
                        <div class="flex items-center">
                            <i class="fas fa-exclamation-triangle mr-2"></i>
                            <span>${error}</span>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty mensaje}">
                    <div class="bg-green-50 border border-green-200 text-green-700 px-4 py-3 rounded-xl mb-6 animate-slide-up" role="alert">
                        <div class="flex items-center">
                            <i class="fas fa-check-circle mr-2"></i>
                            <span>${mensaje}</span>
                        </div>
                    </div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/login" class="space-y-6">
                    <div class="space-y-2">
                        <label for="correo" class="block text-sm font-semibold text-gray-700">
                            <i class="fas fa-envelope mr-2 text-primary-500"></i>Correo Electrónico
                        </label>
                        <input type="email" id="correo" name="correo" required
                               class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300 text-lg"
                               placeholder="ejemplo@correo.com">
                    </div>

                    <div class="space-y-2">
                        <label for="contrasena" class="block text-sm font-semibold text-gray-700">
                            <i class="fas fa-key mr-2 text-primary-500"></i>Contraseña
                        </label>
                        <input type="password" id="contrasena" name="contrasena" required
                               class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300 text-lg"
                               placeholder="Ingresa tu contraseña">
                    </div>

                    <button type="submit" 
                            class="w-full gradient-bg text-white py-4 rounded-xl font-bold text-lg hover:shadow-lg transition-all duration-300 transform hover:scale-105">
                        <i class="fas fa-sign-in-alt mr-2"></i>Iniciar Sesión
                    </button>
                </form>

                <!-- Link para registro -->
                <div class="mt-8 text-center">
                    <p class="text-gray-600">
                        ¿No tienes una cuenta? 
                        <a href="${pageContext.request.contextPath}/registro" 
                           class="text-primary-600 hover:underline font-semibold">
                            Regístrate aquí
                        </a>
                    </p>
                </div>

                <!-- Usuarios de prueba -->
                <div class="mt-8 p-6 bg-gray-50 rounded-2xl">
                    <h4 class="text-center font-bold text-gray-700 mb-4">
                        <i class="fas fa-users mr-2 text-primary-500"></i>Usuarios de Prueba
                    </h4>
                    <div class="space-y-3 text-sm">
                        <div class="flex justify-between items-center p-3 bg-white rounded-xl">
                            <div>
                                <span class="font-semibold text-blue-600">Paciente:</span>
                                <p class="text-gray-600">carlos@paciente.com</p>
                            </div>
                            <span class="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-xs font-medium">pass123</span>
                        </div>
                        <div class="flex justify-between items-center p-3 bg-white rounded-xl">
                            <div>
                                <span class="font-semibold text-green-600">Odontólogo:</span>
                                <p class="text-gray-600">luis@arqui.com</p>
                            </div>
                            <span class="bg-green-100 text-green-800 px-3 py-1 rounded-full text-xs font-medium">odonto123</span>
                        </div>
                        <div class="flex justify-between items-center p-3 bg-white rounded-xl">
                            <div>
                                <span class="font-semibold text-purple-600">Secretaria:</span>
                                <p class="text-gray-600">ana@arqui.com</p>
                            </div>
                            <span class="bg-purple-100 text-purple-800 px-3 py-1 rounded-full text-xs font-medium">secret123</span>
                        </div>
                        <div class="flex justify-between items-center p-3 bg-white rounded-xl">
                            <div>
                                <span class="font-semibold text-red-600">Admin:</span>
                                <p class="text-gray-600">admin@arqui.com</p>
                            </div>
                            <span class="bg-red-100 text-red-800 px-3 py-1 rounded-full text-xs font-medium">admin123</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>
