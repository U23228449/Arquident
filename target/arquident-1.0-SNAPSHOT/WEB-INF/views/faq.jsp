<%@ include file="layout/header.jsp" %>

<!-- Hero Section -->
<section class="gradient-bg text-white py-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h1 class="text-4xl md:text-6xl font-bold mb-6 animate-slide-up" data-aos="fade-up">
            Preguntas Frecuentes
        </h1>
        <p class="text-xl md:text-2xl text-blue-100 max-w-3xl mx-auto" data-aos="fade-up" data-aos-delay="200">
            Encuentra respuestas a las preguntas más comunes sobre nuestros servicios
        </p>
    </div>
</section>

<!-- Mensajes de estado -->
<c:if test="${not empty error}">
    <section class="py-4 bg-red-50">
        <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-xl" role="alert">
                <div class="flex items-center">
                    <i class="fas fa-exclamation-triangle mr-2"></i>
                    <span><strong>Aviso:</strong> ${error}</span>
                </div>
            </div>
        </div>
    </section>
</c:if>

<c:if test="${not empty mensaje}">
    <section class="py-4 bg-blue-50">
        <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="bg-blue-100 border border-blue-400 text-blue-700 px-4 py-3 rounded-xl" role="alert">
                <div class="flex items-center">
                    <i class="fas fa-info-circle mr-2"></i>
                    <span><strong>Información:</strong> ${mensaje}</span>
                </div>
            </div>
        </div>
    </section>
</c:if>

<!-- Search Section -->
<section class="py-12 bg-white">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="relative" data-aos="fade-up">
            <input type="text" id="faq-search" placeholder="Buscar pregunta..." 
                   class="w-full px-6 py-4 text-lg border-2 border-gray-200 rounded-full focus:border-primary-500 focus:outline-none transition-colors duration-300">
            <div class="absolute right-4 top-1/2 transform -translate-y-1/2">
                <i class="fas fa-search text-gray-400 text-xl"></i>
            </div>
        </div>
        
        <!-- Contador de resultados -->
        <div class="mt-4 text-center">
            <span id="results-count" class="text-gray-600"></span>
        </div>
    </div>
</section>

<!-- FAQ Section -->
<section class="py-12 bg-gray-50">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="space-y-6" id="faq-container">
            <c:choose>
                <c:when test="${not empty faqs}">
                    <c:forEach var="faq" items="${faqs}" varStatus="status">
                        <div class="bg-white rounded-2xl shadow-lg overflow-hidden faq-item transition-all duration-300 hover:shadow-xl" 
                             data-aos="fade-up" data-aos-delay="${status.index * 100}">
                            <button class="w-full px-8 py-6 text-left focus:outline-none faq-question hover:bg-gray-50 transition-colors duration-200" 
                                    onclick="toggleFAQ(${status.index})" 
                                    aria-expanded="false" 
                                    aria-controls="answer-${status.index}">
                                <div class="flex justify-between items-center">
                                    <h3 class="text-lg font-semibold text-gray-800 pr-4">
                                        <i class="fas fa-question-circle text-primary-500 mr-3"></i>
                                        ${faq.pregunta}
                                    </h3>
                                    <i class="fas fa-chevron-down text-primary-500 transform transition-transform duration-300" 
                                       id="icon-${status.index}"></i>
                                </div>
                            </button>
                            <div class="faq-answer hidden px-8 pb-6" id="answer-${status.index}" role="region">
                                <div class="border-t border-gray-100 pt-6">
                                    <p class="text-gray-600 leading-relaxed">${faq.respuesta}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="text-center py-12" data-aos="fade-up">
                        <i class="fas fa-question-circle text-gray-300 text-6xl mb-4"></i>
                        <h3 class="text-2xl font-bold text-gray-600 mb-2">No hay preguntas frecuentes disponibles</h3>
                        <p class="text-gray-500 mb-6">Estamos trabajando en agregar más información útil para nuestros pacientes.</p>
                        <a href="${pageContext.request.contextPath}/contacto" 
                           class="bg-primary-500 text-white px-6 py-3 rounded-full hover:bg-primary-600 transition-colors duration-300">
                            <i class="fas fa-envelope mr-2"></i>Contáctanos directamente
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        
        <!-- Mensaje cuando no hay resultados de búsqueda -->
        <div id="no-results" class="text-center py-12 hidden">
            <i class="fas fa-search text-gray-300 text-6xl mb-4"></i>
            <h3 class="text-2xl font-bold text-gray-600 mb-2">No se encontraron resultados</h3>
            <p class="text-gray-500">Intenta con otros términos de búsqueda o contacta directamente con nosotros.</p>
        </div>
    </div>
</section>

<!-- Contact Section -->
<section class="py-20 bg-white">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <div class="bg-gradient-to-r from-primary-500 to-secondary-500 rounded-3xl p-12 text-white" data-aos="fade-up">
            <h2 class="text-3xl font-bold mb-4">¿No encontraste lo que buscabas?</h2>
            <p class="text-xl text-blue-100 mb-8">Si tienes alguna pregunta específica, no dudes en contactarnos directamente.</p>
            
            <div class="grid md:grid-cols-3 gap-6 mb-8">
                <div class="flex items-center justify-center">
                    <i class="fas fa-phone text-2xl mr-3"></i>
                    <div class="text-left">
                        <p class="font-semibold">Teléfono</p>
                        <p class="text-blue-100">(01) 234-5678</p>
                    </div>
                </div>
                <div class="flex items-center justify-center">
                    <i class="fas fa-envelope text-2xl mr-3"></i>
                    <div class="text-left">
                        <p class="font-semibold">Email</p>
                        <p class="text-blue-100">info@arquident.com</p>
                    </div>
                </div>
                <div class="flex items-center justify-center">
                    <i class="fas fa-map-marker-alt text-2xl mr-3"></i>
                    <div class="text-left">
                        <p class="font-semibold">Dirección</p>
                        <p class="text-blue-100">Av. Principal 123, Lima</p>
                    </div>
                </div>
            </div>
            
            <div class="flex flex-col sm:flex-row gap-4 justify-center">
                <a href="tel:+51234567890" 
                   class="bg-white text-primary-600 px-8 py-4 rounded-full font-bold text-lg hover:bg-gray-100 transition-all duration-300 transform hover:scale-105 inline-block">
                    <i class="fas fa-phone mr-2"></i>Llamar Ahora
                </a>
                <a href="mailto:info@arquident.com" 
                   class="bg-white bg-opacity-20 text-white border-2 border-white px-8 py-4 rounded-full font-bold text-lg hover:bg-white hover:text-primary-600 transition-all duration-300 transform hover:scale-105 inline-block">
                    <i class="fas fa-envelope mr-2"></i>Enviar Email
                </a>
            </div>
        </div>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>

<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script>
    AOS.init({
        duration: 800,
        easing: 'ease-in-out',
        once: true
    });
    
    let totalFAQs = document.querySelectorAll('.faq-item').length;
    
    function toggleFAQ(index) {
        const answer = document.getElementById('answer-' + index);
        const icon = document.getElementById('icon-' + index);
        const button = answer.previousElementSibling;
        
        if (answer.classList.contains('hidden')) {
            // Cerrar todas las otras FAQs
            document.querySelectorAll('.faq-answer').forEach(function(item, i) {
                if (i !== index && !item.classList.contains('hidden')) {
                    item.classList.add('hidden');
                    item.classList.remove('animate-slide-up');
                    const otherIcon = document.getElementById('icon-' + i);
                    if (otherIcon) {
                        otherIcon.style.transform = 'rotate(0deg)';
                    }
                    item.previousElementSibling.setAttribute('aria-expanded', 'false');
                }
            });
            
            // Abrir la FAQ seleccionada
            answer.classList.remove('hidden');
            answer.classList.add('animate-slide-up');
            icon.style.transform = 'rotate(180deg)';
            button.setAttribute('aria-expanded', 'true');
        } else {
            answer.classList.add('hidden');
            answer.classList.remove('animate-slide-up');
            icon.style.transform = 'rotate(0deg)';
            button.setAttribute('aria-expanded', 'false');
        }
    }
    
    // Search functionality
    document.getElementById('faq-search').addEventListener('input', function(e) {
        const searchTerm = e.target.value.toLowerCase().trim();
        const faqItems = document.querySelectorAll('.faq-item');
        const noResults = document.getElementById('no-results');
        const resultsCount = document.getElementById('results-count');
        let visibleCount = 0;
        
        faqItems.forEach(function(item) {
            const question = item.querySelector('.faq-question').textContent.toLowerCase();
            const answer = item.querySelector('.faq-answer').textContent.toLowerCase();
            
            if (searchTerm === '' || question.includes(searchTerm) || answer.includes(searchTerm)) {
                item.style.display = 'block';
                visibleCount++;
            } else {
                item.style.display = 'none';
            }
        });
        
        // Actualizar contador de resultados
        if (searchTerm === '') {
            resultsCount.textContent = 'Mostrando ' + totalFAQs + ' preguntas frecuentes';
            noResults.classList.add('hidden');
        } else {
            resultsCount.textContent = visibleCount + ' resultado' + (visibleCount !== 1 ? 's' : '') + ' encontrado' + (visibleCount !== 1 ? 's' : '') + ' para "' + searchTerm + '"';
            
            if (visibleCount === 0) {
                noResults.classList.remove('hidden');
            } else {
                noResults.classList.add('hidden');
            }
        }
    });
    
    // Inicializar contador
    document.addEventListener('DOMContentLoaded', function() {
        const resultsCount = document.getElementById('results-count');
        if (totalFAQs > 0) {
            resultsCount.textContent = 'Mostrando ' + totalFAQs + ' preguntas frecuentes';
        }
        
        // Debug: verificar que los elementos existen
        console.log('Total FAQs encontradas:', totalFAQs);
        document.querySelectorAll('.faq-item').forEach(function(item, index) {
            console.log('FAQ ' + index + ':', item);
        });
    });
    
    // Cerrar FAQs al hacer clic fuera
    document.addEventListener('click', function(e) {
        if (!e.target.closest('.faq-item')) {
            document.querySelectorAll('.faq-answer').forEach(function(answer, index) {
                if (!answer.classList.contains('hidden')) {
                    answer.classList.add('hidden');
                    answer.classList.remove('animate-slide-up');
                    const icon = document.getElementById('icon-' + index);
                    if (icon) {
                        icon.style.transform = 'rotate(0deg)';
                    }
                    answer.previousElementSibling.setAttribute('aria-expanded', 'false');
                }
            });
        }
    });
</script>
