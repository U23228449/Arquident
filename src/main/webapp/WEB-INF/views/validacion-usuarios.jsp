<%@ include file="layout/header.jsp" %>

<section class="gradient-bg text-white py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between">
            <div>
                <h1 class="text-3xl font-bold">
                    <i class="fas fa-user-check mr-3"></i>
                    Validación de Usuarios
                </h1>
                <p class="text-blue-100 mt-2">
                    Panel de administración de registros
                </p>
            </div>
        </div>
    </div>
</section>

<section class="bg-white border-b">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <nav class="flex space-x-8">
            <a href="${pageContext.request.contextPath}/dashboard-secretaria" 
               class="py-4 px-1 border-b-2 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300 font-medium text-sm transition-colors duration-300">
                <i class="fas fa-arrow-left mr-2"></i>
                Volver al Dashboard
            </a>
            <span class="py-4 px-1 border-b-2 border-blue-500 text-blue-600 font-medium text-sm">
                <i class="fas fa-tools mr-2"></i>
                En Desarrollo
            </span>
        </nav>
    </div>
</section>

<section class="py-12 bg-gray-50 min-h-[60vh]">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="bg-white rounded-2xl shadow-xl p-12 text-center" data-aos="fade-up">
            
            <div class="w-24 h-24 bg-yellow-100 rounded-full flex items-center justify-center mx-auto mb-6 animate-pulse">
                <i class="fas fa-hard-hat text-yellow-600 text-4xl"></i>
            </div>

            <h2 class="text-3xl font-bold text-gray-800 mb-4">
                Funcionalidad en Proceso
            </h2>
            
            <p class="text-gray-600 text-lg max-w-2xl mx-auto mb-8 leading-relaxed">
                Estamos trabajando en mejorar el módulo de validación de usuarios para garantizar una mayor seguridad y eficiencia. Esta sección estará habilitada muy pronto.
            </p>

            <div class="flex justify-center space-x-4">
                <a href="${pageContext.request.contextPath}/dashboard-secretaria" 
                   class="bg-blue-500 text-white px-8 py-3 rounded-full hover:bg-blue-600 transition-colors duration-300 font-medium shadow-lg hover:shadow-xl transform hover:-translate-y-1">
                    <i class="fas fa-home mr-2"></i>
                    Ir al Inicio
                </a>
            </div>

        </div>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>

<script>
    // Script simplificado solo para log de carga
    document.addEventListener('DOMContentLoaded', function() {
        console.log('Vista de validación cargada - Modo mantenimiento');
    });
</script>