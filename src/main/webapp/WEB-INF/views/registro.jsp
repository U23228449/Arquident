<%@ include file="layout/header.jsp" %>

<section class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50 py-12">
    <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="bg-white rounded-3xl shadow-2xl overflow-hidden" data-aos="fade-up">
            <!-- Header del formulario -->
            <div class="gradient-bg text-white text-center py-8">
                <div class="w-20 h-20 bg-white bg-opacity-20 rounded-full flex items-center justify-center mx-auto mb-4">
                    <i class="fas fa-user-plus text-3xl"></i>
                </div>
                <h2 class="text-3xl font-bold">Crear Cuenta</h2>
                <p class="text-blue-100 mt-2">Regístrate en ArquiDent</p>
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

                <form method="post" action="${pageContext.request.contextPath}/registro" 
                      enctype="multipart/form-data" class="space-y-6" id="registroForm">
                    
                    <!-- Información Personal -->
                    <div class="bg-gray-50 p-6 rounded-xl">
                        <h3 class="text-lg font-bold text-gray-800 mb-4">
                            <i class="fas fa-user mr-2 text-primary-500"></i>Información Personal
                        </h3>
                        
                        <div class="grid md:grid-cols-2 gap-4">
                            <div class="space-y-2">
                                <label for="nombre" class="block text-sm font-semibold text-gray-700">
                                    Nombre Completo *
                                </label>
                                <input type="text" id="nombre" name="nombre" required
                                       pattern="^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$"
                                       title="Solo se permiten letras y espacios"
                                       class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300"
                                       placeholder="Ingresa tu nombre completo">
                                <div class="text-xs text-red-500 hidden" id="nombreError">
                                    El nombre solo debe contener letras y espacios
                                </div>
                            </div>

                            <div class="space-y-2">
                                <label for="dni" class="block text-sm font-semibold text-gray-700">
                                    DNI *
                                </label>
                                <input type="text" id="dni" name="dni" required maxlength="8" pattern="[0-9]{8}"
                                       title="El DNI debe tener exactamente 8 dígitos"
                                       class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300"
                                       placeholder="12345678">
                                <div class="text-xs text-red-500 hidden" id="dniError">
                                    El DNI debe tener exactamente 8 dígitos
                                </div>
                            </div>
                        </div>

                        <div class="grid md:grid-cols-2 gap-4 mt-4">
                            <div class="space-y-2">
                                <label for="correo" class="block text-sm font-semibold text-gray-700">
                                    Correo Electrónico *
                                </label>
                                <input type="email" id="correo" name="correo" required
                                       class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300"
                                       placeholder="ejemplo@correo.com">
                                <div class="text-xs text-red-500 hidden" id="correoError">
                                    Ingresa un correo electrónico válido
                                </div>
                            </div>

                            <div class="space-y-2">
                                <label for="telefono" class="block text-sm font-semibold text-gray-700">
                                    Teléfono *
                                </label>
                                <input type="tel" id="telefono" name="telefono" required
                                       pattern="[0-9]{9}"
                                       title="El teléfono debe tener exactamente 9 dígitos"
                                       maxlength="9"
                                       class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300"
                                       placeholder="999123456">
                                <div class="text-xs text-red-500 hidden" id="telefonoError">
                                    El teléfono debe tener exactamente 9 dígitos
                                </div>
                            </div>
                        </div>

                        <div class="mt-4">
                            <label for="direccion" class="block text-sm font-semibold text-gray-700 mb-2">
                                Dirección
                            </label>
                            <textarea id="direccion" name="direccion" rows="2"
                                      class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300"
                                      placeholder="Ingresa tu dirección completa"></textarea>
                        </div>
                    </div>

                    <!-- Contraseña -->
                    <div class="bg-gray-50 p-6 rounded-xl">
                        <h3 class="text-lg font-bold text-gray-800 mb-4">
                            <i class="fas fa-lock mr-2 text-primary-500"></i>Contraseña
                        </h3>
                        
                        <!-- Indicadores de seguridad -->
                        <div class="mb-4 p-4 bg-blue-50 rounded-lg">
                            <h5 class="font-bold text-blue-800 mb-2">Requisitos de contraseña:</h5>
                            <ul class="text-sm text-blue-700 space-y-1">
                                <li id="req-length" class="flex items-center">
                                    <i class="fas fa-times text-red-500 mr-2"></i>
                                    Mínimo 8 caracteres
                                </li>
                                <li id="req-uppercase" class="flex items-center">
                                    <i class="fas fa-times text-red-500 mr-2"></i>
                                    Al menos una letra mayúscula
                                </li>
                                <li id="req-lowercase" class="flex items-center">
                                    <i class="fas fa-times text-red-500 mr-2"></i>
                                    Al menos una letra minúscula
                                </li>
                                <li id="req-number" class="flex items-center">
                                    <i class="fas fa-times text-red-500 mr-2"></i>
                                    Al menos un número
                                </li>
                                <li id="req-special" class="flex items-center">
                                    <i class="fas fa-times text-red-500 mr-2"></i>
                                    Al menos un carácter especial (!@#$%^&*)
                                </li>
                            </ul>
                        </div>
                        
                        <div class="grid md:grid-cols-2 gap-4">
                            <div class="space-y-2">
                                <label for="contrasena" class="block text-sm font-semibold text-gray-700">
                                    Contraseña *
                                </label>
                                <div class="relative">
                                    <input type="password" id="contrasena" name="contrasena" required minlength="8"
                                           pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$"
                                           title="La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas, números y símbolos"
                                           class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300 pr-12"
                                           placeholder="Mínimo 8 caracteres">
                                    <button type="button" onclick="togglePassword('contrasena')" 
                                            class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700">
                                        <i class="fas fa-eye" id="toggleContrasena"></i>
                                    </button>
                                </div>
                                <div class="text-xs text-red-500 hidden" id="contrasenaError">
                                    La contraseña no cumple con los requisitos de seguridad
                                </div>
                            </div>

                            <div class="space-y-2">
                                <label for="confirmarContrasena" class="block text-sm font-semibold text-gray-700">
                                    Confirmar Contraseña *
                                </label>
                                <div class="relative">
                                    <input type="password" id="confirmarContrasena" name="confirmarContrasena" required minlength="8"
                                           class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300 pr-12"
                                           placeholder="Repite tu contraseña">
                                    <button type="button" onclick="togglePassword('confirmarContrasena')" 
                                            class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700">
                                        <i class="fas fa-eye" id="toggleConfirmarContrasena"></i>
                                    </button>
                                </div>
                                <div class="text-xs text-red-500 hidden" id="confirmarContrasenaError">
                                    Las contraseñas no coinciden
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Documentos DNI -->
                    <div class="bg-yellow-50 border border-yellow-200 p-6 rounded-xl">
                        <h3 class="text-lg font-bold text-gray-800 mb-4">
                            <i class="fas fa-id-card mr-2 text-yellow-600"></i>Documentos de Identidad
                        </h3>
                        
                        <div class="mb-4 p-4 bg-yellow-100 rounded-lg">
                            <div class="flex items-start">
                                <i class="fas fa-info-circle text-yellow-600 mt-1 mr-3"></i>
                                <div>
                                    <h5 class="font-bold text-yellow-800 mb-1">Importante</h5>
                                    <p class="text-yellow-700 text-sm">
                                        Sube fotos claras de ambos lados de tu DNI. Estas imágenes serán revisadas 
                                        por nuestra secretaria para validar tu identidad antes de activar tu cuenta.
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="grid md:grid-cols-2 gap-4">
                            <div class="space-y-2">
                                <label for="fotoDniFrontal" class="block text-sm font-semibold text-gray-700">
                                    Foto DNI - Frontal *
                                </label>
                                <input type="file" id="fotoDniFrontal" name="fotoDniFrontal" required
                                       accept=".jpg,.jpeg,.png"
                                       class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300">
                                <p class="text-xs text-gray-500">Formatos: JPG, JPEG, PNG. Máximo 10MB</p>
                            </div>

                            <div class="space-y-2">
                                <label for="fotoDniReverso" class="block text-sm font-semibold text-gray-700">
                                    Foto DNI - Reverso *
                                </label>
                                <input type="file" id="fotoDniReverso" name="fotoDniReverso" required
                                       accept=".jpg,.jpeg,.png"
                                       class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:outline-none transition-colors duration-300">
                                <p class="text-xs text-gray-500">Formatos: JPG, JPEG, PNG. Máximo 10MB</p>
                            </div>
                        </div>
                    </div>

                    <!-- Términos y condiciones -->
                    <div class="flex items-start space-x-3">
                        <input type="checkbox" id="terminos" required
                               class="mt-1 w-4 h-4 text-primary-600 border-gray-300 rounded focus:ring-primary-500">
                        <label for="terminos" class="text-sm text-gray-700">
                            Acepto los <a href="#" class="text-primary-600 hover:underline">términos y condiciones</a> 
                            y la <a href="#" class="text-primary-600 hover:underline">política de privacidad</a> de ArquiDent.
                        </label>
                    </div>

                    <button type="submit" id="submitBtn" disabled
                            class="w-full bg-gray-400 text-white py-4 rounded-xl font-bold text-lg cursor-not-allowed transition-all duration-300">
                        <i class="fas fa-user-plus mr-2"></i>Crear Cuenta
                    </button>
                </form>

                <!-- Link para volver al login -->
                <div class="mt-8 text-center">
                    <p class="text-gray-600">
                        ¿Ya tienes una cuenta? 
                        <a href="${pageContext.request.contextPath}/login" 
                           class="text-primary-600 hover:underline font-semibold">
                            Inicia sesión aquí
                        </a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>

<%@ include file="layout/footer.jsp" %>

<script>
// Variables para validación
let validaciones = {
    nombre: false,
    dni: false,
    correo: false,
    telefono: false,
    contrasena: false,
    confirmarContrasena: false,
    terminos: false
};

// Función para mostrar/ocultar contraseña
function togglePassword(fieldId) {
    const field = document.getElementById(fieldId);
    const icon = document.getElementById('toggle' + fieldId.charAt(0).toUpperCase() + fieldId.slice(1));
    
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

// Validación del nombre (solo letras y espacios)
document.getElementById('nombre').addEventListener('input', function() {
    const nombre = this.value;
    const regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
    const errorDiv = document.getElementById('nombreError');
    
    if (nombre.length > 0 && !regex.test(nombre)) {
        this.classList.add('border-red-500');
        errorDiv.classList.remove('hidden');
        validaciones.nombre = false;
    } else if (nombre.length > 0) {
        this.classList.remove('border-red-500');
        this.classList.add('border-green-500');
        errorDiv.classList.add('hidden');
        validaciones.nombre = true;
    } else {
        this.classList.remove('border-red-500', 'border-green-500');
        errorDiv.classList.add('hidden');
        validaciones.nombre = false;
    }
    actualizarBotonSubmit();
});

// Validación del DNI
document.getElementById('dni').addEventListener('input', function() {
    const dni = this.value;
    const errorDiv = document.getElementById('dniError');
    
    // Solo permitir números
    this.value = dni.replace(/[^0-9]/g, '');
    
    if (this.value.length === 8 && /^\d{8}$/.test(this.value)) {
        this.classList.remove('border-red-500');
        this.classList.add('border-green-500');
        errorDiv.classList.add('hidden');
        validaciones.dni = true;
    } else {
        this.classList.add('border-red-500');
        this.classList.remove('border-green-500');
        if (this.value.length > 0) {
            errorDiv.classList.remove('hidden');
        }
        validaciones.dni = false;
    }
    actualizarBotonSubmit();
});

// Validación del correo
document.getElementById('correo').addEventListener('input', function() {
    const correo = this.value;
    const errorDiv = document.getElementById('correoError');
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    
    if (correo.length > 0 && regex.test(correo)) {
        this.classList.remove('border-red-500');
        this.classList.add('border-green-500');
        errorDiv.classList.add('hidden');
        validaciones.correo = true;
    } else if (correo.length > 0) {
        this.classList.add('border-red-500');
        this.classList.remove('border-green-500');
        errorDiv.classList.remove('hidden');
        validaciones.correo = false;
    } else {
        this.classList.remove('border-red-500', 'border-green-500');
        errorDiv.classList.add('hidden');
        validaciones.correo = false;
    }
    actualizarBotonSubmit();
});

// Validación del teléfono
document.getElementById('telefono').addEventListener('input', function() {
    const telefono = this.value;
    const errorDiv = document.getElementById('telefonoError');
    
    // Solo permitir números
    this.value = telefono.replace(/[^0-9]/g, '');
    
    if (this.value.length === 9 && /^\d{9}$/.test(this.value)) {
        this.classList.remove('border-red-500');
        this.classList.add('border-green-500');
        errorDiv.classList.add('hidden');
        validaciones.telefono = true;
    } else {
        this.classList.add('border-red-500');
        this.classList.remove('border-green-500');
        if (this.value.length > 0) {
            errorDiv.classList.remove('hidden');
        }
        validaciones.telefono = false;
    }
    actualizarBotonSubmit();
});

// Validación de contraseña
document.getElementById('contrasena').addEventListener('input', function() {
    const contrasena = this.value;
    const errorDiv = document.getElementById('contrasenaError');
    
    // Verificar requisitos
    const requisitos = {
        length: contrasena.length >= 8,
        uppercase: /[A-Z]/.test(contrasena),
        lowercase: /[a-z]/.test(contrasena),
        number: /\d/.test(contrasena),
        special: /[!@#$%^&*]/.test(contrasena)
    };
    
    // Actualizar indicadores visuales
    actualizarRequisito('req-length', requisitos.length);
    actualizarRequisito('req-uppercase', requisitos.uppercase);
    actualizarRequisito('req-lowercase', requisitos.lowercase);
    actualizarRequisito('req-number', requisitos.number);
    actualizarRequisito('req-special', requisitos.special);
    
    const esValida = Object.values(requisitos).every(req => req);
    
    if (esValida) {
        this.classList.remove('border-red-500');
        this.classList.add('border-green-500');
        errorDiv.classList.add('hidden');
        validaciones.contrasena = true;
    } else {
        this.classList.add('border-red-500');
        this.classList.remove('border-green-500');
        if (contrasena.length > 0) {
            errorDiv.classList.remove('hidden');
        }
        validaciones.contrasena = false;
    }
    
    // Revalidar confirmación de contraseña
    validarConfirmacionContrasena();
    actualizarBotonSubmit();
});

// Validación de confirmación de contraseña
document.getElementById('confirmarContrasena').addEventListener('input', validarConfirmacionContrasena);

function validarConfirmacionContrasena() {
    const contrasena = document.getElementById('contrasena').value;
    const confirmar = document.getElementById('confirmarContrasena').value;
    const errorDiv = document.getElementById('confirmarContrasenaError');
    const field = document.getElementById('confirmarContrasena');
    
    if (confirmar.length > 0) {
        if (contrasena === confirmar) {
            field.classList.remove('border-red-500');
            field.classList.add('border-green-500');
            errorDiv.classList.add('hidden');
            validaciones.confirmarContrasena = true;
        } else {
            field.classList.add('border-red-500');
            field.classList.remove('border-green-500');
            errorDiv.classList.remove('hidden');
            validaciones.confirmarContrasena = false;
        }
    } else {
        field.classList.remove('border-red-500', 'border-green-500');
        errorDiv.classList.add('hidden');
        validaciones.confirmarContrasena = false;
    }
    actualizarBotonSubmit();
}

// Función para actualizar indicadores de requisitos
function actualizarRequisito(id, cumplido) {
    const elemento = document.getElementById(id);
    const icono = elemento.querySelector('i');
    
    if (cumplido) {
        icono.classList.remove('fa-times', 'text-red-500');
        icono.classList.add('fa-check', 'text-green-500');
        elemento.classList.add('text-green-700');
        elemento.classList.remove('text-blue-700');
    } else {
        icono.classList.remove('fa-check', 'text-green-500');
        icono.classList.add('fa-times', 'text-red-500');
        elemento.classList.add('text-blue-700');
        elemento.classList.remove('text-green-700');
    }
}

// Validación de términos y condiciones
document.getElementById('terminos').addEventListener('change', function() {
    validaciones.terminos = this.checked;
    actualizarBotonSubmit();
});

// Función para actualizar el estado del botón de submit
function actualizarBotonSubmit() {
    const submitBtn = document.getElementById('submitBtn');
    const todasValidas = Object.values(validaciones).every(val => val);
    
    if (todasValidas) {
        submitBtn.disabled = false;
        submitBtn.classList.remove('bg-gray-400', 'cursor-not-allowed');
        submitBtn.classList.add('gradient-bg', 'hover:shadow-lg', 'transform', 'hover:scale-105');
    } else {
        submitBtn.disabled = true;
        submitBtn.classList.add('bg-gray-400', 'cursor-not-allowed');
        submitBtn.classList.remove('gradient-bg', 'hover:shadow-lg', 'transform', 'hover:scale-105');
    }
}

// Validación del formulario antes del envío
document.getElementById('registroForm').addEventListener('submit', function(e) {
    const todasValidas = Object.values(validaciones).every(val => val);
    
    if (!todasValidas) {
        e.preventDefault();
        alert('Por favor, completa todos los campos correctamente antes de enviar el formulario.');
        return false;
    }
});

// Preview de imágenes
function previewImage(input, previewId) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const preview = document.getElementById(previewId);
            if (preview) {
                preview.src = e.target.result;
                preview.style.display = 'block';
            }
        };
        reader.readAsDataURL(input.files[0]);
    }
}

document.getElementById('fotoDniFrontal').addEventListener('change', function() {
    previewImage(this, 'previewFrontal');
});

document.getElementById('fotoDniReverso').addEventListener('change', function() {
    previewImage(this, 'previewReverso');
});
</script>
