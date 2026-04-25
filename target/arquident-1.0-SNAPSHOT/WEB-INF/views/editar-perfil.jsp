<%@ include file="layout/header.jsp" %>

<!-- Header -->
<section class="gradient-bg text-white py-12">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center" data-aos="fade-up">
            <h1 class="text-3xl md:text-4xl font-bold mb-4">
                <i class="fas fa-user-edit mr-3"></i>
                Editar Perfil
            </h1>
            <p class="text-blue-100 text-lg">
                Actualiza tu información personal
            </p>
        </div>
    </div>
</section>

<!-- Alertas -->
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

<c:if test="${not empty exito}">
    <section class="bg-green-50 border-l-4 border-green-500 py-4">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex items-center">
                <i class="fas fa-check-circle text-green-500 text-xl mr-3"></i>
                <p class="text-green-700">${exito}</p>
            </div>
        </div>
    </section>
</c:if>

<!-- Contenido Principal -->
<section class="py-12 bg-gray-50">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <!-- Navegación de regreso -->
        <div class="mb-6">
            <a href="${pageContext.request.contextPath}/dashboard-paciente" 
               class="inline-flex items-center text-blue-500 hover:text-blue-600 transition-colors duration-300">
                <i class="fas fa-arrow-left mr-2"></i>
                Volver al Dashboard
            </a>
        </div>
        
        <div class="grid lg:grid-cols-3 gap-8">
            
            <!-- Información del Usuario -->
            <div class="lg:col-span-1">
                <div class="bg-white rounded-2xl shadow-lg p-6 text-center" data-aos="fade-up">
                    <div class="w-24 h-24 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full flex items-center justify-center mx-auto mb-4">
                        <i class="fas fa-user text-white text-3xl"></i>
                    </div>
                    <h3 class="text-xl font-bold text-gray-800 mb-2">${sessionScope.usuario.nombre}</h3>
                    <p class="text-gray-600 mb-1">${sessionScope.usuario.correo}</p>
                    <p class="text-gray-600 mb-4">${sessionScope.usuario.telefono}</p>
                    <span class="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm font-medium capitalize">
                        ${sessionScope.rolUsuario}
                    </span>
                </div>
            </div>
            
            <!-- Formularios de Edición -->
            <div class="lg:col-span-2 space-y-8">
                
                <!-- Actualizar Datos Personales -->
                <div class="bg-white rounded-2xl shadow-lg p-6" data-aos="fade-up" data-aos-delay="100">
                    <h3 class="text-xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-user text-blue-500 mr-2"></i>
                        Datos Personales
                    </h3>
                    
                    <form method="post" action="${pageContext.request.contextPath}/editar-perfil">
                        <input type="hidden" name="accion" value="actualizar-datos">
                        
                        <div class="grid md:grid-cols-2 gap-6">
                            <div>
                                <label class="block text-sm font-medium text-gray-700 mb-2">Nombre Completo</label>
                                <input type="text" value="${sessionScope.usuario.nombre}" disabled
                                       class="w-full px-4 py-3 border border-gray-300 rounded-xl bg-gray-100 text-gray-500 cursor-not-allowed">
                                <p class="text-xs text-gray-500 mt-1">El nombre no se puede modificar</p>
                            </div>
                            
                            <div>
                                <label class="block text-sm font-medium text-gray-700 mb-2">Teléfono *</label>
                                <input type="tel" name="telefono" value="${sessionScope.usuario.telefono}" required
                                       class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none"
                                       pattern="[0-9]{9}" maxlength="9" placeholder="987654321">
                            </div>
                            
                            <div class="md:col-span-2">
                                <label class="block text-sm font-medium text-gray-700 mb-2">Correo Electrónico *</label>
                                <input type="email" name="correo" value="${sessionScope.usuario.correo}" required
                                       class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none"
                                       placeholder="correo@ejemplo.com">
                            </div>
                        </div>
                        
                        <div class="flex justify-end mt-6">
                            <button type="submit" 
                                    class="bg-blue-500 text-white px-6 py-3 rounded-xl hover:bg-blue-600 transition-colors duration-300">
                                <i class="fas fa-save mr-2"></i>
                                Guardar Cambios
                            </button>
                        </div>
                    </form>
                </div>
                
                <!-- Cambiar Contraseña -->
                <div class="bg-white rounded-2xl shadow-lg p-6" data-aos="fade-up" data-aos-delay="200">
                    <h3 class="text-xl font-bold text-gray-800 mb-6">
                        <i class="fas fa-lock text-orange-500 mr-2"></i>
                        Cambiar Contraseña
                    </h3>
                    
                    <form method="post" action="${pageContext.request.contextPath}/editar-perfil" id="form-password">
                        <input type="hidden" name="accion" value="cambiar-password">
                        
                        <div class="space-y-6">
                            <div>
                                <label class="block text-sm font-medium text-gray-700 mb-2">Contraseña Actual *</label>
                                <div class="relative">
                                    <input type="password" name="password-actual" required
                                           class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none pr-12"
                                           placeholder="Ingresa tu contraseña actual">
                                    <button type="button" onclick="togglePassword('password-actual')" 
                                            class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700">
                                        <i class="fas fa-eye" id="icon-password-actual"></i>
                                    </button>
                                </div>
                            </div>
                            
                            <div>
                                <label class="block text-sm font-medium text-gray-700 mb-2">Nueva Contraseña *</label>
                                <div class="relative">
                                    <input type="password" name="password-nueva" required minlength="6"
                                           class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none pr-12"
                                           placeholder="Mínimo 6 caracteres">
                                    <button type="button" onclick="togglePassword('password-nueva')" 
                                            class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700">
                                        <i class="fas fa-eye" id="icon-password-nueva"></i>
                                    </button>
                                </div>
                                <div class="mt-2">
                                    <div class="text-xs text-gray-500">
                                        La contraseña debe tener al menos:
                                    </div>
                                    <ul class="text-xs text-gray-500 mt-1 space-y-1">
                                        <li id="length-check" class="flex items-center">
                                            <i class="fas fa-times text-red-500 mr-2"></i>
                                            6 caracteres mínimo
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            
                            <div>
                                <label class="block text-sm font-medium text-gray-700 mb-2">Confirmar Nueva Contraseña *</label>
                                <div class="relative">
                                    <input type="password" name="password-confirmar" required minlength="6"
                                           class="w-full px-4 py-3 border border-gray-300 rounded-xl focus:border-blue-500 focus:outline-none pr-12"
                                           placeholder="Repite la nueva contraseña">
                                    <button type="button" onclick="togglePassword('password-confirmar')" 
                                            class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700">
                                        <i class="fas fa-eye" id="icon-password-confirmar"></i>
                                    </button>
                                </div>
                                <div id="password-match" class="mt-2 text-xs hidden">
                                    <i class="fas fa-times text-red-500 mr-2"></i>
                                    Las contraseñas no coinciden
                                </div>
                            </div>
                        </div>
                        
                        <div class="flex justify-end mt-6">
                            <button type="submit" id="btn-cambiar-password"
                                    class="bg-orange-500 text-white px-6 py-3 rounded-xl hover:bg-orange-600 transition-colors duration-300 disabled:bg-gray-300 disabled:cursor-not-allowed">
                                <i class="fas fa-key mr-2"></i>
                                Cambiar Contraseña
                            </button>
                        </div>
                    </form>
                </div>
                
                <!-- Información de Seguridad -->
                <div class="bg-yellow-50 border border-yellow-200 rounded-2xl p-6" data-aos="fade-up" data-aos-delay="300">
                    <div class="flex items-start">
                        <i class="fas fa-shield-alt text-yellow-500 text-2xl mr-4 mt-1"></i>
                        <div>
                            <h4 class="font-bold text-yellow-800 mb-2">Consejos de Seguridad</h4>
                            <ul class="text-yellow-700 text-sm space-y-1">
                                <li>? Usa una contraseña única que no uses en otros sitios</li>
                                <li>? Incluye números, letras mayúsculas y minúsculas</li>
                                <li>? No compartas tu contraseña con nadie</li>
                                <li>? Cambia tu contraseña regularmente</li>
                            </ul>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>

<script>
// Función para mostrar/ocultar contraseñas
function togglePassword(fieldName) {
    const field = document.querySelector(`input[name="${fieldName}"]`);
    const icon = document.getElementById(`icon-${fieldName}`);
    
    if (field.type === 'password') {
        field.type = 'text';
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        field.type = 'password';
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}

// Validación en tiempo real de contraseñas
document.addEventListener('DOMContentLoaded', function() {
    const passwordNueva = document.querySelector('input[name="password-nueva"]');
    const passwordConfirmar = document.querySelector('input[name="password-confirmar"]');
    const lengthCheck = document.getElementById('length-check');
    const passwordMatch = document.getElementById('password-match');
    const btnCambiar = document.getElementById('btn-cambiar-password');
    
    function validarPassword() {
        const password = passwordNueva.value;
        const confirmar = passwordConfirmar.value;
        
        // Validar longitud
        if (password.length >= 6) {
            lengthCheck.innerHTML = '<i class="fas fa-check text-green-500 mr-2"></i>6 caracteres mínimo';
        } else {
            lengthCheck.innerHTML = '<i class="fas fa-times text-red-500 mr-2"></i>6 caracteres mínimo';
        }
        
        // Validar coincidencia
        if (confirmar.length > 0) {
            if (password === confirmar) {
                passwordMatch.innerHTML = '<i class="fas fa-check text-green-500 mr-2"></i>Las contraseñas coinciden';
                passwordMatch.classList.remove('hidden');
                passwordMatch.classList.remove('text-red-500');
                passwordMatch.classList.add('text-green-500');
            } else {
                passwordMatch.innerHTML = '<i class="fas fa-times text-red-500 mr-2"></i>Las contraseñas no coinciden';
                passwordMatch.classList.remove('hidden');
                passwordMatch.classList.remove('text-green-500');
                passwordMatch.classList.add('text-red-500');
            }
        } else {
            passwordMatch.classList.add('hidden');
        }
        
        // Habilitar/deshabilitar botón
        const esValido = password.length >= 6 && password === confirmar && confirmar.length > 0;
        btnCambiar.disabled = !esValido;
    }
    
    passwordNueva.addEventListener('input', validarPassword);
    passwordConfirmar.addEventListener('input', validarPassword);
    
    // Validación del formulario
    document.getElementById('form-password').addEventListener('submit', function(e) {
        const password = passwordNueva.value;
        const confirmar = passwordConfirmar.value;
        
        if (password.length < 6) {
            e.preventDefault();
            alert('La contraseña debe tener al menos 6 caracteres.');
            return;
        }
        
        if (password !== confirmar) {
            e.preventDefault();
            alert('Las contraseñas no coinciden.');
            return;
        }
    });
});
</script>
