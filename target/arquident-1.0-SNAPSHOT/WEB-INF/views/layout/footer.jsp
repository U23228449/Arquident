<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!-- Footer -->
    <footer class="bg-gray-900 text-white py-12 mt-12">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
                <!-- Logo y Descripción -->
                <div class="lg:col-span-2">
                    <div class="flex items-center mb-4">
                        <i class="fas fa-tooth text-primary-500 text-3xl mr-3 animate-pulse-slow"></i>
                        <h3 class="text-2xl font-bold">ArquiDent</h3>
                    </div>
                    <p class="text-gray-300 mb-6 max-w-md">
                        Tu sonrisa es nuestra pasión. Brindamos servicios dentales de calidad con tecnología de vanguardia y un equipo profesional comprometido con tu bienestar.
                    </p>
                    <div class="flex space-x-4">
                        <a href="#" class="w-10 h-10 bg-primary-500 rounded-full flex items-center justify-center hover:bg-primary-600 transition-colors duration-300 transform hover:scale-110">
                            <i class="fab fa-facebook text-white"></i>
                        </a>
                        <a href="#" class="w-10 h-10 bg-primary-500 rounded-full flex items-center justify-center hover:bg-primary-600 transition-colors duration-300 transform hover:scale-110">
                            <i class="fab fa-instagram text-white"></i>
                        </a>
                        <a href="#" class="w-10 h-10 bg-primary-500 rounded-full flex items-center justify-center hover:bg-primary-600 transition-colors duration-300 transform hover:scale-110">
                            <i class="fab fa-twitter text-white"></i>
                        </a>
                        <a href="#" class="w-10 h-10 bg-primary-500 rounded-full flex items-center justify-center hover:bg-primary-600 transition-colors duration-300 transform hover:scale-110">
                            <i class="fab fa-linkedin text-white"></i>
                        </a>
                    </div>
                </div>
                
                <!-- Enlaces Rápidos -->
                <div>
                    <h4 class="text-lg font-semibold mb-4 text-primary-400">Enlaces Rápidos</h4>
                    <ul class="space-y-2">
                        <li><a href="${pageContext.request.contextPath}/inicio" class="text-gray-300 hover:text-white transition-colors duration-300">Inicio</a></li>
                        <li><a href="${pageContext.request.contextPath}/servicios" class="text-gray-300 hover:text-white transition-colors duration-300">Servicios</a></li>
                        <li><a href="${pageContext.request.contextPath}/nosotros" class="text-gray-300 hover:text-white transition-colors duration-300">Nosotros</a></li>
                        <li><a href="${pageContext.request.contextPath}/faq" class="text-gray-300 hover:text-white transition-colors duration-300">FAQ</a></li>
                    </ul>
                </div>
                
                <!-- Contacto -->
                <div>
                    <h4 class="text-lg font-semibold mb-4 text-primary-400">Contacto</h4>
                    <div class="space-y-3">
                        <div class="flex items-center">
                            <i class="fas fa-map-marker-alt text-primary-500 mr-3"></i>
                            <span class="text-gray-300 text-sm">Av. Principal 123, Lima, Perú</span>
                        </div>
                        <div class="flex items-center">
                            <i class="fas fa-phone text-primary-500 mr-3"></i>
                            <span class="text-gray-300 text-sm">+51 999 888 777</span>
                        </div>
                        <div class="flex items-center">
                            <i class="fas fa-envelope text-primary-500 mr-3"></i>
                            <span class="text-gray-300 text-sm">info@arquident.com</span>
                        </div>
                        <div class="flex items-center">
                            <i class="fas fa-clock text-primary-500 mr-3"></i>
                            <span class="text-gray-300 text-sm">Lun - Vie: 8:00 - 18:00</span>
                        </div>
                    </div>
                </div>
            </div>
            
            <hr class="my-8 border-gray-700">
            
            <div class="flex flex-col md:flex-row justify-between items-center">
                <p class="text-gray-400 text-sm mb-4 md:mb-0">
                    &copy; 2024 ArquiDent. Todos los derechos reservados.
                </p>
                <div class="flex space-x-6">
                    <a href="#" class="text-gray-400 hover:text-white text-sm transition-colors duration-300">Política de Privacidad</a>
                    <a href="#" class="text-gray-400 hover:text-white text-sm transition-colors duration-300">Términos de Servicio</a>
                </div>
            </div>
        </div>
    </footer>

    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
    <script>
        // Inicializar AOS solo si no está ya inicializado
        if (typeof AOS !== 'undefined' && !document.body.hasAttribute('data-aos-easing')) {
            AOS.init({
                duration: 800,
                easing: 'ease-in-out',
                once: true
            });
        }
    </script>
</body>
</html>
